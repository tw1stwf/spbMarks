package com.example.spbmarks;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {
    public List<Weather> getWeatherList() { return weatherList; }

    public void setWeatherList(java.util.List<Weather> weatherList) { this.weatherList = weatherList; }

    @SerializedName("weather")
    private List<Weather> weatherList;
    @SerializedName("main")
    public Main main;
    @SerializedName("dt")
    public float dt;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public float cod;
}

class Weather {
    @SerializedName("id")
    public int id;
    @SerializedName("main")
    public String main;
    @SerializedName("description")
    public String description;
    @SerializedName("icon")
    public String icon;
}

class Main {
    @SerializedName("temp")
    public float temp;
    @SerializedName("feels_like")
    public float feels_like;
    @SerializedName("humidity")
    public float humidity;
    @SerializedName("pressure")
    public float pressure;
    @SerializedName("temp_min")
    public float temp_min;
    @SerializedName("temp_max")
    public float temp_max;
}

