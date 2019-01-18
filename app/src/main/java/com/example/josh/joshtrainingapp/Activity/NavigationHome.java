package com.example.josh.joshtrainingapp.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josh.joshtrainingapp.Fragment.NotesFragment;
import com.example.josh.joshtrainingapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class NavigationHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Bundle bundle = new Bundle();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("NotePref", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("Style", "").equals("Dark")) {
            setTheme(R.style.DarkTheme);
            bundle.putString("Style", "Dark");
        }
        else {
            setTheme(R.style.LightTheme);
            bundle.putString("Style", "Light");
        }

        setContentView(R.layout.activity_navigation_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        changeFragment(new NotesFragment());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 1);
        } else if (id == R.id.action_backup) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};

                int permsRequestCode = 200;
                requestPermissions(perms, permsRequestCode);
            }
            try {

                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if(sd.canWrite()) {

                    String currentDBPath = "//data//com.example.josh.joshtrainingapp//databases//DIARY.DB";
                    String backupDBPath = "BACKUPDIARY.DB";
                    File src = new File(data, currentDBPath);
                    File dst = new File(sd, backupDBPath);

                    InputStream in = new FileInputStream(src);
                    try {

                        OutputStream out = new FileOutputStream(dst);
                        try {

                            // Transfer bytes from in to out
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                            Toast.makeText(this, "Backup Successful!", Toast.LENGTH_SHORT).show();
                        } finally {
                            out.close();
                        }
                    } finally {
                        in.close();
                    }
                } else {

                    Toast.makeText(this, "Write access not granted!", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.action_restore) {

            AlertDialog.Builder builder;
            if(sharedPreferences.getString("Style", "").equals("Light")) {

                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
            } else {

                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            }
            builder.setTitle("Restore backup")
                    .setMessage("Are you sure you want to restore? It will revert all changes made since previous backup.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            try {

                                File sd = Environment.getExternalStorageDirectory();
                                File data = Environment.getDataDirectory();


                                String currentDBPath = "//data//com.example.josh.joshtrainingapp//databases//DIARY.DB";
                                String backupDBPath = "BACKUPDIARY.DB";
                                File dst = new File(data, currentDBPath);
                                File src = new File(sd, backupDBPath);

                                InputStream in = new FileInputStream(src);
                                try {
                                    OutputStream out = new FileOutputStream(dst);
                                    try {
                                        // Transfer bytes from in to out
                                        byte[] buf = new byte[1024];
                                        int len;
                                        while ((len = in.read(buf)) > 0) {
                                            out.write(buf, 0, len);
                                            }
                                            Toast.makeText(NavigationHome.this, "Restored Successfully!", Toast.LENGTH_SHORT).show();

                                        recreate();

                                    } finally {
                                        out.close();
                                    }
                                } finally {
                                    in.close();
                                }
                            } catch (Exception e) {
                                    e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layoutContainer, fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.setArguments(bundle);
//        if(fragmentManager.getBackStackEntryCount() > 1)
//        {
//            fragmentTransaction.addToBackStack(null);
//        }
        fragmentManager.popBackStack();
        fragmentTransaction.commitAllowingStateLoss();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Log.i("LOGGG", "onActivityResult: ");
                Log.i("LOGGG", "onActivityResult: ");

                SharedPreferences sharedPreferences = getSharedPreferences("NotePref", Context.MODE_PRIVATE);
                if(sharedPreferences.getString("Style", "").equals("Dark")) {
                    setTheme(R.style.DarkTheme);
                    bundle.putString("Style", "Dark");
                }
                else {

                    setTheme(R.style.LightTheme);
                    bundle.putString("Style", "Light");
                }
                setContentView(R.layout.activity_navigation_home);
                recreate();

        }
    }

}