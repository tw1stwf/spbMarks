package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        MainActivity.this.finish();
        System.exit(0);
    }

    public void goToSights (View view)
    {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void aboutUs (View view)
    {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

}