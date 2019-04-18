package com.example.riccardo.myuniversity;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    LocationManager lm;
    LocationListener locList;

    public void onReceive(Context context, Intent intent){

        try{

            Bundle extra = intent.getExtras();
            String name = extra.getString("nome");
            String room = extra.getString("aula");


            Intent resultIntent = new Intent(context, view_mappa.class);
            resultIntent.putExtra("nome", name);
            resultIntent.putExtra("aula", room);
            PendingIntent notificIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.icona)
                    .setContentTitle("Avviso appello d'esame!")
                    .setTicker("Hai Ricevuto un messaggio da MyUniversity")
                    .setContentText("Esame di " + name);

            mBuilder.setContentIntent(notificIntent);
            mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
            mBuilder.setAutoCancel(true);

            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(1, mBuilder.build());

        }catch(Exception e){

            Toast.makeText(context, "Errore", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}