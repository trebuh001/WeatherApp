package com.example.weatherapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.weatherapp.ForecastModel.ForecastModel;
import com.example.weatherapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter1 extends PagerAdapter {
    private List<ForecastModel> rootObjectts;
    private LayoutInflater layoutinflater;
    private Context context;
    private SharedPreferences shared;

    public Adapter1(List<ForecastModel> rootObjectts, Context context) {
        this.rootObjectts = rootObjectts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return rootObjectts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutinflater =LayoutInflater.from(context);
        View view= layoutinflater.inflate(R.layout.item,container,false);

        ImageView img_setIcon;
        TextView txt_setDatetime,txt_setTemperature,txt_setPressure,txt_setHumidity,txt_setDescription,txt_setPlace;
        TextView txt_setTempFeelsLike,txt_setTempMin,txt_setTempMax,txt_setWindSpeed,txt_setWindDegree;

        img_setIcon=view.findViewById(R.id.img_forecast_search);
        txt_setDatetime=view.findViewById(R.id.txt_set_forecast_Datetime);
        txt_setTemperature=view.findViewById(R.id.txt_set_forecast_Temperature);
        txt_setPressure=view.findViewById(R.id.txt_set_forecast_Pressure);
        txt_setHumidity=view.findViewById(R.id.txt_set_forecast_Humidity);
        txt_setDescription=view.findViewById(R.id.txt_set_forecast_Description);
        txt_setPlace=view.findViewById(R.id.txt_set_forecast_Place);
        txt_setTempFeelsLike=view.findViewById(R.id.txt_set_forecast_TempFeelsLike);
        txt_setTempMin=view.findViewById(R.id.txt_set_forecast_TempMin);
        txt_setTempMax=view.findViewById(R.id.txt_set_forecast_TempMax);
        txt_setWindSpeed=view.findViewById(R.id.txt_set_forecast_WindSpeed);
        txt_setWindDegree=view.findViewById(R.id.txt_set_forecast_WindDegree);

        txt_setDatetime.setText(String.valueOf(rootObjectts.get(position).getDate_time()));
        txt_setTemperature.setText(String.valueOf(rootObjectts.get(position).getTemp()));
        txt_setPressure.setText(String.valueOf(rootObjectts.get(position).getPressure())+"hPa");
        txt_setHumidity.setText(String.valueOf(rootObjectts.get(position).getHumidity())+"%");
        txt_setDescription.setText(String.valueOf(rootObjectts.get(position).getDescription()));
        txt_setPlace.setText(String.valueOf(rootObjectts.get(position).getPlace()));
        txt_setTempFeelsLike.setText(String.valueOf(rootObjectts.get(position).getTemp_feels_like()));
        txt_setTempMin.setText(String.valueOf(rootObjectts.get(position).getMin_temp()));
        txt_setTempMax.setText(String.valueOf(rootObjectts.get(position).getMax_temp()));
        txt_setWindSpeed.setText(String.valueOf(rootObjectts.get(position).getWind_speed())+"m/s");
        txt_setWindDegree.setText(String.valueOf(rootObjectts.get(position).getWind_degree())+"\u00B0");


        Picasso.get().load("http://openweathermap.org/img/wn/"+rootObjectts.get(position).getIcon()+"@2x.png").into(img_setIcon);

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View)object);
    }
}
