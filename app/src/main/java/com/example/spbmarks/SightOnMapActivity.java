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
    private LatLng sightMark, cityMark;

    private double latitude, longitude;
    private String name;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_on_map);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        cityMark = new LatLng(59.9429296, 30.314819);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cityMark, 12));

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        Cursor query = db.rawQuery("SELECT COUNT(*) FROM sights;", null);
        while (query.moveToNext()) {
            count = query.getInt(0);
        }

        for(int i = 0; i <= count; i++)
        {
            Cursor query2 = db.rawQuery("SELECT sightName, latitude, longitude FROM sights WHERE id = " + i + " ;", null);
            while (query2.moveToNext()) {
                name = query2.getString(0);
                latitude = query2.getDouble(1);
                longitude = query2.getDouble(2);
                sightMark = new LatLng(latitude, longitude);
                map.addMarker(new MarkerOptions().position(sightMark).title(name));
            }
        }
    }

    public void back(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}