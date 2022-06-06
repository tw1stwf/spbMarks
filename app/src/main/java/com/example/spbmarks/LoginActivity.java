package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.security.MessageDigest;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textBoxLogin, textBoxPassword;
    private String login, password;
    private Toast toast;
    private int id, ids;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textBoxLogin = findViewById(R.id.textBoxGetLogin);
        textBoxPassword = findViewById(R.id.textBoxGetPassword);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, login TEXT NOT NULL, password TEXT NOT NULL, email TEXT NOT NULL, UNIQUE(id))");
    }

    public void login(View view)
    {
        String currentLang = getString(R.string.language);
        String isRusLanguageSelected = "ru";

        login = textBoxLogin.getEditText().getText().toString();
        password = textBoxPassword.getEditText().getText().toString();

        String hashedPassword = md5(password);

        if(textBoxLogin.getEditText().getText().length() > 0 && textBoxPassword.getEditText().getText().length() > 0) {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            Cursor query = db.rawQuery("SELECT id FROM users WHERE password = '" + hashedPassword + "' and login = '" + login + "';", null);

            while (query.moveToNext())
                id = query.getInt(0);

                if(id > 0) {
                    Intent intent = new Intent(this, MainActivity.class);
                    ((MyApplication) this.getApplication()).setUserId(id);
                    startActivity(intent);
                    finish();
                }

                if(id==0)
                {
                    if(currentLang.equals(isRusLanguageSelected)) {
                        toast = Toast.makeText(getApplicationContext(), "Невернные данные", Toast.LENGTH_SHORT);
                    }

                    else {
                        toast = Toast.makeText(getApplicationContext(), "Incorrect data", Toast.LENGTH_SHORT);
                    }
                    toast.show();
                }
        }

        else
        {
            if(currentLang.equals(isRusLanguageSelected)) {
                toast = Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_SHORT);
            }

            else
            {
                toast = Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT);
            }

            toast.show();
        }

    }

    public void exit (View view)
    {
        finishAffinity();
        System.exit(0);
    }

    public void registration(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public static final String md5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return "";
        }
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

    public void languageEn(View view)
    {
        setAppLocale("en");
        startActivity(getIntent());
    }

    public void languageRu(View view)
    {
        setAppLocale("ru");
        startActivity(getIntent());
    }

    public void guest(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        ((MyApplication) this.getApplication()).setUserId(0);
        startActivity(intent);
        finish();
    }
}