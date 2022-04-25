package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
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
        db.execSQL("CREATE TABLE IF NOT EXISTS favorites (id INTEGER, isFav BOOLEAN, UNIQUE(id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS sights (id INTEGER, image BLOB, sightName TEXT, metro TEXT, location TEXT, stared BOOLEAN, dateOfBuild TEXT, discription BLOB, architect TEXT, latitude REAL, longitude REAL, UNIQUE(id))");

        int dr_1 = R.string.kazanDisc;
        int dr_2 = R.string.isacDisc;
        int dr_3= R.string.discHermit;
        int dr_4= R.string.vsadnicDisc;
        int dr_5= R.string.spasDisc;
        int dr_6= R.string.zamokDisc;
        int dr_7= R.string.rusDisc;
        int dr_8= R.string.petropDisc;
        int dr_9= R.string.kuntsDisc;
        int dr_10= R.string.marinksDisc;

        int draw_2 = R.drawable.isac;
        int draw_1 = R.drawable.kazan;
        int draw_3 = R.drawable.hermit;
        int draw_4 = R.drawable.vsadnik;
        int draw_5 = R.drawable.spas;
        int draw_6= R.drawable.zamok;
        int draw_7 = R.drawable.rusmus;
        int draw_8= R.drawable.petrop;
        int draw_9 = R.drawable.kunts;
        int draw_10= R.drawable.marink;

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (1, " + draw_1 + ", 'Казанский собор' , 'Невский проспект.', 'Адрес: Казанская пл., 2', '', '1811 г.'," + dr_1 + ", 'Андрей Никифорович Воронихин', 59.934443, 30.324701)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (2, " + draw_2 + ", 'Исакиевский собор' , 'Адмиралтейская.', 'Адрес: Исаакиевская пл., 4', '', '1858 г.'," + dr_2 + ", 'Огюст Монферран', 59.93409, 30.30614)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (3, " + draw_3 + ", 'Эрмитаж' , 'Адмиралтейская.', 'Адрес: Дворцовая пл., 2', '', '1764 г.'," + dr_3 + ", 'Бартоломео Растрелли', 59.93986, 30.3146)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (4, " + draw_4 + ", 'Медный всадник' , 'Адмиралтейская.', 'Адрес: Сенатская пл.', '', '1782 г.'," + dr_4 + ", 'Этьен Морис Фальконе', 59.93639, 30.30218)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (5, " + draw_5 + ", 'Спас на крови' , 'Невский проспект.', 'Адрес: наб. канала Грибоедова, 2Б', '', '1907 г.'," + dr_5 + ", 'Альфред Александрович Парланд', 59.94006, 30.32882)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (6, " + draw_6 + ", 'Михайловский замок' , 'Гостинный двор.', 'Адрес: Садовая ул., 2', '', '1800 г.'," + dr_6 + ", 'Винченцо Бренна', 59.93999,  30.33801)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (7, " + draw_7 + ", 'Русский музей' , 'Невский проспект.', 'Адрес: Инженерная ул., 4', '', '1895 г.'," + dr_7 + ", 'Карло Росси', 59.93869, 30.3323)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (8, " + draw_8 + ", 'Петропавловская крепость' , 'Горьковская.', 'Адрес: Заячий остров', '', '1740 г.'," + dr_8 + ", 'Доменико Трезини', 59.95018, 30.31647)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (9, " + draw_9 + ", 'Кунсткамера' , 'Адмиралтейская.', 'Адрес: Университетская наб., 3', '', '1714 г.'," + dr_9 + ", 'Георг Иоганн Маттарнови', 59.94154, 30.30454)");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude) VALUES (10, " + draw_10 + ", 'Мариинский театр' , 'Садовая.', 'Адрес: Театральная пл., 1', '', '1714 г.'," + dr_10 + ", 'Альберт Катеринович Кавос', 59.92577,  30.29645)");

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

    public void onMap (View view)
    {
        Intent intent = new Intent(this, SightOnMapActivity.class);
        startActivity(intent);
    }

}