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

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        int dr_1 = R.string.kazanDisc;
        int dr_2 = R.string.isacDisc;
        int dr_3= R.string.discHermit;
        int dr_4= R.string.vsadnicDisc;
        int dr_5= R.string.spasDisc;

        int draw_2 = R.drawable.isac;
        int draw_1 = R.drawable.kazan;
        int draw_3 = R.drawable.hermit;
        int draw_4 = R.drawable.vsadnik;
        int draw_5 = R.drawable.spas;

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (1, " + draw_1 + ", 'Казанский собор' , 'Невский проспект.', 'Адрес: Казанская пл., 2', '', '1811 г.'," + dr_1 + ", 'Андрей Никифорович Воронихин', 59.934443, 30.324701)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (2, " + draw_2 + ", 'Исакиевский собор' , 'Адмиралтейская.', 'Адрес: Исаакиевская пл., 4', '', '1858 г.'," + dr_2 + ", 'Огюст Монферран', 59.93409, 30.30614)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (3, " + draw_3 + ", 'Эрмитаж' , 'Адмиралтейская.', 'Адрес: Дворцовая пл., 2', '', '1764 г.'," + dr_3 + ", 'Бартоломео Растрелли', 59.93986, 30.3146)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (4, " + draw_4 + ", 'Медный всадник' , 'Адмиралтейская.', 'Адрес: Сенатская пл.', '', '1782 г.'," + dr_3 + ", 'Этьен Морис Фальконе', 59.93639, 30.30218)");

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

}