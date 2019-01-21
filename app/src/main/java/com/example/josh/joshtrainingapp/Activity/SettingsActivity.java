package com.example.josh.joshtrainingapp.Activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.josh.joshtrainingapp.R;
import com.example.josh.joshtrainingapp.Services.JobBackup;

public class SettingsActivity extends AppCompatActivity {

    CheckBox checkBox;
    RadioGroup radioGroupThemeSelection;
    RadioGroup radioGroupViewSelection;
    SharedPreferences sharedPreferences;
    String style;
    String view;
    Boolean checkBackup;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setSettings();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("LOGGG", "onCreate: ");
        sharedPreferences = getSharedPreferences("NotePref", Context.MODE_PRIVATE);
        style = sharedPreferences.getString("Style", "");
        view = sharedPreferences.getString("View", "");
        checkBackup = sharedPreferences.getBoolean("autoBackup", false);

        if(style.equals("Dark")) {
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.LightTheme);
        }

        final Intent intent = new Intent();
        setContentView(R.layout.activity_settings);
        checkBox = findViewById(R.id.checkBoxBackup);
        radioGroupViewSelection = findViewById(R.id.rg2);
        radioGroupThemeSelection = findViewById(R.id.rg1);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        System.out.println("INIT");
        final SharedPreferences.Editor editor = getSharedPreferences("NotePref", Context.MODE_PRIVATE).edit();

        radioGroupThemeSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (findViewById(checkedId).isPressed()) {
                    checkBox.setOnCheckedChangeListener(null);
                    radioGroupThemeSelection.setOnCheckedChangeListener(null);
                    Log.i("LOGGG", "onCheckedChanged: ");
                    String value = ((RadioButton) findViewById(checkedId)).getText().toString();
                    if (value.equals("Dark")) {
                        editor.putString("Style", "Dark");


                    } else {
                        editor.putString("Style", "Light");

                    }
                    editor.apply();
                    setResult(1);

                    setContentView(R.layout.activity_settings);
                    recreate();
                }
            }
        });



        radioGroupViewSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
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



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                JobScheduler jobScheduler = (JobScheduler) getApplicationContext()
                        .getSystemService(JOB_SCHEDULER_SERVICE);
                if (checkBox.isPressed()) {
                    if (isChecked) {

                        editor.putBoolean("autoBackup", true);
                        editor.apply();
                        long scheduleMillis = 24 * 60 * 60 * 1000;
                        Toast.makeText(SettingsActivity.this, "Backup will only take place if access to external storage has been granted", Toast.LENGTH_LONG).show();

                        ComponentName componentName = new ComponentName(SettingsActivity.this, JobBackup.class);
                        JobInfo jobInfo = new JobInfo.Builder(12, componentName)
                                .setPeriodic(scheduleMillis)
                                .build();


                        int resultCode = jobScheduler.schedule(jobInfo);
                        if (resultCode == JobScheduler.RESULT_SUCCESS) {
                            Log.d("LOGGG", "Job scheduled!");
                        } else {
                            Log.d("LOGGG", "Job not scheduled");
                        }
                    } else {
                        jobScheduler.cancel(12);
                        editor.putBoolean("autoBackup", false);
                        editor.apply();
                    }
                }
            }
        });

    }

    public void setSettings() {

        System.out.println("INSET");
        if(checkBackup) {
            checkBox.setChecked(true);
        }
        if(style.equals("Dark")) {
            System.out.println("Dark");
            radioGroupThemeSelection.check(R.id.radioButtonDark);
        }
        else {
            System.out.println("Light");
            radioGroupThemeSelection.check(R.id.radioButtonLight);
        }
        if(view.equals("List")) {

            radioGroupViewSelection.check(R.id.radioButtonList);
        }
        else {
            radioGroupViewSelection.check(R.id.radioButtonGrid);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}