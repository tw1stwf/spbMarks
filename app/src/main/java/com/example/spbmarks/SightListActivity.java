package com.example.spbmarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SightListActivity extends AppCompatActivity implements SightAdapter.OnSightListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Sight> mSightList;

    private RecyclerView mRecyclerView;
    private SightAdapter mAdapter;
    private EditText editText;
    private BottomNavigationView bottomNav;
    private RecyclerView.LayoutManager mLayoutManager;

    private int count;
    private String sightType;
    private boolean favoritePageSelected = false;

    private String text;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sightlist);

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(this);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        createExampleList();
        buildRecyclerView();

        editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(favoritePageSelected == false) {
                    filter(s.toString());
                    if (editText.length() == 0) {
                        createExampleList();
                        buildRecyclerView();
                    }
                    else {
                        createSearchList();
                        buildRecyclerView();
                    }
                }

                if(favoritePageSelected == true)
                {
                    filter(s.toString());
                    if (editText.length() == 0) {
                        createFavouriteList();
                        buildRecyclerView();
                    }
                    else {
                        createSearchList();
                        buildRecyclerView();
                    }
                }
            }
        });
    }


    private void filter(String txt) {
        text = txt;
    }

    private void createSearchList()
    {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        String currentLang = getString(R.string.language);
        String isRusLanguageSelected = "ru";

        sightType = getIntent().getStringExtra("type");

        mSightList = new ArrayList<>();


        if(currentLang.equals(isRusLanguageSelected)) {
            if (favoritePageSelected == false) {
                Cursor query2 = db.rawQuery("SELECT  * FROM sights WHERE sightName LIKE  '%" + text + "%' and type = '" + sightType + "';", null);
                while (query2.moveToNext()) {
                    mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), query2.getInt(5) > 0, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getString(12)));
                }
            }

            if (favoritePageSelected == true) {
                Cursor query2 = db.rawQuery("SELECT  * FROM sights WHERE sightName LIKE  '%" + text + "%' and type = '" + sightType + "' and stared = 1;", null);
                while (query2.moveToNext()) {
                    mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), query2.getInt(5) > 0, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getString(12)));
                }
            }
        }

        else{
            if (favoritePageSelected == false) {
                Cursor query2 = db.rawQuery("SELECT  * FROM sights_en WHERE sightName LIKE  '%" + text + "%' and type = '" + sightType + "';", null);
                while (query2.moveToNext()) {
                    mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), query2.getInt(5) > 0, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getString(12)));
                }
            }

            if (favoritePageSelected == true) {
                Cursor query2 = db.rawQuery("SELECT  * FROM sights_en WHERE sightName LIKE  '%" + text + "%' and type = '" + sightType + "' and stared = 1;", null);
                while (query2.moveToNext()) {
                    mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), query2.getInt(5) > 0, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getString(12)));
                }
            }
        }
    }

    private void createExampleList() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        String currentLang = getString(R.string.language);
        String isRusLanguageSelected = "ru";

        sightType = getIntent().getStringExtra("type");

        mSightList = new ArrayList<>();

        if(currentLang.equals(isRusLanguageSelected))
        {
            Cursor query2 = db.rawQuery("SELECT * FROM sights WHERE type LIKE '" + sightType + "';", null);

                while (query2.moveToNext()) {
                    mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), query2.getInt(5) > 0, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getString(12)));
                }
        }

        else
        {
            Cursor query2 = db.rawQuery("SELECT * FROM sights_en  WHERE type LIKE '" + sightType + "';", null);

                while (query2.moveToNext()) {
                    mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), query2.getInt(5) > 0, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getString(12)));
                }
        }

    }

    private void createFavouriteList() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        String currentLang = getString(R.string.language);
        String isRusLanguageSelected = "ru";

        sightType = getIntent().getStringExtra("type");

        mSightList = new ArrayList<>();

        if(currentLang.equals(isRusLanguageSelected))
        {
            Cursor query2 = db.rawQuery("SELECT * FROM sights WHERE type LIKE '" + sightType + "' and stared = 1;", null);

            while (query2.moveToNext()) {
                mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), query2.getInt(5) > 0, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getString(12)));
            }

        }

        else
        {
            Cursor query2 = db.rawQuery("SELECT * FROM sights_en  WHERE type LIKE '" + sightType + "' and stared = 1;", null);

            while (query2.moveToNext()) {
                mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), query2.getInt(5) > 0, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getString(12)));
            }
        }
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SightAdapter(mSightList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSightClick(int position) {
        Intent intent = new Intent(this, SightInDetailActivity.class);

        intent.putExtra("id", mSightList.get(position).getId());
        intent.putExtra("image", mSightList.get(position).getImageResource());
        intent.putExtra("name", mSightList.get(position).getSightName());
        intent.putExtra("location", mSightList.get(position).getLocation());
        intent.putExtra("stared", mSightList.get(position).getStar());
        intent.putExtra("disc", mSightList.get(position).getDisc());
        intent.putExtra("arch", mSightList.get(position).getArchitect());
        intent.putExtra("year", mSightList.get(position).getDateOfBuild());
        intent.putExtra("latitude", mSightList.get(position).getLatitude());
        intent.putExtra("longitude", mSightList.get(position).getLongitude());
        intent.putExtra("website", mSightList.get(position).getWebsite());
        intent.putExtra("type", mSightList.get(position).getType());

        startActivity(intent);
        finish();
    }

    public void back (View view)
    {
        Intent intent = new Intent(this, SightType.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.main_page:
                favoritePageSelected = false;
                createExampleList();
                buildRecyclerView();
                editText.setText("");
                return true;

            case R.id.favorite_page:
                favoritePageSelected = true;
                createFavouriteList();
                buildRecyclerView();
                editText.setText("");
                return true;
        }

        return false;
    }
}