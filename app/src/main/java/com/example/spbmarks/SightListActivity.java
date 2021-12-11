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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sightlist);

        bottomNav = (BottomNavigationView) findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(this);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS favorites (id INTEGER, isFav BOOLEAN, UNIQUE(id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS reviews (sightId INTEGER, name STRING, comment STRING, rating INTEGER)");

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
        int listSize = 11;
        boolean stared[] = new boolean[listSize];

        for(int i = 0; i < listSize; i++) {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            Cursor query = db.rawQuery("SELECT * FROM favorites WHERE id = " + i + " ;", null);

            while (query.moveToNext()) {
                boolean isFav = query.getInt(1) > 0;
                stared[i] = isFav;
            }
        }

        mSightList = new ArrayList<>();
        mSightList.add(new Sight(1, R.drawable.kazan, "Казанский собор", "Невский проспект.", "Адрес: Казанская пл., 2", stared[1], "1811 г.", R.string.kazanDisc, "Андрей Никифорович Воронихин", 59.934443, 30.324701));
        mSightList.add(new Sight(2, R.drawable.isac, "Исакиевский собор", "Адмиралтейская.", "Адрес: Исаакиевская пл., 4", stared[2], "1858 г.", R.string.isacDisc, "Огюст Монферран", 59.93409, 30.30614));
        mSightList.add(new Sight(3, R.drawable.hermit, "Эрмитаж", "Адмиралтейская. ", "Адрес: Дворцовая пл., 2", stared[3], "1764 г.", R.string.discHermit, "Бартоломео Растрелли", 59.93986, 30.3146));
        mSightList.add(new Sight(4, R.drawable.vsadnik, "Медный всадник", "Адмиралтейская." , "Адрес: Сенатская пл.", stared[4], "1782 г.", R.string.vsadnicDisc, "Этьен Морис Фальконе", 59.93639, 30.30218));
        mSightList.add(new Sight(5, R.drawable.spas, "Спас на крови", "Невский проспект.", "Адрес: наб. канала Грибоедова, 2Б", stared[5], "1907 г.", R.string.spasDisc,"Альфред Александрович Парланд", 59.94006, 30.32882));
        mSightList.add(new Sight(6, R.drawable.zamok, "Михайловский замок", "Гостинный двор." , "Адрес: Садовая ул., 2", stared[6], "1800 г.", R.string.zamokDisc, "Винченцо Бренна", 59.93999, 30.33801));
        mSightList.add(new Sight(7, R.drawable.rusmus, "Русский музей", "Невский проспект." , "Адрес: Инженерная ул., 4", stared[7], "1895 г.", R.string.rusDisc , "Карло Росси", 59.93869, 30.3323));
        mSightList.add(new Sight(8, R.drawable.petrop, "Петропавловская крепость", "Горьковская." , "Адрес: Заячий остров", stared[8], "1740 г.", R.string.petropDisc, "Доменико Трезини", 59.95018, 30.31647));
        mSightList.add(new Sight(9, R.drawable.kunts, "Кунсткамера", "Адмиралтейская." , "Адрес: Университетская наб., 3", stared[9], "1714 г.",R.string.kuntsDisc , "Георг Иоганн Маттарнови", 59.94154, 30.30454));
        mSightList.add(new Sight(10,R.drawable.marink , "Мариинский театр", "Садовая." , "Адрес: Театральная пл., 1", stared[10], "1783 г.", R.string.marinksDisc , "Альберт Катеринович Кавос", 59.92577, 30.29645 ));
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
            intent.putExtra("x", mSightList.get(position).getX());
            intent.putExtra("y", mSightList.get(position).getY());
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
            intent.putExtra("x", mSightList.get(idList.get(position)-1).getX());
            intent.putExtra("y", mSightList.get(idList.get(position)-1).getY());
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