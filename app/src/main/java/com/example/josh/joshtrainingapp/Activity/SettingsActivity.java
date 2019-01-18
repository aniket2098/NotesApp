package com.example.josh.joshtrainingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.josh.joshtrainingapp.R;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RadioGroup rg;
        Log.i("LOGGG", "onCreate: ");
        SharedPreferences sharedPreferences = getSharedPreferences("NotePref", Context.MODE_PRIVATE);
        String style = sharedPreferences.getString("Style", "");
        String view = sharedPreferences.getString("View", "");
        if(style.equals("Dark")) {
            setTheme(R.style.DarkTheme);
         }
        else {
            setTheme(R.style.LightTheme);
         }
        final Intent intent = new Intent();
        setContentView(R.layout.activity_settings);
        rg = findViewById(R.id.rg1);
//        rg.setOnCheckedChangeListener(null);
//        rg.clearCheck();
//        if(style.equals("Dark")) {
//
//            rg.check(R.id.radioButtonDark);
//        }
//        else {
//
//            rg.check(R.id.radioButtonLight);
//        }
        final SharedPreferences.Editor editor = getSharedPreferences("NotePref", Context.MODE_PRIVATE).edit();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("LOGGG", "onCheckedChanged: ");
                String value = ((RadioButton)findViewById(checkedId)).getText().toString();
                if(value.equals("Dark")) {
                    editor.putString("Style", "Dark");
                    setTheme(R.style.DarkTheme);

                } else {
                    editor.putString("Style", "Light");
                    setTheme(R.style.LightTheme);
                }
                editor.apply();
                setResult(1);
                setContentView(R.layout.activity_settings);
                recreate();
            }
        });

        rg = findViewById(R.id.rg2);

        if(view.equals("List")) {

            rg.check(R.id.radioButtonList);
        }
        else {
            rg.check(R.id.radioButtonGrid);
        }

        rg = findViewById(R.id.rg2);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String value = ((RadioButton)findViewById(checkedId)).getText().toString();
                if(value.equals("List")) {
                    editor.putString("View", "List");
                }
                else {
                    editor.putString("View", "Grid");
                }
                editor.apply();
            }
        });
    }
}