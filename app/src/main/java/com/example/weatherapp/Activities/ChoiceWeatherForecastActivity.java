package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.weatherapp.FavoritesLocations.FavoriteLocations;
import com.example.weatherapp.R;

import io.realm.Realm;

public class ChoiceWeatherForecastActivity extends AppCompatActivity {
    Realm realm;
    TextView txt_choice;
    SharedPreferences shared;
    FavoriteLocations location;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_weather_forecast);
        txt_choice=findViewById(R.id.txt_choice_location);

        shared=getSharedPreferences("A", Context.MODE_PRIVATE);
        position=shared.getInt("realm_id",0);
        realm=Realm.getDefaultInstance();
        txt_choice.setText(getString(R.string.location)+": "+shared.getString("city_name",""));
        location=realm.where(FavoriteLocations.class).equalTo("id",position).findFirst();
    }
    public void Btn_Current_Weather(View v)
    {
        Intent intent = new Intent(this, CurrentWeatherActivity.class);
        startActivity(intent);
    }
    public void Btn_Forecast_Weather(View v)
    {
        Intent intent = new Intent(this, ForecastWeatherActivity.class);
        startActivity(intent);
    }
    public void Btn_Favorite_Delete(View v)
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                location.deleteFromRealm();
                onBackPressed();
            }
        });
    }
}
