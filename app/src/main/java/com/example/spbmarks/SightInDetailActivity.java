package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
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
    private TextView openTime, price, priceKids, sightName, discription, yearOfBuild, architector, adress;
    private View streetpanorama;
    private ScrollView scrollView;
    private CardView cardView;
    private GoogleMap map;
    private LatLng sightMark;

    private int id;
    private boolean isFav, starPressed = false;
    private double latitude, longitude;
    private String name, website;
    private static int type;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sightdetail);

        imageViewReceipt = findViewById(R.id.image);
        star = findViewById(R.id.imageButtonStar);
        sightName = findViewById(R.id.textViewName);
        discription = findViewById(R.id.textViewDescription);
        yearOfBuild = findViewById(R.id.textViewDateOfBuild);
        architector = findViewById(R.id.textViewArchitect);
        adress = findViewById(R.id.textViewAdress);
        streetpanorama = findViewById(R.id.streetviewpanorama2);
        scrollView = findViewById(R.id.scrollview);
        cardView =  findViewById(R.id.cardView);
        openTime = findViewById(R.id.textViewOpenTime);
        price = findViewById(R.id.textViewPrice);
        priceKids = findViewById(R.id.textViewPriceKids);

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

        id = getIntent().getIntExtra("id", 0);
        name = getIntent().getStringExtra("name");
        int image = getIntent().getIntExtra("image", 0);

        String location = getIntent().getStringExtra("location");
        String arch = getIntent().getStringExtra("arch");
        String year = getIntent().getStringExtra("year");
        String disc = getIntent().getStringExtra("disc");
        String opentime = getIntent().getStringExtra("openTime");
        String closetime = getIntent().getStringExtra("closeTime");
        String priceadult = getIntent().getStringExtra("price");
        String pricekids = getIntent().getStringExtra("priceKids");

        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        website = getIntent().getStringExtra("website");
        type = getIntent().getIntExtra("type", 0);

        imageViewReceipt.setImageResource(image);
        sightName.setText(name);

        yearOfBuild.append(year);
        architector.append(arch);
        adress.append(location);
        discription.setText(disc);

        openTime.append(opentime + " - " + closetime);
        price.append(priceadult);
        priceKids.append(pricekids);

        userId = ((MyApplication) this.getApplication()).getUserId();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT count(*) FROM favourites WHERE sight_id = " + id + " and user_id = " + userId + ";", null);

        while (query.moveToNext()) {
            int fav = query.getInt(0);

            if (fav != 0)
            {
                star.setColorFilter(Color.argb(255, 205, 201, 112));
                isFav = false;
            }

            else
            {
                isFav = true;
            }
        }

        query.close();
        db.close();
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

        int userId = ((MyApplication) this.getApplication()).getUserId();

        if(userId > 0) {
            if (isFav) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

                db.execSQL("INSERT INTO favourites(sight_id, user_id) VALUES (" + id + ", " + userId + ")");

                star.setColorFilter(Color.argb(255, 205, 201, 112));
                Toast toast;

                if (currentLang.equals(isRusLanguageSelected)) {
                    toast = Toast.makeText(getApplicationContext(), "Добавлено в избранное", Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(getApplicationContext(), "Added to favourites", Toast.LENGTH_SHORT);
                }

                toast.show();
                db.close();

                isFav = false;
            } else {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

                db.execSQL("DELETE FROM favourites WHERE sight_id = " + id + " and user_id = " + userId + ";");

                star.setColorFilter(Color.argb(255, 151, 151, 151));
                Toast toast;

                if (currentLang.equals(isRusLanguageSelected)) {
                    toast = Toast.makeText(getApplicationContext(), "Удалено из избранного", Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(getApplicationContext(), "Delete from favourites", Toast.LENGTH_SHORT);
                }

                toast.show();
                db.close();

                isFav = true;
            }

        }

        else
        {
            Toast toast;
            if (currentLang.equals(isRusLanguageSelected)) {
                toast = Toast.makeText(getApplicationContext(), "Недоступно в режиме гостя", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "Not available in guest mode", Toast.LENGTH_SHORT);
            }
            toast.show();
        }
    }

    public void back(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        type = ((MyApplication) this.getApplication()).getType();
        intent.putExtra("type", type);
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