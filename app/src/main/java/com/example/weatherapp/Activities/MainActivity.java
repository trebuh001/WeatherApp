package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weatherapp.R;

public class MainActivity extends AppCompatActivity {
SharedPreferences shared;
Button btn_current_GPS,btn_forecast_GPS;
EditText et_city_name;
String str_city_name;
Toast toast=null;
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared=getSharedPreferences("A",Context.MODE_PRIVATE);
         btn_current_GPS=findViewById(R.id.btn_current_weather_by_gps);
         btn_forecast_GPS=findViewById(R.id.btn_forecast_weather_by_gps);
         et_city_name=findViewById(R.id.et_city_name);
         btn_current_GPS.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                    Intent intent=new Intent(getApplicationContext(), CurrentWeatherActivityGPS.class);

                    startActivity(intent);

             }
         });

        btn_forecast_GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   Intent intent=new Intent(getApplicationContext(), ForecastWeatherActivityGPS.class);
                    startActivity(intent);

            }
        });
    }

    public void Btn_Current_Weather(View v)
    {
        str_city_name=et_city_name.getText().toString();
         if(str_city_name.isEmpty())
         {
                 if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE))
                 {
                     toast = null;
                     toast = Toast.makeText(getApplicationContext(), R.string.alert_empty_field_text, Toast.LENGTH_SHORT);
                     toast.show();
                 }

         }
         else {
             SharedPreferences.Editor editor = shared.edit();
             editor.putString("city_name", str_city_name);
             editor.apply();
             Intent intent = new Intent(this, CurrentWeatherActivity.class);
             startActivity(intent);
         }
    }
    public void Btn_Forecast_Weather(View v)
    {
        str_city_name=et_city_name.getText().toString();
        if(str_city_name.isEmpty())
        {
            if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE))
            {
                toast = null;
                toast = Toast.makeText(getApplicationContext(), R.string.alert_empty_field_text, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("city_name", str_city_name);
            editor.apply();
            Intent intent = new Intent(this, ForecastWeatherActivity.class);
            startActivity(intent);
        }
    }
    public void Btn_favorites_places(View v)
    {
        Intent intent = new Intent(this, FavoritesPlacesActivity.class);
        startActivity(intent);
    }
    public void Btn_settings_application(View v)
    {
        Intent intent = new Intent(this, SettingsAppActivity.class);
        startActivity(intent);
    }

}
