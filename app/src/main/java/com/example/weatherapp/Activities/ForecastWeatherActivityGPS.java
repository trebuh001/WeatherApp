package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.weatherapp.Adapters.Adapter1;
import com.example.weatherapp.FavoritesLocations.FavoriteLocations;
import com.example.weatherapp.ForecastModel.ForecastModel;
import com.example.weatherapp.GPSClass.GPStracker;
import com.example.weatherapp.Interfaces.ForecastWeatherInterfaceGPS;
import com.example.weatherapp.R;
import com.example.weatherapp.TopSecret.TopSecret;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastWeatherActivityGPS extends AppCompatActivity {
    Realm realm;
    ViewPager viewPager;
    Toast toast=null;
    List<ForecastModel> rootObjectts;
    public static final String TAG="RX_RETROFIT";
    private String city_name;
    private String lat,lon;
    private String temp_sign;
    private ForecastWeatherInterfaceGPS weather;
    SharedPreferences shared;
    String unit="metric";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_weather_g_p_s);
        viewPager=findViewById(R.id.vp_forecast_gps);
        rootObjectts =new ArrayList<>();
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
        initializeForecastWeatherActivity();
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
    private void initializeForecastWeatherActivity()
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        weather= retrofit.create(ForecastWeatherInterfaceGPS.class);
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
                            for(int i=0;i<val.getList().size();i++)
                            {
                                city_name=val.getCity().getName();
                                rootObjectts.add(new ForecastModel(val.getList().get(i).getWeather().get(0).getIcon(),val.getList().get(i).getDt_txt(),
                                        val.getCity().getName()+", "+val.getCity().getCountry(),val.getList().get(i).getWeather().get(0).getDescription(),
                                        String.valueOf(val.getList().get(i).getMain().getTemp())+temp_sign,String.valueOf(val.getList().get(i).getMain().getFeels_like())+temp_sign,
                                        String.valueOf(val.getList().get(i).getMain().getTemp_min())+temp_sign,String.valueOf(val.getList().get(i).getMain().getTemp_max())+temp_sign,
                                        val.getList().get(i).getMain().getPressure(),val.getList().get(i).getMain().getHumidity(),
                                        val.getList().get(i).getWind().getSpeed(),val.getList().get(i).getWind().getDeg()));
                            }
                            Log.d(TAG, val.toString());
                        },
                        error ->{
                            Log.e(TAG,error.getMessage());
                            if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE))
                            {
                                toast=Toast.makeText(getApplicationContext(), R.string.api_error2, Toast.LENGTH_LONG);
                                toast.show();
                            }
                            this.finish();

                        },
                        () ->
                        {
                            Adapter1 adapter=new Adapter1(rootObjectts,this);
                            viewPager.setAdapter(adapter);
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
