package com.example.weatherapp.TreeForecastModels;

import java.util.List;

public class RootObjectt {
    private String cod;
    private int message;
    private int cnt;
    private List<Listt> list;
    private City city;

    public RootObjectt() {
    }





    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }



    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Listt> getList() {
        return list;
    }

    public void setList(List<Listt> list) {
        this.list = list;
    }
}
