package com.example.riccardo.myuniversity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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


public class addAppelli extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appelli);

        Bundle codice = getIntent().getExtras();
        final String code = codice.getString("codice");

        TextView text_nome_add = (TextView)findViewById(R.id.text_nome_add);
        TextView text_dettagli1_add = (TextView)findViewById(R.id.text_dettagli1_add);
        TextView text_dettagli2_add = (TextView)findViewById(R.id.text_dettagli2_add);
        TextView text_warning1_add = (TextView)findViewById(R.id.text_warning1_add);
        TextView text_warning2_add = (TextView)findViewById(R.id.text_warning2_add);

        final Button prenota_primo_appelli = (Button)findViewById(R.id.prenota_primo_appelli);
        final Button prenota_secondo_appelli = (Button)findViewById(R.id.prenota_secondo_appelli);

        final Button annulla_primo_appelli = (Button)findViewById(R.id.annulla_primo_appelli);
        final Button annulla_secondo_appelli = (Button)findViewById(R.id.annulla_secondo_appelli);

        annulla_primo_appelli.setEnabled(false);
        annulla_secondo_appelli.setEnabled(false);

        annulla_primo_appelli.setBackgroundResource(R.drawable.delete_disattivato);
        annulla_secondo_appelli.setBackgroundResource(R.drawable.delete_disattivato);


        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM sessione WHERE codice = " + code + "";
        final Cursor c = db.rawQuery(sql, null);
        c.moveToNext();

        String sql1 = "SELECT data FROM prenotati WHERE codice = " + code + "";
        final Cursor d = db.rawQuery(sql1, null);

        while(d.moveToNext()){
            if(d.getString(0).equals(c.getString(3))){
                prenota_primo_appelli.setEnabled(false);
                prenota_secondo_appelli.setEnabled(false);
                prenota_primo_appelli.setBackgroundResource(R.drawable.prenota_disattivato);
                prenota_secondo_appelli.setBackgroundResource(R.drawable.prenota_disattivato);
                text_warning1_add.setTextColor(Color.BLUE);
                text_warning1_add.setText("Esame prenotato");
                annulla_primo_appelli.setEnabled(true);
                annulla_primo_appelli.setBackgroundResource(R.drawable.delete);
            }
        }

        d.moveToPosition(-1);

        while(d.moveToNext()){
            if(d.getString(0).equals(c.getString(6))){
                prenota_secondo_appelli.setEnabled(false);
                prenota_primo_appelli.setEnabled(false);
                prenota_primo_appelli.setBackgroundResource(R.drawable.prenota_disattivato);
                prenota_secondo_appelli.setBackgroundResource(R.drawable.prenota_disattivato);
                text_warning2_add.setTextColor(Color.BLUE);
                text_warning2_add.setText("Esame prenotato");
                annulla_secondo_appelli.setEnabled(true);
                annulla_secondo_appelli.setBackgroundResource(R.drawable.delete);
            }
        }

        String lista1 = "", lista2 = "";

        Calendar cal = new GregorianCalendar();
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        int mese = 1 + cal.get(Calendar.MONTH);
        int anno = cal.get(Calendar.YEAR);

        final String dataoggi = (giorno + "/" + mese  + "/" + anno);

        String data_1 = c.getString(3);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date data1 = formatter.parse(data_1);
            Date data2 = formatter.parse(dataoggi);
            if(data1.compareTo(data2)<0){
                prenota_primo_appelli.setEnabled(false);
                prenota_primo_appelli.setBackgroundResource(R.drawable.prenota_disattivato);
                annulla_primo_appelli.setEnabled(false);
                annulla_primo_appelli.setBackgroundResource(R.drawable.delete_disattivato);
                text_warning1_add.setTextColor(Color.RED);
                text_warning1_add.setText("ATTENZIONE: Appello non più disponibile!");}

        } catch (ParseException e) {}

        String data_2 = c.getString(6);
        try {

            Date data1 = formatter.parse(data_2);
            Date data2 = formatter.parse(dataoggi);

            if(data1.compareTo(data2)<0){
                prenota_secondo_appelli.setEnabled(false);
                prenota_secondo_appelli.setBackgroundResource(R.drawable.prenota_disattivato);
                annulla_secondo_appelli.setEnabled(false);
                annulla_secondo_appelli.setBackgroundResource(R.drawable.delete_disattivato);
                text_warning2_add.setTextColor(Color.RED);
                text_warning2_add.setText("ATTENZIONE: Appello non più disponibile!");}

        } catch (ParseException e) {}

        text_nome_add.setText(c.getString(2));
        lista1 = "Data: " + c.getString(3) + "\nOra: " + c.getString(4) + "\nAula: " + c.getString(5);
        text_dettagli1_add.setText(lista1);
        lista2 = "Data: " + c.getString(6) + "\nOra: " + c.getString(7) + "\nAula: " + c.getString(8);
        text_dettagli2_add.setText(lista2);

        prenota_primo_appelli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("codice", code);
                values.put("data", c.getString(3));
                values.put("ora", c.getString(4));
                values.put("aula", c.getString(5));
                SQLiteDatabase db = mydb.getWritableDatabase();
                db.insert("prenotati", null, values);


                ContentValues valori = new ContentValues();
                String whereClause = "codice = ?";
                String[] whereArgs = {code};
                valori.put("data", c.getString(3));
                db.update("piano", valori, whereClause, whereArgs);

                /////////////////////////////////////////////////////////////////////

                String g, m, a;
                String o, min;

                g = c.getString(3).substring(0, 2);
                m = c.getString(3).substring(3, 5);
                a = c.getString(3).substring(6, 10);

                int giorno, mese, anno;

                giorno = Integer.parseInt(g);
                mese = Integer.parseInt(m) - 1;
                anno = Integer.parseInt(a);

                o = c.getString(4).substring(0, 2);
                min = c.getString(4).substring(3, 5);

                int ora, minuti;

                ora = Integer.parseInt(o);
                minuti = Integer.parseInt(min);


                Calendar cal = Calendar.getInstance();

                cal.set(Calendar.YEAR, anno);
                cal.set(Calendar.MONTH, mese);
                cal.set(Calendar.DAY_OF_MONTH, giorno);
                cal.set(Calendar.HOUR_OF_DAY, ora);
                cal.set(Calendar.MINUTE, minuti);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);

                Intent intent = new Intent(addAppelli.this, AlarmReceiver.class);
                intent.putExtra("nome", c.getString(2));
                intent.putExtra("aula", c.getString(5));

                int co = Integer.parseInt(code);

                PendingIntent sender = PendingIntent.getBroadcast(addAppelli.this, co , intent, 0);

                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),sender);


                Toast.makeText(addAppelli.this, "Allarme settato", Toast.LENGTH_SHORT).show();



                /////////////////////////////////////////////////////////////////////



                db.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(addAppelli.this);
                builder.setIcon(R.drawable.icona);
                builder.setTitle("OPERAZIONE EFFETTUATA!");
                builder.setMessage("Prenotazione esame andata a buon fine! Controlla la lista dei tuoi appelli per tutti tutti i dettagli.");
                builder.setCancelable(false);
                builder.setPositiveButton("Grazie!", new DialogInterface.OnClickListener() {
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

        prenota_secondo_appelli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("codice", code);
                values.put("data", c.getString(6));
                values.put("ora", c.getString(7));
                values.put("aula", c.getString(8));
                SQLiteDatabase db = mydb.getWritableDatabase();
                db.insert("prenotati", null, values);

                ContentValues valori = new ContentValues();
                String whereClause = "codice = ?";
                String[] whereArgs = {code};
                valori.put("data", c.getString(6));
                db.update("piano", valori, whereClause, whereArgs);



                /////////////////////////////////////////////////////////////

                String g, m, a;
                String o, min;

                g = c.getString(6).substring(0, 2);
                m = c.getString(6).substring(3, 5);
                a = c.getString(6).substring(6, 10);

                int giorno, mese, anno;

                giorno = Integer.parseInt(g);
                mese = Integer.parseInt(m) - 1;
                anno = Integer.parseInt(a);

                o = c.getString(7).substring(0, 2);
                min = c.getString(7).substring(3, 5);

                int ora, minuti;

                ora = Integer.parseInt(o);
                minuti = Integer.parseInt(min);


                Calendar cal = Calendar.getInstance();

                cal.set(Calendar.YEAR, anno);
                cal.set(Calendar.MONTH, mese);
                cal.set(Calendar.DAY_OF_MONTH, giorno);
                cal.set(Calendar.HOUR_OF_DAY, ora);
                cal.set(Calendar.MINUTE, minuti);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);

                Intent intent = new Intent(addAppelli.this, AlarmReceiver.class);
                intent.putExtra("nome", c.getString(2));
                intent.putExtra("aula", c.getString(8));

                int co = Integer.parseInt(code);

                PendingIntent sender = PendingIntent.getBroadcast(addAppelli.this, co , intent, 0);

                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),sender);


                Toast.makeText(addAppelli.this, "Allarme settato", Toast.LENGTH_SHORT).show();

                /////////////////////////////////////////////////////////////
                db.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(addAppelli.this);
                builder.setIcon(R.drawable.icona);
                builder.setTitle("OPERAZIONE EFFETTUATA!");
                builder.setMessage("Prenotazione esame andata a buon fine! Controlla la lista dei tuoi appelli per tutti tutti i dettagli.");
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

        annulla_primo_appelli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whereClause = "codice = ?";
                String[] whereArgs = {code};
                SQLiteDatabase db = mydb.getWritableDatabase();
                db.delete("prenotati", whereClause, whereArgs);

                ContentValues valori = new ContentValues();
                String whereClause1 = "codice = ?";
                String[] whereArgs1 = {code};
                valori.put("data", "null");
                db.update("piano", valori, whereClause1, whereArgs1);

                int co = Integer.parseInt(code);

                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

                Intent intent = new Intent(addAppelli.this, AlarmReceiver.class);

                PendingIntent sender = PendingIntent.getBroadcast(addAppelli.this, co , intent, 0);


                if (am!= null) {
                    am.cancel(sender);
                }

                Toast.makeText(addAppelli.this, "Allarme settato", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(addAppelli.this);
                builder.setIcon(R.drawable.icona);
                builder.setTitle("OPERAZIONE EFFETTUATA!");
                builder.setMessage("Prenotazione cancellata con successo!");
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

        annulla_secondo_appelli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whereClause = "codice = ?";
                String[] whereArgs = {code};
                SQLiteDatabase db = mydb.getWritableDatabase();
                db.delete("prenotati", whereClause, whereArgs);

                ContentValues valori = new ContentValues();
                String whereClause2 = "codice = ?";
                String[] whereArgs2 = {code};
                valori.put("data", "null");
                db.update("piano", valori, whereClause2, whereArgs2);

                int co = Integer.parseInt(code);

                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

                Intent intent = new Intent(addAppelli.this, AlarmReceiver.class);

                PendingIntent sender = PendingIntent.getBroadcast(addAppelli.this, co , intent, 0);


                if (am!= null) {
                    am.cancel(sender);
                }

                Toast.makeText(addAppelli.this, "Allarme settato", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(addAppelli.this);
                builder.setIcon(R.drawable.icona);
                builder.setTitle("OPERAZIONE EFFETTUATA!");
                builder.setMessage("Prenotazione cancellata con successo!");
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