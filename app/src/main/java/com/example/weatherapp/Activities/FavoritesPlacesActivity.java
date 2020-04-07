package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.weatherapp.Adapters.ListAdapter1;
import com.example.weatherapp.FavoritesLocations.FavoriteLocations;
import com.example.weatherapp.R;
import com.example.weatherapp.Realm.RealmHelper;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class FavoritesPlacesActivity extends AppCompatActivity {
Realm realm;
ListView listView;
RealmHelper helper;
RealmChangeListener realmChangeListener;
SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_places);
        listView=findViewById(R.id.lv_list);
        shared=getSharedPreferences("A", Context.MODE_PRIVATE);
        realm=Realm.getDefaultInstance();
        helper=new RealmHelper(realm);
        helper.selectFromDB();
        ListAdapter1 adapter=new ListAdapter1(this,helper.justRefresh());
        listView.setAdapter(adapter);
        Refresh();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FavoriteLocations l = (FavoriteLocations) parent.getItemAtPosition(position);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("city_name", l.getName());
                editor.putInt("realm_id",l.getId());
                editor.apply();
                Intent intent= new Intent (getApplicationContext(),ChoiceWeatherForecastActivity.class);
                startActivity(intent);
            }

        });

    }

    private void Refresh()
    {
        realmChangeListener=new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                ListAdapter1 adapter=new ListAdapter1(getApplicationContext(),helper.justRefresh());
                listView.setAdapter(adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }
}
