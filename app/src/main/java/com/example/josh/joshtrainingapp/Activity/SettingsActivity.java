package com.example.josh.joshtrainingapp.Activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.josh.joshtrainingapp.Services.JobBackup;
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
        Boolean checkBackup = sharedPreferences.getBoolean("autoBackup", false);
        if(style.equals("Dark")) {
            setTheme(R.style.DarkTheme);
         }
        else {
            setTheme(R.style.LightTheme);
         }
        final Intent intent = new Intent();
        setContentView(R.layout.activity_settings);
        rg = findViewById(R.id.rg1);
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

        CheckBox checkBox = findViewById(R.id.checkBoxBackup);

        if(checkBackup) {
            checkBox.setChecked(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                JobScheduler jobScheduler = (JobScheduler)getApplicationContext()
                        .getSystemService(JOB_SCHEDULER_SERVICE);
                if(isChecked) {

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
        });
    }
}