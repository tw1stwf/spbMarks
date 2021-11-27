package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {

    private ImageView imageViewReceipt;
    private ImageButton star;
    private TextView sightName;
    private TextView discription;
    private TextView yearOfBuild;
    private TextView architector;
    private TextView commentsView;
    private TextView adress;

    private EditText textName;
    private EditText textComment;

    private int pos;
    private boolean isFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        imageViewReceipt = (ImageView) findViewById(R.id.image);
        star = (ImageButton) findViewById(R.id.imageButtonStar);
        sightName = (TextView) findViewById(R.id.textViewName);
        discription = (TextView) findViewById(R.id.textViewDescription);
        yearOfBuild = (TextView) findViewById(R.id.textViewDateOfBuild);
        architector = (TextView) findViewById(R.id.textViewArchitect);
        commentsView = (TextView) findViewById(R.id.textViewRev);
        textName = (EditText) findViewById(R.id.editTextName);
        textComment = (EditText) findViewById(R.id.editTextComment);
        adress = (TextView) findViewById(R.id.textViewAdress);

        Intent intent = getIntent();
        int image = getIntent().getIntExtra("image", 0);
        pos = getIntent().getIntExtra("position", 0) + 1;
        String name = getIntent().getStringExtra("name");
        String location = getIntent().getStringExtra("location");

        String arch = getIntent().getStringExtra("arch");
        String year = getIntent().getStringExtra("year");
        int disc = getIntent().getIntExtra("disc", 0);

        imageViewReceipt.setImageResource(image);
        sightName.setText(name);

        yearOfBuild.setText("Год постройки: " + year);
        architector.setText("Архитектор: " + arch);
        adress.setText(location);
        discription.setText(disc);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("INSERT OR IGNORE INTO favorites VALUES ( " + pos + ", 0);");
        Cursor query = db.rawQuery("SELECT * FROM favorites WHERE id = " + pos + " ;", null);

        while(query.moveToNext()){
            isFav = query.getInt(1) > 0;

            if(isFav == true)
            {
                star.setColorFilter(Color.argb(255, 205, 201, 112));
            }
        }

        Cursor query2 = db.rawQuery("SELECT * FROM reviews WHERE sightId = " + pos + " ;", null);

        commentsView.setText("");
        while(query2.moveToNext()){
            String Name = query2.getString(1);
            String Comment = query2.getString(2);
            commentsView.append("Имя: " + Name + "\n" + "Комментарий: " + Comment + "\n");
        }

        query.close();
        query2.close();
        db.close();
    }

    public void isStared (View view)
    {
        if(isFav == false)
        {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            db.execSQL("UPDATE favorites SET isFav = 1 WHERE id = " + pos + ";");
            db.close();
            star.setColorFilter(Color.argb(255, 205, 201, 112));
        }

        if(isFav == true)
        {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            db.execSQL("UPDATE favorites SET isFav = 0 WHERE id = " + pos + ";");
            db.close();
            star.setColorFilter(Color.argb(255, 151, 151, 151));
        }

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM favorites WHERE id = " + pos + " ;", null);

        while(query.moveToNext()){
            isFav = query.getInt(1) > 0;

        }

        query.close();
        db.close();
    }

    public void back(View view)
    {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        finish();
    }

    public void addRev (View view)
    {
        String name = textName.getText().toString();
        String comment = textComment.getText().toString();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("INSERT OR IGNORE INTO reviews VALUES ( " + pos + ", '" + name +"', '" + comment +"');");

        Cursor query = db.rawQuery("SELECT * FROM reviews WHERE sightId = " + pos + " ;", null);
        commentsView.setText("");
        while(query.moveToNext()){
            String Name = query.getString(1);
            String Comment = query.getString(2);
            commentsView.append("Имя: " + Name + "\n" + "Комментарий: " + Comment + "\n");
        }

        db.close();
        query.close();
    }

}