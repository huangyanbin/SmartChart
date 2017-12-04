package com.bin.david.smartchart.weather;

/**
 * Created by huang on 2017/11/30.
 */

public class WeatherBean {

    private String date;
    private String week;
    private String fristWeather;
    private String lastWeather;
    private String wind;
    private String hightTemp;
    private String lowTemp;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getFristWeather() {
        return fristWeather;
    }

    public void setFristWeather(String fristWeather) {
        this.fristWeather = fristWeather;
    }

    public String getLastWeather() {
        return lastWeather;
    }

    public void setLastWeather(String lastWeather) {
        this.lastWeather = lastWeather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHightTemp() {
        return hightTemp;
    }

    public void setHightTemp(String hightTemp) {
        this.hightTemp = hightTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }
}
