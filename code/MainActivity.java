package com.example.riccardo.myuniversity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView inizio_semestre = (TextView)findViewById(R.id.inizio_semestre);
        TextView fine_semestre = (TextView)findViewById(R.id.fine_semestre);
        TextView crediti_totali = (TextView)findViewById(R.id.crediti_totali);
        TextView crediti_ottenuti = (TextView)findViewById(R.id.crediti_ottenuti);
        TextView crediti_mancanti = (TextView)findViewById(R.id.crediti_mancanti);
        TextView esami_totali = (TextView)findViewById(R.id.esami_totali);
        TextView esami_sostenuti = (TextView)findViewById(R.id.esami_sostenuti);
        TextView esami_mancanti = (TextView)findViewById(R.id.esami_mancanti);
        TextView media_pesata = (TextView)findViewById(R.id.media_pesata);
        final TextView esami_vicini = (TextView)findViewById(R.id.esami_vicini);

        Button gestisci_carriera = (Button)findViewById(R.id.gestisci_carriera);
        Button bottone_piano = (Button)findViewById(R.id.bottone_piano);

        gestisci_carriera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_manage();
            }
        });

        bottone_piano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_modifica_piano();
            }
        });


        mydb = new Database(getApplicationContext());
        SQLiteDatabase db5 = mydb.getReadableDatabase();
        String sql5 = "SELECT * FROM piano WHERE voto IS NULL AND data IS NOT NULL";
        Cursor o = db5.rawQuery(sql5, null);

        while(o.moveToNext()) {

            Calendar cal = new GregorianCalendar();
            int giorno = cal.get(Calendar.DAY_OF_MONTH);
            int mese = 1 + cal.get(Calendar.MONTH);
            int anno = cal.get(Calendar.YEAR);

            final String dataoggi = (giorno + "/" + mese + "/" + anno);

            String data_1 = o.getString(5);
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");

            try {

                Date data1 = formatter1.parse(data_1);
                Date data2 = formatter1.parse(dataoggi);
                if (data1.compareTo(data2) < 0) {
                    String whereClause = "codice = ?";
                    String[] whereArgs = {o.getString(1)};
                    SQLiteDatabase db = mydb.getWritableDatabase();
                    db.delete("prenotati", whereClause, whereArgs);

                    ContentValues valori = new ContentValues();
                    String whereClause1 = "codice = ?";
                    String[] whereArgs1 = {o.getString(1)};
                    valori.put("data", "null");
                    db.update("piano", valori, whereClause1, whereArgs1);
                }

            } catch (ParseException e) {
            }
        }


        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM semestre";
        Cursor c = db.rawQuery(sql, null);
        c.moveToNext();
        inizio_semestre.setText(c.getString(1));
        fine_semestre.setText(c.getString(2));

        int crediti_tot = 0;

        sql = "SELECT * FROM piano";
        Cursor a = db.rawQuery(sql, null);

        while(a.moveToNext()){
            crediti_tot = crediti_tot + a.getInt(3);
        }

        crediti_totali.setText(Integer.toString(crediti_tot));

        int crediti_ott = 0;


        sql = "SELECT * FROM piano WHERE voto IS NOT NULL";
        Cursor b = db.rawQuery(sql, null);

        while(b.moveToNext()){
            crediti_ott = crediti_ott + b.getInt(3);
        }

        crediti_ottenuti.setText(Integer.toString(crediti_ott));

        int crediti_manc = 0;

        sql = "SELECT * FROM piano WHERE voto IS NULL";
        Cursor d = db.rawQuery(sql, null);

        while(d.moveToNext()){
            crediti_manc = crediti_manc + d.getInt(3);
        }

        crediti_mancanti.setText(Integer.toString(crediti_manc));

        int conteggio_totali = 0;

        sql = "SELECT * FROM piano";
        Cursor e = db.rawQuery(sql, null);

        while(e.moveToNext()){
            conteggio_totali++;
        }

        esami_totali.setText(Integer.toString(conteggio_totali));

        int conteggio_sostenuti = 0;

        sql = "SELECT * FROM piano WHERE voto IS NOT NULL";
        Cursor f = db.rawQuery(sql, null);

        while(f.moveToNext()){
            conteggio_sostenuti++;
        }

        esami_sostenuti.setText(Integer.toString(conteggio_sostenuti));


        int conteggio_mancanti = 0;

        sql = "SELECT * FROM piano WHERE voto IS NULL";
        Cursor g = db.rawQuery(sql, null);

        while(g.moveToNext()){
            conteggio_mancanti++;
        }

        esami_mancanti.setText(Integer.toString(conteggio_mancanti));


        sql = "SELECT * FROM piano WHERE voto IS NOT NULL";
        Cursor h = db.rawQuery(sql, null);

        double numeratore = 0;
        double denominatore = 0;
        double totale;

        while(h.moveToNext()){
            numeratore = numeratore + (h.getInt(3) * h.getInt(4));
            denominatore = denominatore + h.getInt(3);
        }

        totale = numeratore / denominatore;

        if(denominatore == 0.0){
            media_pesata.setText("0.0");
        }
        else{
            media_pesata.setText(Double.toString(totale));
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Date> array = new ArrayList<Date>();
        ArrayList<String> date = new ArrayList<String>();

        sql = "SELECT * FROM piano WHERE (voto IS NULL) AND (data IS NOT NULL) AND (data <> 'null')";
        Cursor z = db.rawQuery(sql, null);


        try {

            while(z.moveToNext()){
                array.add(formatter.parse(z.getString(5)));
            }

            Collections.sort(array);


            for(int i = 0; i < array.size(); i++){
                date.add(formatter.format(array.get(i)));
            }

            String lista = "";

            Cursor q;

            if(date.isEmpty()){
                lista = "Non ci sono esami!";
            }else {


                for (int i = 0; i < 1; i++) {
                    sql = "SELECT * FROM piano WHERE (voto IS NULL) AND data = '" + date.get(i) + "'";
                    q = db.rawQuery(sql, null);
                    if (q.getCount() != 0) {
                        q.moveToNext();
                        lista = lista + q.getString(2) + "\n(" + q.getString(5) + ") \n";
                    }
                }
            }

            esami_vicini.setText(lista);
        } catch (ParseException r) {}
    }

    public void start_manage(){
        Intent intent = new Intent(this, manageCarriera.class);
        startActivity(intent);
    }

    public void start_modifica_piano(){
        Intent intent = new Intent(this, loginPiano.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.icona);
            builder.setTitle("Cosa è MyUniversity?");
            builder.setMessage("Applicazione nata per facilitare lo studente durante la sua carriera universitaria. \n \n A cura di: \n\n ROSSI RICCARDO \n DE LUCA JACOPO");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}

