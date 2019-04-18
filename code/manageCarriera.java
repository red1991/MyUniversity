package com.example.riccardo.myuniversity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class manageCarriera extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_carriera);

        final ListView listview_manage = (ListView)findViewById(R.id.listview_manage);

        String [] array = {"Gestione appelli","Gestione corsi","I tuoi appelli","I tuoi corsi", "Inserisci votazione", "Esami superati"};
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, R.layout.activity_listview_manage, R.id.text_manage, array);
        listview_manage.setAdapter(arrayAdapter);

        listview_manage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(id == 0){
                    start_visualizza_lista_appelli();
                }
                else if(id == 1){
                    start_visualizza_lista_corsi();
                }
                else if(id == 2){
                    start_visualizza_miei_appelli();
                }
                else if(id == 3){
                    start_visualizza_lista_miei_corsi();
                }
                else if(id == 4){
                    start_visualizza_lista_voti();
                }
                else if(id == 5){
                    start_visualizza_lista_superati();
                }
            }
        });

    }

    public void start_visualizza_lista_voti(){
        Intent intent = new Intent(this, listVoti.class);
        startActivity(intent);
    }

    public void start_visualizza_lista_miei_corsi(){
        Intent intent = new Intent(this, listMieiCorsi.class);
        startActivity(intent);
    }

    public void start_visualizza_miei_appelli(){
        Intent intent = new Intent(this, listMieiAppelli.class);
        startActivity(intent);
    }

    public void start_visualizza_lista_appelli(){
        Intent intent = new Intent(this, listAppelli.class);
        startActivity(intent);
    }

    public void start_visualizza_lista_corsi(){
        Intent intent = new Intent(this, listCorsi.class);
        startActivity(intent);
    }

    public void start_visualizza_lista_superati(){
        Intent intent = new Intent(this, listSuperati.class);
        startActivity(intent);
    }


}
