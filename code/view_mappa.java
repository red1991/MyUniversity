package com.example.riccardo.myuniversity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


public class view_mappa extends ActionBarActivity {
    private Database mydb = null;
    LocationManager lm;
    LocationListener locList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mappa);

        Bundle extra = getIntent().getExtras();
        final String nome = extra.getString("nome");
        final String aula = extra.getString("aula");


        //////////////////////////////////////////////////

        mydb = new Database(getApplicationContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        String sql = "SELECT * FROM coordinate WHERE aula = '" + aula + "'"  ;
        final Cursor c = db.rawQuery(sql, null);
        c.moveToNext();

        String latitudine = c.getString(2);
        String longitudine = c.getString(3);

        final Double lat_aula = Double.parseDouble(latitudine);
        final Double lon_aula = Double.parseDouble(longitudine);

        ////////////////////////////////////////////////////

        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locList = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(), "LOCALIZZATORE ATTIVATO!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(), "LOCALIZZATORE NON ATTIVO!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onLocationChanged(Location loc) {
                Double lat_mia = loc.getLatitude();
                Double lon_mia = loc.getLongitude();
                passa(lat_mia, lon_mia, lat_aula, lon_aula);
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_mappa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onPause(){
        super.onPause();
        lm.removeUpdates(locList);
    }

    protected void onResume() {
        super.onResume();
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locList);
    }

    public void passa(Double lat_mia, Double lon_mia, Double lat_aula, Double lon_aula){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("lat_mia", lat_mia);
        intent.putExtra("lon_mia", lon_mia);
        intent.putExtra("lat_aula", lat_aula);
        intent.putExtra("lon_aula", lon_aula);
        startActivity(intent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
