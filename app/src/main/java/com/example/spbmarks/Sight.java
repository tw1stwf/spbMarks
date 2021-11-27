package com.example.spbmarks;

public class Sight {
    private int mId;
    private int mImageResource;
    private int mDisc;
    private boolean mStar;
    private double mX;
    private double mY;
    private String mSightName;
    private String mMetro;
    private String mLocation;
    private String mDateOfBuild;
    private String mArchitect;

    public Sight(int id, int imageResource, String sightName, String metro, String location ,boolean star, String dateOfBuild, int disc, String architect, double x, double y) {
        mId = id;
        mImageResource = imageResource;
        mSightName = sightName;
        mMetro = metro;
        mStar = star;
        mDateOfBuild = dateOfBuild;
        mDisc = disc;
        mArchitect = architect;
        mLocation = location;
        mX = x;
        mY = y;
    }

    public int getId() { return mId; }

    public int getImageResource() {
        return mImageResource;
    }

    public String getSightName() { return mSightName; }

    public String getMetro() { return mMetro; }

    public boolean getStar() { return mStar; }

    public String getDateOfBuild() { return mDateOfBuild; }

    public int getDisc() { return mDisc; }

    public String getArchitect() { return mArchitect; }

    public String getLocation() { return mLocation; }

    public double getX() { return mX; }

    public double getY() { return mY; }
}