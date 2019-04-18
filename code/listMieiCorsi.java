package com.example.riccardo.myuniversity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class listMieiCorsi extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_miei_corsi);

        ImageView vuoto = (ImageView)findViewById(R.id.vuoto);
        TextView text_vuoto_corsi = (TextView)findViewById(R.id.text_vuoto_corsi);

        Calendar cal = new GregorianCalendar();
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        int mese = 1 + cal.get(Calendar.MONTH);
        int anno = cal.get(Calendar.YEAR);

        final String dataoggi = (giorno + "/" + mese  + "/" + anno);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db1 = mydb.getReadableDatabase();
        String sql1 = "SELECT * FROM semestre";
        final Cursor d = db1.rawQuery(sql1, null);
        d.moveToNext();
        String data_fine = d.getString(2);

        ListView listview_miei_corsi = (ListView)findViewById(R.id.listview_miei_corsi);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


        try {
            Date data1 = formatter.parse(dataoggi);
            Date data2 = formatter.parse(data_fine);
            if(data2.compareTo(data1)<0){
                listview_miei_corsi.setVisibility(View.GONE);
                vuoto.setImageResource(R.drawable.fine);
                text_vuoto_corsi.setText("IL SEMESTRE E' TERMINATO!");
            }

         else if((data1.compareTo(data2)<0) || (data1.compareTo(data2)<0)) {
            mydb = new Database(getApplicationContext());
            SQLiteDatabase db = mydb.getReadableDatabase();
            String sql = "SELECT * FROM seguiti, piano WHERE (piano.codice = seguiti.codice) AND (piano.voto IS NULL)";
            final Cursor c = db.rawQuery(sql, null);
            c.moveToNext();

            String from[] = {"materia"};
            int[] to = {R.id.textview_miei_corsi};

            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.activity_listview_miei_corsi, c, from, to, 0);
            if (sca.isEmpty()) {
                listview_miei_corsi.setVisibility(View.GONE);
                vuoto.setImageResource(R.drawable.vuoto);
                text_vuoto_corsi.setText("NON STAI SEGUENDO NESSUN CORSO!");
            }
            listview_miei_corsi.setAdapter(sca);

            listview_miei_corsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {
                    String codice = c.getString(1);
                    String nome = c.getString(2);
                    start_dettagli_corso(codice, nome);
                }

            });
        }
        } catch (ParseException e) {}
    }

    public void start_dettagli_corso(String codice, String nome){
        Intent intent = new Intent(this,listMieiCorsiView.class);
        intent.putExtra("codice", codice);
        intent.putExtra("nome", nome);
        startActivity(intent);
    }


}
