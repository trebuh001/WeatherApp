package com.example.weatherapp.GPSClass;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.weatherapp.R;

public class GPStracker implements LocationListener {
    Context context;
    public GPStracker(Context c)
    {
        context=c;
    }

    public Location getLocation()
    {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {

            if (android.os.Build.VERSION.SDK_INT < 28)
            {
                this.context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.getPackageName(), null)));
                Toast.makeText(context, R.string.permission_not_granted,Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context, R.string.permission_not_granted,Toast.LENGTH_LONG).show();

            }


            return null;
        }
        LocationManager lm= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled)
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,10,this);
            Location l=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        }
        else
        {
            Toast.makeText(context,R.string.please_on_gps,Toast.LENGTH_SHORT).show();
            this.context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        return null;
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
