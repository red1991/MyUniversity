package com.example.riccardo.myuniversity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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


public class listSuperati extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_superati);

        final ListView listview_superati = (ListView)findViewById(R.id.listview_superati);

        ImageView superati_vuoto = (ImageView)findViewById(R.id.superati_vuoto);
        TextView testo_superati_vuoto = (TextView)findViewById(R.id.testo_superati_vuoto);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();

        String sql = "SELECT * FROM piano WHERE piano.voto IS NOT NULL";
        final Cursor c = db.rawQuery(sql, null);

        String from[] = {"materia", "codice", "cfu"};
        int[] to = {R.id.textview_nome_superati,R.id.textview_codice_superati, R.id.textview_cfu_superati};

        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,R.layout.activity_listview_superati,c,from,to,0);
        listview_superati.setAdapter(sca);

        if(sca.isEmpty()){
            listview_superati.setVisibility(View.GONE);
            superati_vuoto.setImageResource(R.drawable.vuoto);
            testo_superati_vuoto.setText("Non sono presenti esami!");
        }


        listview_superati.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {

                String codice = c.getString(1);
                String nome = c.getString(2);
                String cfu = c.getString(3);
                String voto = c.getString(4);
                String data = c.getString(5);
                AlertDialog.Builder builder = new AlertDialog.Builder(listSuperati.this);
                builder.setTitle(nome);
                builder.setIcon(R.drawable.icona);
                builder.setMessage("Codice: " + codice +  "\nCFU: " + cfu + "\nVoto: " + voto + "\nSuperato il: " + data + "");
                builder.setCancelable(false);
                builder.setPositiveButton("Torna alla lista", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();



            }
        });
    }


}
