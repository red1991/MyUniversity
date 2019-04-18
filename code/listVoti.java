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


public class listVoti extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_voti);

        final ListView listview_voti = (ListView)findViewById(R.id.listview_voti);
        ImageView voti_vuoto = (ImageView)findViewById(R.id.voti_vuoto);
        TextView testo_voti_vuoto = (TextView)findViewById(R.id.testo_voti_vuoto);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();

        String sql = "SELECT * FROM piano WHERE voto IS NULL AND data IS NOT NULL AND data <> 'null'";
        final Cursor c = db.rawQuery(sql, null);

        String from[] = {"materia", "codice", "cfu"};
        int[] to = {R.id.textview_nome_voti, R.id.textview_codice_voti, R.id.textview_cfu_voti};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.activity_listview_voti,c,from,to,0);
        listview_voti.setAdapter(sca);

        if(sca.isEmpty()){
            listview_voti.setVisibility(View.GONE);
            voti_vuoto.setImageResource(R.drawable.vuoto);
            testo_voti_vuoto.setText("NON CI SONO ESAMI PER LA QUALE INSERIRE UNA VOTAZIONE!");
        }


        listview_voti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {
                String codice = c.getString(1);
                String nome = c.getString(2);
                start_inserisci_voto(codice, nome);
            }
        });
    }


    public void start_inserisci_voto(String codice, String nome){
        Intent intent = new Intent(this,addVoto.class);
        intent.putExtra("codice", codice);
        intent.putExtra("nome", nome);
        startActivity(intent);
    }



}
