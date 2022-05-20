package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.security.MessageDigest;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout textBoxLogin, textBoxPassword, textBoxPasswordRepeat, textBoxEmail;
    private String login, password, email, passwordRepeat;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textBoxLogin = findViewById(R.id.textBoxGetLoginReg);
        textBoxPassword = findViewById(R.id.textBoxGetPasswordReg2);
        textBoxPasswordRepeat = findViewById(R.id.textBoxGetPasswordReg);
        textBoxEmail = findViewById(R.id.textBoxGetEmailReg);
    }

    public void confirmReg(View view)
    {
        String currentLang = getString(R.string.language);
        String isRusLanguageSelected = "ru";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        login = textBoxLogin.getEditText().getText().toString();
        password = textBoxPassword.getEditText().getText().toString();
        email = textBoxEmail.getEditText().getText().toString();
        passwordRepeat = textBoxPasswordRepeat.getEditText().getText().toString();

        if(textBoxLogin.getEditText().getText().length() > 0 &&  textBoxPassword.getEditText().getText().length() > 0 && textBoxPasswordRepeat.getEditText().getText().length() > 0 && textBoxEmail.getEditText().getText().length() > 0)
        {
            if(password.equals(passwordRepeat) && email.matches(emailPattern)) {

                String hashPassword = md5(password);
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                db.execSQL("INSERT INTO users(login, password, email) values ('" + login + "', '" + hashPassword + "', '" + email + "')");

                if(currentLang.equals(isRusLanguageSelected)) {
                    toast = Toast.makeText(getApplicationContext(), "Пользователь " + login + " успешно зарегистрирован", Toast.LENGTH_SHORT);
                }

                else
                {
                    toast = Toast.makeText(getApplicationContext(), "User " + login + " successfully registered", Toast.LENGTH_SHORT);
                }

                toast.show();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            else
            {
                if(currentLang.equals(isRusLanguageSelected)) {
                    toast = Toast.makeText(getApplicationContext(), "Проверьте правильность ввода данных", Toast.LENGTH_SHORT);
                }

                else
                {
                    toast = Toast.makeText(getApplicationContext(), "Check the correctness of the data entered", Toast.LENGTH_SHORT);
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

    public void back(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
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
}