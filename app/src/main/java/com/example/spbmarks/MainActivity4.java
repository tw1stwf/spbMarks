package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity4 extends AppCompatActivity {

    private ImageView imageViewReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        imageViewReceipt = (ImageView) findViewById(R.id.image);

        Intent intent = getIntent();

        int image = getIntent().getIntExtra("image", 0);

        imageViewReceipt.setImageResource(image);
    }
}