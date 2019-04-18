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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class loginPiano extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_piano);

        Button login = (Button) findViewById(R.id.login);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(loginPiano.this);
                    builder.setIcon(R.drawable.icona);
                    builder.setTitle("ATTENZIONE!");
                    builder.setMessage("I campi non possono essere lasciati vuoti!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                } else if ((username.getText().toString().equals("riccardo") || username.getText().toString().equals("jacopo")) && (password.getText().toString().equals("tesi"))) {
                    Toast.makeText(loginPiano.this, "Autenticazione effettuata!", Toast.LENGTH_SHORT).show();
                    start_manage_piano();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(loginPiano.this);
                    builder.setIcon(R.drawable.icona);
                    builder.setTitle("ATTENZIONE!");
                    builder.setMessage("Username o password errati!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Riprova", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }

        });
    }

    public void start_manage_piano() {
        Intent intent = new Intent(this, managePiano.class);
        startActivity(intent);
    }

    public void start_main() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}