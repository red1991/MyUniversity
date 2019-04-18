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


public class listCorsi extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_corsi);

        final ListView listview_corsi = (ListView)findViewById(R.id.listview_corsi);

        ImageView corsi_vuoto = (ImageView)findViewById(R.id.corsi_vuoto);
        TextView testo_corsi_vuoto = (TextView)findViewById(R.id.testo_corsi_vuoto);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();

        String sql = "SELECT * FROM piano WHERE piano.voto IS NULL";
        final Cursor c = db.rawQuery(sql, null);

        String from[] = {"materia"};
        int[] to = {R.id.textview_nome_corsi};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.activity_listview_corsi,c,from,to,0);
        listview_corsi.setAdapter(sca);

        if(sca.isEmpty()){
            listview_corsi.setVisibility(View.GONE);
            corsi_vuoto.setImageResource(R.drawable.laurea);
            testo_corsi_vuoto.setText("COMPLIMENTI INGEGNERE!");
        }


        listview_corsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {
                String codice = c.getString(1);
                start_segui_corso(codice);
            }
        });
    }

    public void start_segui_corso(String codice){
        Intent intent = new Intent(this,addCorsi.class);
        intent.putExtra("codice", codice);
        startActivity(intent);
    }



}
