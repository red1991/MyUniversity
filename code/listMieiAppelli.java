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
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class listMieiAppelli extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_miei_appelli);

        ImageView vuoto = (ImageView)findViewById(R.id.vuoto);
        TextView text_vuoto = (TextView)findViewById(R.id.text_vuoto);

        Calendar cal = new GregorianCalendar();
        int giorno = cal.get(Calendar.DAY_OF_MONTH);
        int mese = 1 + cal.get(Calendar.MONTH);
        int anno = cal.get(Calendar.YEAR);

        final String dataoggi = (giorno + "/" + mese  + "/" + anno);

        ListView listview_miei_appelli = (ListView)findViewById(R.id.listview_miei_appelli);
        TextView vuoto_miei_appelli = (TextView)findViewById(R.id.vuoto_miei_appelli);
        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM piano,prenotati  WHERE piano.codice = prenotati.codice AND prenotati.data >= " + dataoggi + "  AND piano.voto IS NULL";
        final Cursor c = db.rawQuery(sql, null);
        c.moveToNext();

        String from[] = {"materia", "codice"};
        int[] to = {R.id.textview_miei_appelli, R.id.textview_codice_miei_appelli};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.activity_listview_miei_appelli,c,from,to,0);
        if(sca.isEmpty()){
            listview_miei_appelli.setVisibility(View.GONE);
            vuoto.setImageResource(R.drawable.vuoto);
            text_vuoto.setText("NON HAI ANCORA PRENOTATO ESAMI!");
        }
        listview_miei_appelli.setAdapter(sca);

        listview_miei_appelli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {
                String codice = c.getString(1);
                String nome = c.getString(2);
                start_dettagli_esame(codice, nome);
            }
        });
    }

    public void start_dettagli_esame(String codice, String nome){
        Intent intent = new Intent(this,listMieiAppelliView.class);
        intent.putExtra("codice", codice);
        intent.putExtra("nome", nome);
        startActivity(intent);
    }


}
