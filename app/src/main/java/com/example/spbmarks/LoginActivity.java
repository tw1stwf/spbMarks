package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textBoxLogin, textBoxPassword;
    private String login, password;
    private Toast toast;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textBoxLogin = findViewById(R.id.textBoxGetLogin);
        textBoxPassword = findViewById(R.id.textBoxGetPassword);
    }

    public void login(View view)
    {
        login = textBoxLogin.getEditText().getText().toString();
        password = textBoxPassword.getEditText().getText().toString();

        if(textBoxLogin.getEditText().getText().length() > 0 && textBoxPassword.getEditText().getText().length() > 0) {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            Cursor query = db.rawQuery("SELECT count(*) as count FROM users WHERE password = '" + password + "' and login = '" + login + "';", null);

            while (query.moveToNext())
                id = query.getInt(0);

                if(id > 0) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                if(id==0)
                {
                    toast = Toast.makeText(getApplicationContext(), "Невернные данные", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }

        else
        {
            toast = Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_SHORT);
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
}