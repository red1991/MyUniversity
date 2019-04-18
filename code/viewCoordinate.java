package com.example.riccardo.myuniversity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class viewCoordinate extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_coordinate);

        TextView aula_coordinate_view = (TextView)findViewById(R.id.aula_coordinate_view);
        TextView latitudine_coordinate_view = (TextView)findViewById(R.id.latitudine_coordinate_view);
        TextView longitudine_coordinate_view = (TextView)findViewById(R.id.longitudine_coordinate_view);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM coordinate";
        Cursor c = db.rawQuery(sql, null);
        String lista1 = "", lista2 = "", lista3 = "";

        while(c.moveToNext()){
            lista1 = lista1 + c.getString(1) + "\n";
            lista2 = lista2 + c.getString(2) + "\n";
            lista3 = lista3 + c.getString(3) + "\n"; }

        aula_coordinate_view.setText(lista1);
        latitudine_coordinate_view.setText(lista2);
        longitudine_coordinate_view.setText(lista3);
    }



}
