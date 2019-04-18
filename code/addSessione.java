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


public class addSessione extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sessione);

        mydb = new Database(getApplicationContext());

        Button conferma_sessione = (Button)findViewById(R.id.conferma_sessione);

        final EditText codice_sessione = (EditText)findViewById(R.id.codice_sessione);
        final EditText materia_sessione = (EditText)findViewById(R.id.materia_sessione);
        final EditText data1_sessione = (EditText)findViewById(R.id.data1_sessione);
        final EditText ora1_sessione = (EditText)findViewById(R.id.ora1_sessione);
        final EditText aula1_sessione = (EditText)findViewById(R.id.aula1_sessione);

        final EditText data2_sessione = (EditText)findViewById(R.id.data2_sessione);
        final EditText ora2_sessione = (EditText)findViewById(R.id.ora2_sessione);
        final EditText aula2_sessione = (EditText)findViewById(R.id.aula2_sessione);


        conferma_sessione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codice_sessione.getText().toString().isEmpty() || materia_sessione.getText().toString().isEmpty() || data1_sessione.getText().toString().isEmpty() || ora1_sessione.getText().toString().isEmpty() || aula1_sessione.getText().toString().isEmpty() ||data2_sessione.getText().toString().isEmpty() || ora2_sessione.getText().toString().isEmpty() || aula2_sessione.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addSessione.this);
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
                    values.put("codice", codice_sessione.getText().toString());
                    values.put("materia", materia_sessione.getText().toString());
                    values.put("data1", data1_sessione.getText().toString());
                    values.put("ora1", ora1_sessione.getText().toString());
                    values.put("aula1", aula1_sessione.getText().toString());
                    values.put("data2", data2_sessione.getText().toString());
                    values.put("ora2", ora2_sessione.getText().toString());
                    values.put("aula2", aula2_sessione.getText().toString());

                    SQLiteDatabase db = mydb.getWritableDatabase();
                    db.insert("sessione", null, values);
                    db.close();
                    Toast.makeText(addSessione.this, "Esame inserito con successo!", Toast.LENGTH_SHORT).show();
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
