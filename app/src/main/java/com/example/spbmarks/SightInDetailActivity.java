package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SightInDetailActivity extends AppCompatActivity implements OnMapReadyCallback, OnStreetViewPanoramaReadyCallback {

    private ImageView imageViewReceipt;
    private ImageButton star;
    private TextView sightName;
    private TextView discription;
    private TextView yearOfBuild;
    private TextView architector;
    private TextView adress;
    private View streetpanorama;
    private ScrollView scrollView;
    private CardView cardView;

    private GoogleMap map;
    private LatLng sightMark;

    private int pos;
    private boolean isFav;
    private boolean starPressed = false;
    private double latitude, longitude;
    private String name;
    private String website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sightdetail);

        imageViewReceipt = (ImageView) findViewById(R.id.image);
        star = (ImageButton) findViewById(R.id.imageButtonStar);
        sightName = (TextView) findViewById(R.id.textViewName);
        discription = (TextView) findViewById(R.id.textViewDescription);
        yearOfBuild = (TextView) findViewById(R.id.textViewDateOfBuild);
        architector = (TextView) findViewById(R.id.textViewArchitect);
        adress = (TextView) findViewById(R.id.textViewAdress);
        streetpanorama = (View) findViewById(R.id.streetviewpanorama2);
        scrollView = (ScrollView) findViewById(R.id.scrollview);
        cardView = (CardView) findViewById(R.id.cardView);

        streetpanorama.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama2);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        int image = getIntent().getIntExtra("image", 0);
        pos = getIntent().getIntExtra("position", 0) + 1;
        name = getIntent().getStringExtra("name");
        String location = getIntent().getStringExtra("location");

        String arch = getIntent().getStringExtra("arch");
        String year = getIntent().getStringExtra("year");
        String disc = getIntent().getStringExtra("disc");

        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        website = getIntent().getStringExtra("website");

        imageViewReceipt.setImageResource(image);
        sightName.setText(name);

        yearOfBuild.setText(getString(R.string.yearOfBuild) + " " + year);
        architector.setText(getString(R.string.architect) + " " + arch);
        adress.setText(location + "\n");
        discription.setText(disc);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("INSERT OR IGNORE INTO favorites VALUES ( " + pos + ", 0);");
        Cursor query = db.rawQuery("SELECT * FROM favorites WHERE id = " + pos + " ;", null);

        while(query.moveToNext()){
            isFav = query.getInt(1) > 0;

            if(isFav == true)
            {
                star.setColorFilter(Color.argb(255, 205, 201, 112));
            }
        }

        query.close();
        db.close();

        starPressed = false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        sightMark = new LatLng(latitude, longitude);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sightMark, 14));
        map.addMarker(new MarkerOptions().position(sightMark).title(name));
    }

    public void isStared (View view)
    {
        String currentLang = getString(R.string.language);
        String isRusLanguageSelected = "ru";

        if(isFav == false)
        {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            db.execSQL("UPDATE favorites SET isFav = 1 WHERE id = " + pos + ";");
            db.close();
            star.setColorFilter(Color.argb(255, 205, 201, 112));
            if(currentLang.equals(isRusLanguageSelected))
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Добавлено в избранное", Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Added to favourites", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if(isFav == true)
        {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            db.execSQL("UPDATE favorites SET isFav = 0 WHERE id = " + pos + ";");
            db.close();
            star.setColorFilter(Color.argb(255, 151, 151, 151));
            if(currentLang.equals(isRusLanguageSelected))
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Удалено из избранного", Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Delete from favourites", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM favorites WHERE id = " + pos + " ;", null);

        while(query.moveToNext()){
            isFav = query.getInt(1) > 0;
        }

        query.close();
        db.close();
    }

    public void back(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        startActivity(intent);
        finish();
    }

    public void panoramaView (View view)
    {
        streetpanorama.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);
    }

    public void backPanorama(View view)
    {
        streetpanorama.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);
    }

    public void url (View view)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
        startActivity(browserIntent);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        LatLng position = new LatLng(latitude, longitude);
        streetViewPanorama.setPosition(position);
    }
}