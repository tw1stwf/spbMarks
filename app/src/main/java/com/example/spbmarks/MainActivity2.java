package com.example.spbmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements SightAdapter.OnSightListener {
    private ArrayList<Sight> mExampleList;

    private RecyclerView mRecyclerView;
    private ImageButton imageButton;
    private SightAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private boolean isStared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }
    //

    private void filter(String text) {
        ArrayList<Sight> filteredList = new ArrayList<>();

        for (Sight item : mExampleList) {
            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void createExampleList() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new Sight(R.drawable.kazan, "Казанский собор", "      Невский проспект. \nАдрес: Казанская пл., 2"));
        mExampleList.add(new Sight(R.drawable.isac, "Исакиевский собор", "      Адмиралтейская. \nАдрес: Исаакиевская пл., 4"));
        mExampleList.add(new Sight(R.drawable.hermit, "Эрмитаж", "      Адмиралтейская. \nАдрес: Дворцовая пл., 2"));
        mExampleList.add(new Sight(R.drawable.vsadnik, "Медный всадник", "      Адмиралтейская. \nАдрес: Сенатская пл"));
        mExampleList.add(new Sight(R.drawable.spas, "Спас на крови", "      Невский проспект. \nАдрес: наб. канала Грибоедова, 2Б"));
        mExampleList.add(new Sight(R.drawable.zamok, "Михайловский замок", "      Гостинный двор. \nАдрес: Садовая ул., 2"));
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SightAdapter(mExampleList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSightClick(int position) {
        Intent intent = new Intent(this, MainActivity4.class);
        intent.putExtra("image", mExampleList.get(position).getImageResource());
        startActivity(intent);
    }

    public void isStared (View view)
    {
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);

        if(isStared == false)
        {
            imageButton.setColorFilter(Color.argb(255, 205, 201, 112));
            isStared = true;
        }

        else
        {
            imageButton.setColorFilter(Color.argb(255, 151, 151, 151));
            isStared = false;
        }
    }
}