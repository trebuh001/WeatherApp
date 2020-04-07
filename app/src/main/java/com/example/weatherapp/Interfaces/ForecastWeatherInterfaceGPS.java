package com.example.weatherapp.Interfaces;

import com.example.weatherapp.TreeForecastModels.RootObjectt;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastWeatherInterfaceGPS
{
    @GET("forecast")
    Observable<RootObjectt> getAll(@Query("lat") String lat, @Query("lon") String lon,@Query("appid") String appid,@Query("units") String unit);
}
