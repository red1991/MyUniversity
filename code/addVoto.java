package com.example.riccardo.myuniversity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class addVoto extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voto);

        mydb = new Database(getApplicationContext());

        Bundle codice = getIntent().getExtras();
        final String code = codice.getString("codice");

        Bundle nome = getIntent().getExtras();
        final String name = codice.getString("nome");

        TextView nome_esame_voto = (TextView)findViewById(R.id.nome_esame_voto);
        final EditText testo_voto = (EditText)findViewById(R.id.testo_voto);
        Button aggiungi_voto = (Button)findViewById(R.id.aggiungi_voto);

        Calendar cal = new GregorianCalendar();
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        int mese = 1 + cal.get(Calendar.MONTH);
        int anno = cal.get(Calendar.YEAR);

        final String dataoggi = (giorno + "/" + mese  + "/" + anno);

        nome_esame_voto.setText(name);

        aggiungi_voto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mydb.getWritableDatabase();

                if ( testo_voto.getText().toString().isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(addVoto.this);
                    builder.setIcon(R.drawable.icona);
                    builder.setTitle("ATTENZIONE!");
                    builder.setMessage("Il campo voto non può essere lasciato vuoto!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                else if ((Integer.parseInt(testo_voto.getText().toString()) >= 18) && (Integer.parseInt(testo_voto.getText().toString()) <= 31))
                {
                    ContentValues valori = new ContentValues();
                    String whereClause = "codice = ?";
                    String[] whereArgs = {code};
                    valori.put("data", dataoggi);
                    valori.put("seguito", "NO");
                    valori.put("voto", Integer.parseInt(testo_voto.getText().toString()));
                    db.update("piano", valori, whereClause, whereArgs);
                    db.close();

                    AlertDialog.Builder builder = new AlertDialog.Builder(addVoto.this);
                    builder.setIcon(R.drawable.icona);
                    builder.setTitle("OPERAZIONE EFFETTUATA!");
                    builder.setMessage("Hai inserito con successo il voto dell'esame alla tua carriera!");
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

                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addVoto.this);
                    builder.setIcon(R.drawable.icona);
                    builder.setTitle("ATTENZIONE!");
                    builder.setMessage("Il voto inserito deve essere compreso tra 18 e 31.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    public void start_main(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
