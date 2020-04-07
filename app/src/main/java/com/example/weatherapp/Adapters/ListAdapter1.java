package com.example.weatherapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weatherapp.FavoritesLocations.FavoriteLocations;
import com.example.weatherapp.R;

import java.util.ArrayList;

public class ListAdapter1 extends BaseAdapter
{
    Context context;
    ArrayList<FavoriteLocations> location;

    public ListAdapter1(Context context, ArrayList<FavoriteLocations> location) {
        this.context = context;
        this.location = location;
    }

    @Override
    public int getCount() {
        return location.size();
    }

    @Override
    public Object getItem(int position) {
        return location.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.list_item,parent,false);
        TextView txtName;
        txtName=view.findViewById(R.id.list_item_name);

        FavoriteLocations l=(FavoriteLocations)this.getItem(position);
        txtName.setText(l.getName());


        return view;

    }
}
