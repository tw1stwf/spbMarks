package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SightType extends AppCompatActivity {

    public static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_type);

        userId = getIntent().getIntExtra("userId", 0);
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
        ((MyApplication) this.getApplication()).setType(3);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void museumList(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        ((MyApplication) this.getApplication()).setType(2);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void otherList(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        ((MyApplication) this.getApplication()).setType(4);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void cathedralList(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        ((MyApplication) this.getApplication()).setType(1);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void favourites(View view)
    {
        Intent intent = new Intent(this, SightListActivity.class);
        ((MyApplication) this.getApplication()).setType(0);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }
}