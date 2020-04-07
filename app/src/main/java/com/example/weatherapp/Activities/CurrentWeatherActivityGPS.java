package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.FavoritesLocations.FavoriteLocations;
import com.example.weatherapp.GPSClass.GPStracker;
import com.example.weatherapp.Interfaces.CurrentWeatherInterfaceGPS;
import com.example.weatherapp.R;
import com.example.weatherapp.TopSecret.TopSecret;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentWeatherActivityGPS extends AppCompatActivity {
    Realm realm;
    Toast toast=null;
    public static final String TAG="RX_RETROFIT";
    TextView txt_setTemperature,txt_setPressure,txt_setHumidity,txt_setDescription,txt_setPlace;
    TextView txt_setTempFeelsLike,txt_setTempMin,txt_setTempMax,txt_setWindSpeed,txt_setWindDegree,txt_setVisibility;
    ImageView img_setIcon;
    SharedPreferences shared;
    private ProgressDialog dialog;
    private String icon_string;
    private String city_name,country_name,lat,lon;
    private String unit="metric";
    private String temp_sign;
    private String description;
    private double temp,temp_feels_like,min_temp,max_temp,wind_speed;
    private int pressure,visibility,wind_degree;
    private double humidity;
    private CurrentWeatherInterfaceGPS weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather_g_p_s);
        txt_setTemperature=findViewById(R.id.txt_setTemperature);
        txt_setPressure=findViewById(R.id.txt_setPressure);
        txt_setHumidity=findViewById(R.id.txt_setHumidity);
        txt_setDescription=findViewById(R.id.txt_setDescription);
        txt_setTempFeelsLike=findViewById(R.id.txt_setTempFeelsLike);
        txt_setTempMin=findViewById(R.id.txt_setTempMin);
        txt_setTempMax=findViewById(R.id.txt_setTempMax);
        txt_setWindSpeed=findViewById(R.id.txt_setWindSpeed);
        txt_setWindDegree=findViewById(R.id.txt_setWindDegree);
        txt_setVisibility=findViewById(R.id.txt_setVisibility);
        txt_setPlace=findViewById(R.id.txt_setPlace);
        img_setIcon=findViewById(R.id.img_current_search);
        dialog = new ProgressDialog(CurrentWeatherActivityGPS.this);
        shared=getSharedPreferences("A", Context.MODE_PRIVATE);
        realm=Realm.getDefaultInstance();

        GPStracker g=new GPStracker(getApplicationContext());
        Location l= g.getLocation();

        if(l!=null) {
            lat = String.valueOf(l.getLatitude());
            lon = String.valueOf(l.getLongitude());
            SharedPreferences.Editor editor=shared.edit();
            editor.putString("lat",lat);
            editor.putString("lon",lon);
            editor.apply();
        }
        else
        {
            if(shared.contains("lat")&& shared.contains("lon"))
            {
                lat = shared.getString("lat", "");
                lon = shared.getString("lon", "");
            }
        }

        checkUnitTemp();
        initializeCurrentWeatherActivity();
        getAll();
    }
    private void checkUnitTemp()
    {
        if(!shared.contains("unit") || shared.getString("unit","").equals(getString(R.string.grad_of_celsius)))
        {
            unit="metric";
            temp_sign="\u2103";

        }
        else if(shared.getString("unit","").equals(getString(R.string.grad_of_kelvin)))
        {
            unit="";
            temp_sign="\u212A";

        }
        else if(shared.getString("unit","").equals(getString(R.string.grad_of_fahrenheit)))
        {
            unit="imperial";
            temp_sign="\u2109";
        }
    }
    private void initializeCurrentWeatherActivity()
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        weather= retrofit.create(CurrentWeatherInterfaceGPS.class);
    }

    private void getAll()
    {
        ProgressDialog ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.create();
        ringProgressDialog.setMessage(getString(R.string.Loading));
        weather.getAll(lat,lon, TopSecret.API_KEY,unit)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeOn(Schedulers.io())
                .subscribe(
                        val-> {

                            country_name=val.getSys().getCountry();
                            city_name=val.getName();
                            description = val.getWeather().get(0).getDescription();
                            temp= val.getMain().getTemp();
                            temp_feels_like=val.getMain().getFeels_like();
                            min_temp=val.getMain().getTemp_min();
                            max_temp=val.getMain().getTemp_max();
                            pressure=val.getMain().getPressure();
                            humidity=val.getMain().getHumidity();
                            visibility=val.getVisibility();
                            wind_speed=val.getWind().getSpeed();
                            wind_degree=val.getWind().getDeg();
                            icon_string=val.getWeather().get(0).getIcon();


                            Log.d(TAG, val.toString());
                        },
                        error ->{
                            Log.e(TAG,error.getMessage());
                            if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)) {
                                toast=Toast.makeText(getApplicationContext(), R.string.api_error2, Toast.LENGTH_LONG);
                                toast.show();
                            }
                            this.finish();
                        },
                        () ->
                        {
                            txt_setPlace.setText(city_name+", "+country_name);
                            txt_setDescription.setText(description);
                            txt_setTemperature.setText(String.valueOf(temp)+temp_sign);
                            txt_setTempFeelsLike.setText(String.valueOf(temp_feels_like)+temp_sign);
                            txt_setTempMin.setText(String.valueOf(min_temp)+temp_sign);
                            txt_setTempMax.setText(String.valueOf(max_temp)+temp_sign);
                            txt_setPressure.setText(String.valueOf(pressure)+"hPa");
                            txt_setHumidity.setText(String.valueOf(humidity)+"%");
                            txt_setVisibility.setText(String.valueOf(visibility)+"ft");
                            txt_setWindSpeed.setText(String.valueOf(wind_speed)+"m/s");
                            txt_setWindDegree.setText(String.valueOf(wind_degree)+"\u00B0");
                            Picasso.get().load("http://openweathermap.org/img/wn/"+icon_string+"@2x.png").into(img_setIcon);
                            Log.d(TAG,"finish getAll");
                            if (ringProgressDialog.isShowing())
                            {
                                ringProgressDialog.dismiss();
                            }
                        },
                        d -> {
                            Log.d(TAG,"subscribe getAll");
                            ringProgressDialog.show();
                        }
                );
    }

    public void Btn_save_location_to_realm(View v)
    {
        FavoriteLocations loc=realm.where(FavoriteLocations.class).equalTo("name",city_name).findFirst();
        if(loc!=null)
        {
            Toast.makeText(getApplicationContext(), R.string.location_exist, Toast.LENGTH_SHORT).show();
        }
        else {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    Number max_id = bgRealm.where(FavoriteLocations.class).max("id");
                    int new_id;
                    if (max_id == null) {
                        new_id = 1;
                    } else {
                        new_id = max_id.intValue() + 1;
                    }

                    FavoriteLocations location = bgRealm.createObject(FavoriteLocations.class, new_id);
                    location.setName(city_name);


                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), R.string.toast_location_saved, Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(getApplicationContext(), R.string.api_error2, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
