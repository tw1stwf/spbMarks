package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SightOnMapActivity extends AppCompatActivity implements OnMapReadyCallback  {

    private GoogleMap map;
    private LatLng sightMark;
    private int count;
    private static final String TAG = "MyApp";

  //  SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_on_map);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Cursor query = db.rawQuery("SELECT latitude, longitude FROM sights WHERE id = " + 1 + " ;", null);

        /*
        double latitude;
        double longitude;

        while (query.moveToNext()) {
            latitude = query.getDouble(0);
            longitude = query.getDouble(1);
            Log.i(TAG, "lat = " + latitude + "long = " + longitude);
        }

         */

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        sightMark = new LatLng(59.42432, 30.44242);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sightMark, 14));
        map.addMarker(new MarkerOptions().position(sightMark));

        /*
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        for(int i = 0; i <= count; i++)
        {
            double latitude;
            double longitude;
            Cursor query = db.rawQuery("SELECT latitude, longitude FROM sights WHERE id = " + i + " ;", null);

            while (query.moveToNext()) {

                latitude = query.getDouble(0);
                longitude = query.getDouble(1);
                sightMark = new LatLng(latitude, longitude);
            }

            sightMark = new LatLng(59.42432, 30.44242);
            map.addMarker(new MarkerOptions().position(sightMark));
        }

         */


    }

    public void back(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}