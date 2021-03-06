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
import android.view.ViewGroup;
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
    private RecyclerView recyclerView;

    private int sightId, isStared;
    private int sightType;
    private boolean favoritePageSelected = false;
    private String text;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sightlist);

        bottomNav = findViewById(R.id.bottomNav);
        recyclerView = findViewById(R.id.recyclerView);
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

        sightType = ((MyApplication) this.getApplication()).getType();

        userId = ((MyApplication) this.getApplication()).getUserId();

        mSightList = new ArrayList<>();

        if(currentLang.equals(isRusLanguageSelected)) {

            if(sightType == 0)
            {
                Cursor query2 = db.rawQuery("SELECT * FROM sights WHERE sightName LIKE  '%" + text + "%';", null);

                while (query2.moveToNext()) {
                    sightId = query2.getInt(0);

                    Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                    while (getStared.moveToNext()) {
                        isStared = getStared.getInt(0);
                    }

                    if(isStared != 0)
                        mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                }
            }

            else {

                if (favoritePageSelected == false) {
                    Cursor query2 = db.rawQuery("SELECT * FROM sights WHERE sightName LIKE  '%" + text + "%' and type = " + sightType + " ;", null);

                    while (query2.moveToNext()) {
                        sightId = query2.getInt(0);

                        Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                        while (getStared.moveToNext()) {
                            isStared = getStared.getInt(0);
                        }

                        if (isStared == 0) {
                            mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), false, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                        }

                        else {
                            mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                        }
                    }
                }

                if (favoritePageSelected == true) {
                    Cursor query2 = db.rawQuery("SELECT * FROM sights WHERE sightName LIKE  '%" + text + "%' and type = " + sightType + " ;", null);

                    while (query2.moveToNext()) {
                        sightId = query2.getInt(0);

                        Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                        while (getStared.moveToNext()) {
                            isStared = getStared.getInt(0);
                        }

                        if (isStared != 0) {
                            mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                        }
                    }
                }
            }
        }

        else {

            if(sightType == 0)
            {
                Cursor query2 = db.rawQuery("SELECT * FROM sights_en WHERE sightName LIKE  '%" + text + "%';", null);

                while (query2.moveToNext()) {
                    sightId = query2.getInt(0);

                    Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                    while (getStared.moveToNext()) {
                        isStared = getStared.getInt(0);
                    }

                    if(isStared != 0)
                        mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                }
            }

            else {
                if (favoritePageSelected == false) {
                    Cursor query2 = db.rawQuery("SELECT * FROM sights_en WHERE sightName LIKE  '%" + text + "%' and type = " + sightType + " ;", null);

                    while (query2.moveToNext()) {
                        sightId = query2.getInt(0);

                        Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                        while (getStared.moveToNext()) {
                            isStared = getStared.getInt(0);
                        }

                        if (isStared == 0) {
                            mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), false, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                        }

                        else {
                            mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                        }
                    }
                }

                if (favoritePageSelected == true) {
                    Cursor query2 = db.rawQuery("SELECT * FROM sights_en WHERE sightName LIKE  '%" + text + "%' and type = " + sightType + " ;", null);

                    while (query2.moveToNext()) {
                        sightId = query2.getInt(0);

                        Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                        while (getStared.moveToNext()) {
                            isStared = getStared.getInt(0);
                        }

                        if (isStared != 0) {
                            mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                        }
                    }
                }
            }
        }
    }

    private void createExampleList() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        String currentLang = getString(R.string.language);
        String isRusLanguageSelected = "ru";

        userId = ((MyApplication) this.getApplication()).getUserId();

        sightType = ((MyApplication) this.getApplication()).getType();

        mSightList = new ArrayList<>();

        if(currentLang.equals(isRusLanguageSelected))
        {
            if(sightType == 0)
            {
                Cursor query2 = db.rawQuery("SELECT * FROM sights;", null);

                while (query2.moveToNext()) {
                    sightId = query2.getInt(0);

                    Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                    while (getStared.moveToNext()) {
                        isStared = getStared.getInt(0);
                    }

                    if(isStared != 0)
                        mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                    }

                bottomNav.setVisibility(View.INVISIBLE);
                ViewGroup.LayoutParams params= recyclerView.getLayoutParams();
                params.height=2100;
                recyclerView.setLayoutParams(params);
            }

            else {

                Cursor query2 = db.rawQuery("SELECT * FROM sights, types WHERE type=type_id and type LIKE " + sightType + ";", null);

                while (query2.moveToNext()) {
                    sightId = query2.getInt(0);

                    Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                    while (getStared.moveToNext()) {
                        isStared = getStared.getInt(0);
                    }

                    if(isStared == 0) {
                        mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), false, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                    }

                    else {
                        mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                    }

                }

                bottomNav.setVisibility(View.VISIBLE);
            }
        }

        else
        {
            if(sightType == 0)
            {
                if(sightType == 0) {
                    Cursor query2 = db.rawQuery("SELECT * FROM sights_en;", null);

                    while (query2.moveToNext()) {
                        sightId = query2.getInt(0);

                        Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                        while (getStared.moveToNext()) {
                            isStared = getStared.getInt(0);
                        }

                        if (isStared != 0)
                            mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                    }
                }

                bottomNav.setVisibility(View.INVISIBLE);
                ViewGroup.LayoutParams params= recyclerView.getLayoutParams();
                params.height=2100;
                recyclerView.setLayoutParams(params);
            }

            else
            {
                Cursor query2 = db.rawQuery("SELECT * FROM sights_en, types WHERE type=type_id and type LIKE " + sightType + ";", null);

                while (query2.moveToNext()) {
                    sightId = query2.getInt(0);

                    Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                    while (getStared.moveToNext()) {
                        isStared = getStared.getInt(0);
                    }

                    if (isStared == 0) {
                        mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), false, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                    }

                    else {
                        mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                    }
                }

                bottomNav.setVisibility(View.VISIBLE);
            }
        }

    }

    private void createFavouriteList() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        String currentLang = getString(R.string.language);
        String isRusLanguageSelected = "ru";

        sightType = ((MyApplication) this.getApplication()).getType();

        userId = ((MyApplication) this.getApplication()).getUserId();

        mSightList = new ArrayList<>();

        if(currentLang.equals(isRusLanguageSelected))
        {
            Cursor query2 = db.rawQuery("SELECT * FROM sights, types WHERE type=type_id and type LIKE " + sightType + ";", null);

            while (query2.moveToNext()) {
                sightId = query2.getInt(0);

                Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                while (getStared.moveToNext()) {
                    isStared = getStared.getInt(0);
                }

                if (isStared != 0)
                    mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
                }
        }

        else
        {
            Cursor query2 = db.rawQuery("SELECT * FROM sights_en, types WHERE type=type_id and type LIKE " + sightType + ";", null);

            while (query2.moveToNext()) {
                sightId = query2.getInt(0);

                Cursor getStared = db.rawQuery("SELECT count(*) as count FROM favourites WHERE user_id = " + userId + " and sight_id = " + sightId + ";", null);

                while (getStared.moveToNext()) {
                    isStared = getStared.getInt(0);
                }

                if (isStared != 0)
                    mSightList.add(new Sight(query2.getInt(0), query2.getInt(1), query2.getString(2), query2.getString(3), query2.getString(4), true, query2.getString(6), query2.getString(7), query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11), query2.getInt(12), query2.getString(13), query2.getString(14), query2.getString(15), query2.getString(16)));
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
        intent.putExtra("openTime", mSightList.get(position).getOpenTime());
        intent.putExtra("closeTime", mSightList.get(position).getCloseTime());
        intent.putExtra("price", mSightList.get(position).getPrice());
        intent.putExtra("priceKids", mSightList.get(position).getPriceKids());

        intent.putExtra("userId", userId);

        if(sightType == 0)
        {
            intent.putExtra("type", 0);
        }

        else
        {
            intent.putExtra("type", mSightList.get(position).getType());
        }

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