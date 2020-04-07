package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.weatherapp.Adapters.Adapter1;
import com.example.weatherapp.FavoritesLocations.FavoriteLocations;
import com.example.weatherapp.ForecastModel.ForecastModel;
import com.example.weatherapp.Interfaces.ForecastWeatherInterface;
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

public class ForecastWeatherActivity extends AppCompatActivity {
    Realm realm;
    Toast toast=null;
    ViewPager viewPager;
    List<ForecastModel> rootObjectts;
    public static final String TAG="RX_RETROFIT";
    private ForecastWeatherInterface weather;
    private String temp_sign;
    Button btn_save_to_realm;
    SharedPreferences shared;
    String unit="metric";
    private String city_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_weather);
        btn_save_to_realm=findViewById(R.id.btn_favorites_places_forecast);
        viewPager=findViewById(R.id.vp_forecast_city);
        rootObjectts =new ArrayList<>();
        shared=getSharedPreferences("A", Context.MODE_PRIVATE);
        city_name=shared.getString("city_name","");
        realm=Realm.getDefaultInstance();
        checkUnitTemp();
        initializeForecastWeatherActivity();
        getAll();
    }

    private void initializeForecastWeatherActivity()
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        weather= retrofit.create(ForecastWeatherInterface.class);
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

    private void getAll()
    {

        ProgressDialog ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.create();
        ringProgressDialog.setMessage(getString(R.string.Loading));


        weather.getAll(city_name, TopSecret.API_KEY,unit)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeOn(Schedulers.io())
                .subscribe(

                        val-> {
                            city_name=val.getCity().getName();
                            for(int i=0;i<val.getList().size();i++)
                            {
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
                            if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)) {
                                toast=Toast.makeText(getApplicationContext(), R.string.api_error, Toast.LENGTH_LONG);
                                toast.show();
                            }
                            this.finish();

                        },
                        () ->
                        {
                            Adapter1 adapter=new Adapter1(rootObjectts,this);
                            viewPager.setAdapter(adapter);
                            if (ringProgressDialog.isShowing()) {
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
