package com.example.weatherapp.Interfaces;


import com.example.weatherapp.TreeForecastModels.RootObjectt;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastWeatherInterface
{

    @GET("forecast")
    Observable<RootObjectt> getAll(@Query("q") String city_name, @Query("appid") String token, @Query("units") String unit);
}
