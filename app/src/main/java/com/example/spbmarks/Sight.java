package com.example.spbmarks;

import android.graphics.drawable.Drawable;

public class Sight {
    private int mId;
    private int mImageResource;
    private int mDisc;
    private boolean mStar;
    private double mLatitude;
    private double mLongitude;
    private String mSightName;
    private String mMetro;
    private String mLocation;
    private String mDateOfBuild;
    private String mArchitect;
    private String mWebsite;

    public Sight(int id, int imageResource, String sightName, String metro, String location , boolean star, String dateOfBuild, int disc, String architect, double latitude, double longitude, String website) {
        mId = id;
        mImageResource = imageResource;
        mSightName = sightName;
        mMetro = metro;
        mStar = star;
        mDateOfBuild = dateOfBuild;
        mDisc = disc;
        mArchitect = architect;
        mLocation = location;
        mLatitude = latitude;
        mLongitude = longitude;
        mWebsite = website;
    }

    public int getId() { return mId; }

    public int getImageResource() { return mImageResource; }

    public int getDisc() { return mDisc; }

    public boolean getStar() { return mStar; }

    public String getSightName() { return mSightName; }

    public String getMetro() { return mMetro; }

    public String getDateOfBuild() { return mDateOfBuild; }

    public String getArchitect() { return mArchitect; }

    public String getLocation() { return mLocation; }

    public String getWebsite() { return mWebsite; }

    public double getLatitude() { return mLatitude; }

    public double getLongitude() { return mLongitude; }


}