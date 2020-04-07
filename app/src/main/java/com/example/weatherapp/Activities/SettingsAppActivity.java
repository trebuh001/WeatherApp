package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.weatherapp.R;

public class SettingsAppActivity extends AppCompatActivity {
    Toast toast=null;
    SharedPreferences shared;
    RadioGroup radioGroup_temp;
    RadioButton rb_celsius,rb_kelvin,rb_fahrenheit;
    RadioButton rb_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_app);
        shared=getSharedPreferences("A", Context.MODE_PRIVATE);
        radioGroup_temp=findViewById(R.id.radio_group_temp_unit);
        rb_celsius=findViewById(R.id.radio_temp_celsius);
        rb_kelvin=findViewById(R.id.radio_temp_kelvin);
        rb_fahrenheit=findViewById(R.id.radio_temp_fahrenheit);

        if(!shared.contains("unit") || shared.getString("unit","").equals(getString(R.string.grad_of_celsius)))
        {
            rb_celsius.setChecked(true);

        }
        else if(shared.getString("unit","").equals(getString(R.string.grad_of_kelvin)))
        {
            rb_kelvin.setChecked(true);
        }
        else if(shared.getString("unit","").equals(getString(R.string.grad_of_fahrenheit)))
        {
            rb_fahrenheit.setChecked(true);
        }
    }
    public void Btn_set_unit(View v)
    {
        int selectedId = radioGroup_temp.getCheckedRadioButtonId();
        rb_selected=findViewById(selectedId);
        if((toast == null || toast.getView().getWindowVisibility() != View.VISIBLE)) {
            toast=Toast.makeText(getApplicationContext(),getString(R.string.temp_unit_set_to)+" "+String.valueOf(rb_selected.getText().toString()), Toast.LENGTH_LONG);
            toast.show();
        }
            SharedPreferences.Editor editor= shared.edit();
            editor.putString("unit",rb_selected.getText().toString());
            editor.commit();
    }
}
