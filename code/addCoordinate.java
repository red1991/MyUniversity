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
import android.widget.RadioGroup;
import android.widget.Toast;


public class addCoordinate extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coordinate);

        mydb = new Database(getApplicationContext());

        Button conferma_coordinate = (Button)findViewById(R.id.conferma_coordinate);
        final EditText aula_coordinate = (EditText)findViewById(R.id.aula_coordinate);
        final EditText latitudine_coordinate = (EditText)findViewById(R.id.latitudine_coordinate);
        final EditText longitudine_coordinate = (EditText)findViewById(R.id.longitudine_coordinate);

        conferma_coordinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aula_coordinate.getText().toString().isEmpty() || latitudine_coordinate.getText().toString().isEmpty() || longitudine_coordinate.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addCoordinate.this);
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
                    values.put("aula", aula_coordinate.getText().toString());
                    values.put("latitudine", latitudine_coordinate.getText().toString());
                    values.put("longitudine", longitudine_coordinate.getText().toString());

                    SQLiteDatabase db = mydb.getWritableDatabase();
                    db.insert("coordinate", null, values);
                    db.close();
                    Toast.makeText(addCoordinate.this, "Coordinata inserita con successo!", Toast.LENGTH_SHORT).show();
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
