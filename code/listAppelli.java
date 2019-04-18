package com.example.riccardo.myuniversity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class listAppelli extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appelli);

        final ListView listview_appelli = (ListView)findViewById(R.id.listview_appelli);

        ImageView appelli_vuoto = (ImageView)findViewById(R.id.appelli_vuoto);
        TextView testo_appelli_vuoto = (TextView)findViewById(R.id.testo_appelli_vuoto);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();

        String sql = "SELECT * FROM piano, sessione WHERE (piano.codice = sessione.codice) AND (piano.voto IS NULL)";
        final Cursor c = db.rawQuery(sql, null);

        String from[] = {"materia", "codice", "cfu"};
        int[] to = {R.id.textview_nome_appelli, R.id.textview_codice_appelli, R.id.textview_cfu_appelli};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.activity_listview_appelli,c,from,to,0);
        listview_appelli.setAdapter(sca);

        if(sca.isEmpty()){
            listview_appelli.setVisibility(View.GONE);
            appelli_vuoto.setImageResource(R.drawable.laurea);
            testo_appelli_vuoto.setText("COMPLIMENTI INGEGNERE!");
        }


        listview_appelli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {
                String codice = c.getString(1);
                start_prenota_esame(codice);
            }
        });
    }

    public void start_prenota_esame(String codice){
        Intent intent = new Intent(this,addAppelli.class);
        intent.putExtra("codice", codice);
        startActivity(intent);
    }



}