package com.example.weatherapp.Realm;

import com.example.weatherapp.FavoritesLocations.FavoriteLocations;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    Realm realm;
    RealmResults<FavoriteLocations> location;

    public RealmHelper(Realm realm)
    {
        this.realm = realm;
    }

    public void selectFromDB()
    {
        location=realm.where(FavoriteLocations.class).findAll();
    }
    public ArrayList<FavoriteLocations> justRefresh()
    {
        ArrayList<FavoriteLocations>listItem=new ArrayList<>();
        for(FavoriteLocations l: location)
        {
            listItem.add(l);
        }
        return listItem;
    }
}
