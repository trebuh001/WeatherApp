package com.example.weatherapp.Interfaces;

import com.example.weatherapp.TreeWeatherModels.RootObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrentWeatherInterfaceGPS
{

    @GET("weather")

    Observable<RootObject> getAll(@Query("lat") String lat, @Query("lon") String lon,@Query("appid") String appid,@Query("units") String unit);
}
