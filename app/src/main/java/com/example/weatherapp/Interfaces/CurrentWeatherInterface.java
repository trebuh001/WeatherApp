package com.example.weatherapp.Interfaces;

import com.example.weatherapp.TreeWeatherModels.RootObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrentWeatherInterface {

    @GET("weather")

    Observable<RootObject> getAll(@Query("q") String city_name, @Query("appid") String token,@Query("units") String unit);
}
