package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements SightAdapter.OnSightListener {
    private ArrayList<Sight> mSightList;
    private ArrayList<Integer> idList = new ArrayList<Integer>();

    private RecyclerView mRecyclerView;
    private SightAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isFiltred = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS favorites (id INTEGER, age BOOLEAN, UNIQUE(id))");

        createExampleList();
        buildRecyclerView();

        EditText editText = findViewById(R.id.edittext);
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
                filter(s.toString());
                if(editText.length() == 0)
                {
                    isFiltred = false;
                }
            }
        });

    }

    private void filter(String text) {
        ArrayList<Sight> filteredList = new ArrayList<>();
        idList.clear();

        for (Sight item : mSightList) {
            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
                idList.add(item.getId());
            }
        }
        
        mAdapter.filterList(filteredList);
        isFiltred = true;

    }

    private void createExampleList() {

        int listSize = 7;
        boolean stared[] = new boolean[listSize];

        for(int i = 0; i < listSize; i++) {
            SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            Cursor query = db.rawQuery("SELECT * FROM favorites WHERE id = " + i + " ;", null);

            while (query.moveToNext()) {
                boolean age = query.getInt(1) > 0;
                stared[i] = age;
            }
        }

        mSightList = new ArrayList<>();
        mSightList.add(new Sight(1, R.drawable.kazan, "Казанский собор", "      Невский проспект. \nАдрес: Казанская пл., 2", stared[1], "1811 г.", R.string.kazanDisc, "Андрей Никифорович Воронихин"));
        mSightList.add(new Sight(2, R.drawable.isac, "Исакиевский собор", "      Адмиралтейская. \nАдрес: Исаакиевская пл., 4", stared[2], "1858 г.", R.string.isacDisc, "Огюст Монферран"));
        mSightList.add(new Sight(3, R.drawable.hermit, "Эрмитаж", "      Адмиралтейская. \nАдрес: Дворцовая пл., 2", stared[3], "1764 г.", R.string.discHermit, "Бартоломео Растрелли"));
        mSightList.add(new Sight(4, R.drawable.vsadnik, "Медный всадник", "      Адмиралтейская. \nАдрес: Сенатская пл.", stared[4], "1782 г.", R.string.vsadnicDisc, "Этьен Морис Фальконе"));
        mSightList.add(new Sight(5, R.drawable.spas, "Спас на крови", "      Невский проспект. \nАдрес: наб. канала Грибоедова, 2Б", stared[5], "1907 г.", R.string.spasDisc,"Альфред Александрович Парланд"));
        mSightList.add(new Sight(6, R.drawable.zamok, "Михайловский замок", "      Гостинный двор. \nАдрес: Садовая ул., 2", stared[6], "1800 г.", R.string.zamokDisc, "Винченцо Бренна"));
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
        Intent intent = new Intent(this, MainActivity4.class);

        if(isFiltred == false)
        {
            intent.putExtra("image", mSightList.get(position).getImageResource());
            intent.putExtra("position", position);
            intent.putExtra("name", mSightList.get(position).getText1());

            intent.putExtra("disc", mSightList.get(position).getDisc());
            intent.putExtra("arch", mSightList.get(position).getArchitect());
            intent.putExtra("year", mSightList.get(position).getDateOfBuild());
        }

        if(isFiltred == true)
        {
            intent.putExtra("image", mSightList.get(idList.get(position)-1).getImageResource());
            intent.putExtra("position", idList.get(position)-1);
            intent.putExtra("name", mSightList.get(idList.get(position)-1).getText1());

            intent.putExtra("disc", mSightList.get(idList.get(position)-1).getDisc());
            intent.putExtra("arch", mSightList.get(idList.get(position)-1).getArchitect());
            intent.putExtra("year", mSightList.get(idList.get(position)-1).getDateOfBuild());
        }

        startActivity(intent);
        finish();
    }
}