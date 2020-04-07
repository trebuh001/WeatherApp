package com.example.weatherapp.Realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration= new RealmConfiguration.Builder().name("realmdata2.realm").build();
        Realm.setDefaultConfiguration(configuration);
    }
}
