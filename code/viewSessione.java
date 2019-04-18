package com.example.riccardo.myuniversity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class viewSessione extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessione);

        TextView codice_sessione_view = (TextView)findViewById(R.id.codice_sessione_view);
        TextView materia_sessione_view = (TextView)findViewById(R.id.materia_sessione_view);
        TextView data1_sessione_view = (TextView)findViewById(R.id.data1_sessione_view);
        TextView ora1_sessione_view = (TextView)findViewById(R.id.ora1_sessione_view);
        TextView aula1_sessione_view = (TextView)findViewById(R.id.aula1_sessione_view);
        TextView data2_sessione_view = (TextView)findViewById(R.id.data2_sessione_view);
        TextView ora2_sessione_view = (TextView)findViewById(R.id.ora2_sessione_view);
        TextView aula2_sessione_view = (TextView)findViewById(R.id.aula2_sessione_view);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM sessione";
        Cursor c = db.rawQuery(sql, null);
        String lista1 = "", lista2 = "", lista3 = "", lista4 = "", lista5 = "", lista6 = "", lista7 = "", lista8 = "";

        while(c.moveToNext()){
            lista1 = lista1 + c.getString(1) + "\n";
            lista2 = lista2 + c.getString(2) + "\n";
            lista3 = lista3 + c.getString(3) + "\n";
            lista4 = lista4 + c.getString(4) + "\n";
            lista5 = lista5 + c.getString(5) + "\n";
            lista6 = lista6 + c.getString(6) + "\n";
            lista7 = lista7 + c.getString(7) + "\n";
            lista8 = lista8 + c.getString(8) + "\n"; }

        codice_sessione_view.setText(lista1);
        materia_sessione_view.setText(lista2);
        data1_sessione_view.setText(lista3);
        ora1_sessione_view.setText(lista4);
        aula1_sessione_view.setText(lista5);
        data2_sessione_view.setText(lista6);
        ora2_sessione_view.setText(lista7);
        aula2_sessione_view.setText(lista8);
    }

}
