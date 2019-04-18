package com.example.riccardo.myuniversity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class viewOrario extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orario);

        TextView codice_orario_view = (TextView)findViewById(R.id.codice_orario_view);
        TextView materia_orario_view = (TextView)findViewById(R.id.materia_orario_view);
        TextView aula_orario_view = (TextView)findViewById(R.id.aula_orario_view);
        TextView lun_orario_view = (TextView)findViewById(R.id.lun_orario_view);
        TextView mar_orario_view = (TextView)findViewById(R.id.mar_orario_view);
        TextView mer_orario_view = (TextView)findViewById(R.id.mer_orario_view);
        TextView gio_orario_view = (TextView)findViewById(R.id.gio_orario_view);
        TextView ven_orario_view = (TextView)findViewById(R.id.ven_orario_view);
        TextView cfu_orario_view = (TextView)findViewById(R.id.cfu_orario_view);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM orario";
        Cursor c = db.rawQuery(sql, null);
        String lista1 = "", lista2 = "", lista3 = "", lista4 = "", lista5 = "", lista6 = "", lista7 = "", lista8 = "", lista9 = "";

        while(c.moveToNext()){
            lista1 = lista1 + c.getString(1) + "\n";
            lista2 = lista2 + c.getString(2) + "\n";
            lista3 = lista3 + c.getString(3) + "\n";
            lista4 = lista4 + c.getString(4) + "\n";
            lista5 = lista5 + c.getString(5) + "\n";
            lista6 = lista6 + c.getString(6) + "\n";
            lista7 = lista7 + c.getString(7) + "\n";
            lista8 = lista8 + c.getString(8) + "\n";
            lista9 = lista9 + c.getString(9) + "\n"; }

        codice_orario_view.setText(lista1);
        materia_orario_view.setText(lista2);
        aula_orario_view.setText(lista3);
        lun_orario_view.setText(lista4);
        mar_orario_view.setText(lista5);
        mer_orario_view.setText(lista6);
        gio_orario_view.setText(lista7);
        ven_orario_view.setText(lista8);
        cfu_orario_view.setText(lista9);
    }



}
