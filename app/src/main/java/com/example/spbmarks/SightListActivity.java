package com.example.spbmarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SightListActivity extends AppCompatActivity implements SightAdapter.OnSightListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Sight> mSightList;
    private ArrayList<Sight> favoriteList;
    private ArrayList<Integer> idList = new ArrayList<Integer>();

    private RecyclerView mRecyclerView;
    private SightAdapter mAdapter;
    private EditText editText;
    private BottomNavigationView bottomNav;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isFiltred = false;
    private boolean favoritePageSelected = false;

    private static final String TAG = "MyApp";

    private int count;
    private boolean language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sightlist);

        bottomNav = (BottomNavigationView) findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(this);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        language = getIntent().getBooleanExtra("language", false);

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
                        isFiltred = false;
                    }
                }

                if(favoritePageSelected == true)
                {
                    favoriteSearch(s.toString());
                }
            }
        });
    }

    private void filter(String text) {
        ArrayList<Sight> filteredList = new ArrayList<>();
        idList.clear();

        for (Sight item : mSightList) {
            if (item.getSightName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
                idList.add(item.getId());
            }
        }
        
        mAdapter.filterList(filteredList);
        isFiltred = true;
    }

    private void createExampleList() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        if(language != true) {
            Cursor query3 = db.rawQuery("SELECT COUNT(*) FROM sights;", null);
            while (query3.moveToNext()) {
                count = query3.getInt(0);
            }
        }

        if(language == true)
        {

        }

        int listSize = count+1;
        boolean stared[] = new boolean[listSize];

        for(int i = 0; i < listSize; i++) {
            Cursor query = db.rawQuery("SELECT * FROM favorites WHERE id = " + i + " ;", null);

            while (query.moveToNext()) {
                boolean isFav = query.getInt(1) > 0;
                stared[i] = isFav;
            }
        }

        mSightList = new ArrayList<>();

        for(int i = 0; i <= count; i++)
        {
            Cursor query2 = db.rawQuery("SELECT * FROM sights WHERE id = " + i + " ;", null);

            while (query2.moveToNext()) {
                mSightList.add(new Sight(i,  query2.getInt(1) , query2.getString(2), query2.getString(3), query2.getString(4), stared[i], query2.getString(6), query2.getInt(7) , query2.getString(8), query2.getDouble(9), query2.getDouble(10), query2.getString(11)));
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

        if(isFiltred == false)
        {
            intent.putExtra("image", mSightList.get(position).getImageResource());
            intent.putExtra("position", position);
            intent.putExtra("name", mSightList.get(position).getSightName());
            intent.putExtra("location", mSightList.get(position).getLocation());
            intent.putExtra("disc", mSightList.get(position).getDisc());
            intent.putExtra("arch", mSightList.get(position).getArchitect());
            intent.putExtra("year", mSightList.get(position).getDateOfBuild());
            intent.putExtra("latitude", mSightList.get(position).getLatitude());
            intent.putExtra("longitude", mSightList.get(position).getLongitude());
            intent.putExtra("website", mSightList.get(position).getWebsite());
        }

        if(isFiltred == true)
        {
            intent.putExtra("image", mSightList.get(idList.get(position)-1).getImageResource());
            intent.putExtra("position", idList.get(position)-1);
            intent.putExtra("name", mSightList.get(idList.get(position)-1).getSightName());
            intent.putExtra("location", mSightList.get(idList.get(position)-1).getLocation());
            intent.putExtra("disc", mSightList.get(idList.get(position)-1).getDisc());
            intent.putExtra("arch", mSightList.get(idList.get(position)-1).getArchitect());
            intent.putExtra("year", mSightList.get(idList.get(position)-1).getDateOfBuild());
            intent.putExtra("latitude", mSightList.get(idList.get(position)-1).getLatitude());
            intent.putExtra("longitude", mSightList.get(idList.get(position)-1).getLongitude());
            intent.putExtra("website", mSightList.get(idList.get(position)-1).getWebsite());
        }

        startActivity(intent);
        finish();
    }

    public void back (View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void favorite() {
        favoriteList = new ArrayList<>();
        idList.clear();

        for (Sight item : mSightList) {
            if (item.getStar() == true) {
                favoriteList.add(item);
                idList.add(item.getId());
            }
        }

        mAdapter.filterList(favoriteList);
        isFiltred = true;
    }

    private void favoriteSearch(String text) {
        ArrayList<Sight> favoriteFiltredList = new ArrayList<>();
        idList.clear();

        for (Sight item : favoriteList) {
            if (item.getSightName().toLowerCase().contains(text.toLowerCase())) {
                favoriteFiltredList.add(item);
                idList.add(item.getId());
            }
        }

        mAdapter.filterList(favoriteFiltredList);
        isFiltred = true;
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
                favorite();
                editText.setText("");
                return true;
        }

        return false;
    }
}