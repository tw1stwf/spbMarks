package com.example.spbmarks;

public class Sight {
    private int mId;
    private int mImageResource;
    private String mText1;
    private String mText2;
    private boolean mStar;

    public Sight(int id, int imageResource, String text1, String text2, boolean star) {
        mId = id;
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mStar = star;
    }

    public int getId() { return mId; }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() { return mText2; }

    public boolean getStar() { return mStar; }
}