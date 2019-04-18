package com.example.riccardo.myuniversity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class viewPiano extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_piano);

        TextView codice_piano_view = (TextView)findViewById(R.id.codice_piano_view);
        TextView materia_piano_view = (TextView)findViewById(R.id.materia_piano_view);
        TextView cfu_piano_view = (TextView)findViewById(R.id.cfu_piano_view);
        TextView voto_piano_view = (TextView)findViewById(R.id.voto_piano_view);
        TextView data_piano_view = (TextView)findViewById(R.id.data_piano_view);
        TextView seguito_piano_view = (TextView)findViewById(R.id.seguito_piano_view);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM piano";
        Cursor c = db.rawQuery(sql, null);
        String lista1 = "", lista2 = "", lista3 = "", lista4 = "", lista5 = "", lista6 = "";

        while(c.moveToNext()){
            lista1 = lista1 + c.getString(1) + "\n";
            lista2 = lista2 + c.getString(2) + "\n";
            lista3 = lista3 + c.getString(3) + "\n";
            lista4 = lista4 + c.getString(4) + "\n";
            lista5 = lista5 + c.getString(5) + "\n";
            lista6 = lista6 + c.getString(6) + "\n"; }

        codice_piano_view.setText(lista1);
        materia_piano_view.setText(lista2);
        cfu_piano_view.setText(lista3);
        voto_piano_view.setText(lista4);
        data_piano_view.setText(lista5);
        seguito_piano_view.setText(lista6);
    }

}
