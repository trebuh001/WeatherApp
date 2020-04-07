package com.example.weatherapp.ForecastModel;

public class ForecastModel
{
    private String icon;
    private String date_time;
    private String place;
    private String description;
    private String temp;
    private String temp_feels_like;
    private String min_temp;
    private String max_temp;
    private int pressure;
    private int humidity;
    private double wind_speed;
    private int wind_degree;

    public ForecastModel(String icon, String date_time, String place, String description, String temp,
                         String temp_feels_like, String min_temp, String max_temp, int pressure, int humidity, double wind_speed, int wind_degree)
    {
        this.icon = icon;
        this.date_time = date_time;
        this.place = place;
        this.description = description;
        this.temp = temp;
        this.temp_feels_like = temp_feels_like;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.wind_degree = wind_degree;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp_feels_like() {
        return temp_feels_like;
    }

    public void setTemp_feels_like(String temp_feels_like) {
        this.temp_feels_like = temp_feels_like;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWind_degree() {
        return wind_degree;
    }

    public void setWind_degree(int wind_degree) {
        this.wind_degree = wind_degree;
    }
}

