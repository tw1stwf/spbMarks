package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton buttonRu, buttonEn;

    public boolean isLanguageEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEn = findViewById(R.id.imageButtonRu);
        buttonRu = findViewById(R.id.imageButtonEn);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS favorites (id INTEGER, isFav BOOLEAN, UNIQUE(id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS sights (id INTEGER, image BLOB, sightName TEXT, metro TEXT, location TEXT, stared BOOLEAN, dateOfBuild TEXT, discription BLOB, architect TEXT, latitude REAL, longitude REAL, website TEXT, UNIQUE(id))");

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

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (1, " + draw_1 + ", 'Казанский собор' , 'Невский проспект.', 'Адрес: Казанская пл., 2', '', '1811 г.'," + dr_1 + ", 'Андрей Никифорович Воронихин', 59.9347713, 30.3248576, 'http://kazansky-spb.ru/')");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (2, " + draw_2 + ", 'Исакиевский собор' , 'Адмиралтейская.', 'Адрес: Исаакиевская пл., 4', '', '1858 г.'," + dr_2 + ", 'Огюст Монферран', 59.933489, 30.3067315, 'http://cathedral.ru/')");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (3, " + draw_3 + ", 'Эрмитаж' , 'Адмиралтейская.', 'Адрес: Дворцовая пл., 2', '', '1764 г.'," + dr_3 + ", 'Бартоломео Растрелли', 59.9396108, 30.3149881, 'https://www.hermitagemuseum.org/')");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (4, " + draw_4 + ", 'Медный всадник' , 'Адмиралтейская.', 'Адрес: Сенатская пл.', '', '1782 г.'," + dr_4 + ", 'Этьен Морис Фальконе', 59.9363632, 30.3019047, 'https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D0%B4%D0%BD%D1%8B%D0%B9_%D0%B2%D1%81%D0%B0%D0%B4%D0%BD%D0%B8%D0%BA')");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (5, " + draw_5 + ", 'Спас на крови' , 'Невский проспект.', 'Адрес: наб. канала Грибоедова, 2Б', '', '1907 г.'," + dr_5 + ", 'Альфред Александрович Парланд', 59.9397094, 30.3287002, 'http://spas.spb.ru/')");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (6, " + draw_6 + ", 'Михайловский замок' , 'Гостинный двор.', 'Адрес: Садовая ул., 2', '', '1800 г.'," + dr_6 + ", 'Винченцо Бренна', 59.9395817,  30.3381481, 'https://www.rusmuseum.ru/mikhailovsky-castle/')");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (7, " + draw_7 + ", 'Русский музей' , 'Невский проспект.', 'Адрес: Инженерная ул., 4', '', '1895 г.'," + dr_7 + ", 'Карло Росси', 59.9380167, 30.3320391, 'https://www.rusmuseum.ru/')");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (8, " + draw_8 + ", 'Петропавловская крепость' , 'Горьковская.', 'Адрес: Заячий остров', '', '1740 г.'," + dr_8 + ", 'Доменико Трезини', 59.9499811, 30.3157435, 'https://www.spbmuseum.ru/themuseum/museum_complex/peterpaul_fortress/')");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (9, " + draw_9 + ", 'Кунсткамера' , 'Адмиралтейская.', 'Адрес: Университетская наб., 3', '', '1714 г.'," + dr_9 + ", 'Георг Иоганн Маттарнови', 59.9413583, 30.3046383, 'https://www.kunstkamera.ru/')");
        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website) VALUES (10, " + draw_10 + ", 'Мариинский театр' , 'Садовая.', 'Адрес: Театральная пл., 1', '', '1714 г.'," + dr_10 + ", 'Альберт Катеринович Кавос', 59.9258033,  30.2970917, 'https://www.mariinsky.ru/')");

    }

    private void setAppLocale(String localeCode)
    {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            conf.locale = new Locale(localeCode.toLowerCase());

        }
        res.updateConfiguration(conf, dm);
    }

    public void exit (View v)
    {
        finishAffinity();
        System.exit(0);
    }

    public void goToSights (View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        intent.putExtra("language", isLanguageEn);
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

    public void languageEn(View view)
    {
        setAppLocale("en");
        finish();
        startActivity(getIntent());
        isLanguageEn = true;
    }

    public void languageRu(View view)
    {
        setAppLocale("ru");
        finish();
        startActivity(getIntent());
        isLanguageEn = false;
    }
}