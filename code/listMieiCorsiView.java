package com.example.riccardo.myuniversity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;


public class listMieiCorsiView extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_miei_corsi_view);

        Bundle codice = getIntent().getExtras();
        final String code = codice.getString("codice");

        Bundle nome = getIntent().getExtras();
        final String name = codice.getString("nome");

        TextView dettagli_corso_view = (TextView)findViewById(R.id.dettagli_corso_view);
        TextView nome_corso_view = (TextView)findViewById(R.id.nome_corso_view);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db1 = mydb.getReadableDatabase();
        String sql1 = "SELECT * FROM semestre";
        final Cursor d = db1.rawQuery(sql1, null);
        d.moveToNext();
        String data_inizio = d.getString(1);

        Calendar cal = new GregorianCalendar();
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        int mese = 1 + cal.get(Calendar.MONTH);
        int anno = cal.get(Calendar.YEAR);

        final String dataoggi = (giorno + "/" + mese  + "/" + anno);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM orario WHERE codice = " + code + "";
        final Cursor c = db.rawQuery(sql, null);
        String lista = "";
        nome_corso_view.setText(name);



        Calendar cal1 = new GregorianCalendar();
        int sett = cal1.get(Calendar.DAY_OF_WEEK);
        int x = 0;

        String[] giorni = new String[10];
        giorni[4] = "1)LUNEDI'";
        giorni[5] = "2)MARTERI'";
        giorni[6] = "3)MERCOLEDI'";
        giorni[7] = "3)GIOVEDI'";
        giorni[8] = "4)VENERDI'";


        ArrayList<String> array = new ArrayList<String>();

        if(sett == 2){x = 4;}
        else if(sett == 3){x = 5;}
        else if(sett == 4){x = 6;}
        else if(sett == 5){x = 7;}
        else if(sett == 6){x = 8;}
        else if(sett == 7){x = 4;}
        else if(sett == 1){x = 4;}

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date data1 = formatter.parse(dataoggi);
            Date data2 = formatter.parse(data_inizio);
            if(data1.compareTo(data2)<0){
               x = 4;
            }

        } catch (ParseException e) {}

        while(c.moveToNext()) {
            for (int i = x; i <= 8; i++) {
                if (!(c.getString(i).isEmpty())) {
                    array.add(giorni[i] + " " + c.getString(i) + " | Aula: " + c.getString(3));
                }
            }
        }

        Collections.sort(array);
        String cut = "";

        if(!(array.isEmpty())) {

            for (int j = 0; j < array.size(); j++) {
                cut = array.get(j);
                lista = lista + cut.substring(2) + " \n";
            }
            dettagli_corso_view.setText(lista);
        }
        else {
            dettagli_corso_view.setText("Da oggi fino alla fine della settimana non hai lezioni per questo corso!");
        }


    }


}
