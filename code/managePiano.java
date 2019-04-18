package com.example.riccardo.myuniversity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class managePiano extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_piano);

        final ListView listview_manage_piano = (ListView)findViewById(R.id.listview_manage_piano);

        String [] array = {"Aggiungi un esame al piano di studi","Inserisci orario di lezione di un corso", "Inserisci una sessione di esame ","Inserisci coordinate aula", "Visualizza piano di studi", "Visualizza orari di lezione", "Visualizza sessioni di esame", "Visualizza coordinate aule", "Torna in MyUniversity"};
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, R.layout.activity_listview_manage_piano, R.id.text_manage_piano, array);
        listview_manage_piano.setAdapter(arrayAdapter);

        listview_manage_piano.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(id == 0){
                    start_inserisci_piano();
                }
                else if(id == 1){
                    start_inserisci_orario();
                }
                else if(id == 2){
                    start_inserisci_sessione();
                }
                else if(id == 3){
                    start_inserisci_coordinate();
                }
                else if(id == 4){
                    start_visualizza_piano();
                }
                else if(id == 5){
                    start_visualizza_orario();
                }

                else if(id == 6){
                    start_visualizza_sessione();
                }

                else if(id == 7){
                    start_visualizza_coordinate();
                }

                else if(id == 8){
                    AlertDialog.Builder builder = new AlertDialog.Builder(managePiano.this);
                    builder.setIcon(R.drawable.icona);
                    builder.setTitle("Torna a MyUniversity");
                    builder.setMessage("Ti ricordiamo che uscendo da questa sezione per rientrare sarà necessario effettuare nuovamente l'autenticazione");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Annulla", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Torna a MyUniversity", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            start_main();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    public void start_main(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void start_inserisci_piano(){
        Intent intent = new Intent(this, addPiano.class);
        startActivity(intent);
    }

    public void start_visualizza_piano(){
        Intent intent = new Intent(this, viewPiano.class);
        startActivity(intent);
    }

    public void start_inserisci_sessione(){
        Intent intent = new Intent(this, addSessione.class);
        startActivity(intent);
    }

    public void start_visualizza_sessione(){
        Intent intent = new Intent(this, viewSessione.class);
        startActivity(intent);
    }

    public void start_inserisci_orario(){
        Intent intent = new Intent(this, addOrario.class);
        startActivity(intent);
    }

    public void start_visualizza_orario(){
        Intent intent = new Intent(this, viewOrario.class);
        startActivity(intent);
    }

    public void start_inserisci_coordinate(){
        Intent intent = new Intent(this, addCoordinate.class);
        startActivity(intent);
    }

    public void start_visualizza_coordinate(){
        Intent intent = new Intent(this, viewCoordinate.class);
        startActivity(intent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }



}
