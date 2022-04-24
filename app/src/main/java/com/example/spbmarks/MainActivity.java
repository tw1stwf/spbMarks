package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exit (View v)
    {
        finishAffinity();
        System.exit(0);
    }

    public void goToSights (View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        startActivity(intent);
    }

    public void aboutUs (View view)
    {
        Intent intent = new Intent(this, AboutAppActivity.class);
        startActivity(intent);
    }

    public void insert(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        int dr_1 = R.string.kazanDisc;
        int draw_1 = R.drawable.kazan;
        int dr_2 = R.string.isacDisc;
        int draw_2 = R.drawable.isac;
        int dr_3= R.string.discHermit;
        int draw_3 = R.drawable.hermit;
        int dr_4= R.string.vsadnicDisc;
        int draw_4 = R.drawable.vsadnik;
        int dr_5= R.string.spasDisc;
        int draw_5 = R.drawable.spas;
        db.execSQL("INSERT INTO sights(discription, image) VALUES (" + dr_1 + ", " + draw_1 + ")");
        db.execSQL("INSERT INTO sights(discription, image) VALUES (" + dr_2 + ", " + draw_2 + ")");
        db.execSQL("INSERT INTO sights(discription, image) VALUES (" + dr_3 + ", " + draw_3 + ")");
        db.execSQL("INSERT INTO sights(discription, image) VALUES (" + dr_4 + ", " + draw_4 + ")");
        db.execSQL("INSERT INTO sights(discription, image) VALUES (" + dr_5 + ", " + draw_5 + ")");
    }
}