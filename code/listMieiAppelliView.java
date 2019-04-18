package com.example.riccardo.myuniversity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class listMieiAppelliView extends ActionBarActivity {
    private Database mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_miei_appelli_view);

        Bundle codice = getIntent().getExtras();
        final String code = codice.getString("codice");

        Bundle nome = getIntent().getExtras();
        final String name = codice.getString("nome");

        TextView dettagli_esame_view = (TextView)findViewById(R.id.dettagli_esame_view);
        TextView nome_esame_view = (TextView)findViewById(R.id.nome_esame_view);
        TextView passato = (TextView)findViewById(R.id.passato);

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM prenotati WHERE codice = " + code + "";
        final Cursor c = db.rawQuery(sql, null);
        c.moveToNext();

        String lista = "";

        nome_esame_view.setText(name);

        lista = "Data: " + c.getString(2) + "\nOra: " + c.getString(3) + "\nAula: " + c.getString(4);

        dettagli_esame_view.setText(lista);
    }



}
