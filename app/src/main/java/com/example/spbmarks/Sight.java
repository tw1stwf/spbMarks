package com.example.spbmarks;

import android.graphics.drawable.Drawable;

public class Sight {
    private int mId;
    private int mImageResource;
    private double mLatitude;
    private double mLongitude;
    private boolean mStar;
    private String mDisc;
    private String mSightName;
    private String mMetro;
    private String mLocation;
    private String mDateOfBuild;
    private String mArchitect;
    private String mWebsite;
    private String mType;

    private String mPriceKids;
    private String mPrice;
    private String mOpenTime;
    private String mCloseTime;


    public Sight(int id, int imageResource, String sightName, String metro, String location , boolean star, String dateOfBuild, String disc, String architect, double latitude, double longitude, String website, String type, String openTime, String closeTime, String price, String priceKids) {
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
        mType = type;
        mPriceKids = priceKids;
        mPrice = price;
        mCloseTime = closeTime;
        mOpenTime = openTime;
    }

    public int getId() { return mId; }

    public int getImageResource() { return mImageResource; }

    public double getLatitude() { return mLatitude; }

    public double getLongitude() { return mLongitude; }

    public boolean getStar() { return mStar; }

    public String getDisc() { return mDisc; }

    public String getSightName() { return mSightName; }

    public String getMetro() { return mMetro; }

    public String getDateOfBuild() { return mDateOfBuild; }

    public String getArchitect() { return mArchitect; }

    public String getLocation() { return mLocation; }

    public String getWebsite() { return mWebsite; }

    public String getType() { return mType; }

    public String getPriceKids() { return mPriceKids; }

    public String getPrice() { return mPrice; }

    public String getOpenTime() { return mOpenTime; }

    public String getCloseTime() { return mCloseTime; }
}