package com.example.riccardo.myuniversity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class addOrario extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_orario);

        mydb = new Database(getApplicationContext());

        Button conferma_orario = (Button)findViewById(R.id.conferma_orario);

        final EditText codice_orario = (EditText)findViewById(R.id.codice_orario);
        final EditText materia_orario = (EditText)findViewById(R.id.materia_orario);
        final EditText aula_orario = (EditText)findViewById(R.id.aula_orario);
        final EditText lun_orario = (EditText)findViewById(R.id.lun_orario);
        final EditText mar_orario = (EditText)findViewById(R.id.mar_orario);
        final EditText mer_orario = (EditText)findViewById(R.id.mer_orario);
        final EditText gio_orario = (EditText)findViewById(R.id.gio_orario);
        final EditText ven_orario = (EditText)findViewById(R.id.ven_orario);
        final EditText cfu_orario = (EditText)findViewById(R.id.cfu_orario);

        conferma_orario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codice_orario.getText().toString().isEmpty() || materia_orario.getText().toString().isEmpty() || aula_orario.getText().toString().isEmpty() || cfu_orario.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addOrario.this);
                    builder.setIcon(R.drawable.icona);
                    builder.setTitle("ATTENZIONE!");
                    builder.setMessage("I campi devono essere riempiti");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Torna indietro", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    ContentValues values = new ContentValues();
                    values.put("codice", codice_orario.getText().toString());
                    values.put("materia", materia_orario.getText().toString());
                    values.put("aula", aula_orario.getText().toString());
                    values.put("lun", lun_orario.getText().toString());
                    values.put("mar", mar_orario.getText().toString());
                    values.put("mer", mer_orario.getText().toString());
                    values.put("gio", gio_orario.getText().toString());
                    values.put("ven", ven_orario.getText().toString());
                    values.put("cfu", Integer.parseInt(cfu_orario.getText().toString()));

                    SQLiteDatabase db = mydb.getWritableDatabase();
                    db.insert("orario", null, values);
                    db.close();
                    Toast.makeText(addOrario.this, "Orario inserito con successo!", Toast.LENGTH_SHORT).show();
                    start_main_manage();
                }
            }
        });
    }

    public void start_main_manage(){
        Intent intent = new Intent(this,managePiano.class);
        startActivity(intent);
    }


}
