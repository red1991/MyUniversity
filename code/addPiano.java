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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class addPiano extends ActionBarActivity {
    String decisione_piano = new String();
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_piano);

        mydb = new Database(getApplicationContext());

        Button conferma_piano = (Button)findViewById(R.id.conferma_piano);
        final EditText codice_piano = (EditText)findViewById(R.id.codice_piano);
        final EditText materia_piano = (EditText)findViewById(R.id.materia_piano);
        final EditText cfu_piano = (EditText)findViewById(R.id.cfu_piano);

        conferma_piano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codice_piano.getText().toString().isEmpty() || materia_piano.getText().toString().isEmpty() || cfu_piano.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addPiano.this);
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
                    values.put("codice", codice_piano.getText().toString());
                    values.put("materia", materia_piano.getText().toString());
                    values.put("cfu", Integer.parseInt(cfu_piano.getText().toString()));
                    values.put("seguito", "NO");

                    SQLiteDatabase db = mydb.getWritableDatabase();
                    db.insert("piano", null, values);
                    db.close();
                    Toast.makeText(addPiano.this, "Esame inserito con successo!", Toast.LENGTH_SHORT).show();
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
