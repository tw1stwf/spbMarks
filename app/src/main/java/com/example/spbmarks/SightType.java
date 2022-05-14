package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SightType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_type);
    }

    public void back(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void sightList(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        intent.putExtra("type", "sight");
        startActivity(intent);
        finish();
    }

    public void museumList(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        intent.putExtra("type", "museum");
        startActivity(intent);
        finish();
    }

    public void otherList(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        intent.putExtra("type", "other");
        startActivity(intent);
        finish();
    }

    public void cathedralList(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        intent.putExtra("type", "cathedral");
        startActivity(intent);
        finish();
    }
}