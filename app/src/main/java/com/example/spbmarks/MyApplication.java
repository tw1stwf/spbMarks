package com.example.spbmarks;

import android.app.Application;

public class MyApplication extends Application {

    private int id;
    private int type;

    public int getUserId() {
        return id;
    }

    public void setUserId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
