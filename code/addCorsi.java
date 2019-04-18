package com.example.riccardo.myuniversity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class addCorsi extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_corsi);

        Bundle codice = getIntent().getExtras();
        final String code = codice.getString("codice");

        TextView text_nome_add_corsi = (TextView)findViewById(R.id.text_nome_add_corsi);
        TextView text_dettagli_add_corsi = (TextView)findViewById(R.id.text_dettagli_add_corsi);
        TextView text_warning_add_corsi = (TextView)findViewById(R.id.text_warning_add_corsi);

        final Button segui_corso = (Button)findViewById(R.id.segui_corso);
        final Button annulla_corso = (Button)findViewById(R.id.annulla_corso);

        annulla_corso.setEnabled(false);
        annulla_corso.setBackgroundResource(R.drawable.delete_disattivato);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM piano, semestre WHERE  piano.codice = " + code + "";
        final Cursor c = db.rawQuery(sql, null);
        c.moveToNext();

        String sql1 = "SELECT codice FROM seguiti WHERE codice = " + code + "";
        final Cursor d = db.rawQuery(sql1, null);

        while(d.moveToNext()){
            if(d.getString(0).equals(c.getString(1))){
                segui_corso.setEnabled(false);
                segui_corso.setBackgroundResource(R.drawable.prenota_disattivato);
                text_warning_add_corsi.setTextColor(Color.BLUE);
                text_warning_add_corsi.setText("Stai seguendo questo corso");
                annulla_corso.setEnabled(true);
                annulla_corso.setBackgroundResource(R.drawable.delete);
            }
        }

        Calendar cal = new GregorianCalendar();
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        int mese = 1 + cal.get(Calendar.MONTH);
        int anno = cal.get(Calendar.YEAR);

        final String dataoggi = (giorno + "/" + mese  + "/" + anno);

        String data_1 = c.getString(9);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date data1 = formatter.parse(data_1);
            Date data2 = formatter.parse(dataoggi);
            if(data1.compareTo(data2)<0){
                segui_corso.setEnabled(false);
                segui_corso.setBackgroundResource(R.drawable.prenota_disattivato);
                annulla_corso.setEnabled(false);
                annulla_corso.setBackgroundResource(R.drawable.delete_disattivato);
                text_warning_add_corsi.setTextColor(Color.RED);
                text_warning_add_corsi.setText("ATTENZIONE: Corso non più disponibile!");}

        } catch (ParseException e) {}

        text_nome_add_corsi.setText(c.getString(2));
        String lista = "";

        lista = "Codice: " + c.getString(1) + "\nCFU: " + c.getString(3) + "\nSeguito: " + c.getString(6);
        text_dettagli_add_corsi.setText(lista);

        segui_corso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("codice", code);
                values.put("materia", c.getString(2));

                SQLiteDatabase db = mydb.getWritableDatabase();
                db.insert("seguiti", null, values);
                ContentValues valori = new ContentValues();
                String whereClause = "codice = ?";
                String[] whereArgs = {code};
                valori.put("seguito", "SI");
                db.update("piano", valori, whereClause, whereArgs);
                db.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(addCorsi.this);
                builder.setIcon(R.drawable.icona);
                builder.setTitle("OPERAZIONE EFFETTUATA!");
                builder.setMessage("Adesso segui questo corso! Controlla la lista dei tuoi corsi per tutti tutti i dettagli.");
                builder.setCancelable(false);
                builder.setPositiveButton("Grazie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        start_main();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        annulla_corso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whereClause = "codice = ?";
                String[] whereArgs = {code};
                SQLiteDatabase db = mydb.getWritableDatabase();
                db.delete("seguiti", whereClause, whereArgs);

                ContentValues valori = new ContentValues();
                String whereClause1 = "codice = ?";
                String[] whereArgs1 = {code};
                valori.put("seguito", "NO");
                db.update("piano", valori, whereClause1, whereArgs1);
                db.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(addCorsi.this);
                builder.setIcon(R.drawable.icona);
                builder.setTitle("OPERAZIONE EFFETTUATA!");
                builder.setMessage("Adesso non segui più questo corso!");
                builder.setCancelable(false);
                builder.setPositiveButton("Grazie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        start_main();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void start_main(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
