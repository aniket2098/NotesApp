package com.example.josh.joshtrainingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.josh.joshtrainingapp.Fragment.NotesFragment;
import com.example.josh.joshtrainingapp.R;

public class NavigationHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Bundle bundle = new Bundle();

    @Override
    protected void onStart() {
        super.onStart();
//        SharedPreferences sharedPreferences = getSharedPreferences("NotePref", Context.MODE_PRIVATE);
//        if(sharedPreferences.getString("Style", "").equals("Dark")) {
//            setTheme(R.style.DarkTheme);
//            bundle.putString("Style", "Dark");
//        }
//        else {
//            setTheme(R.style.LightTheme);
//            bundle.putString("Style", "Light");
//        }
//        setContentView(R.layout.activity_navigation_home);
//        recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivityForResult(intent, 1);
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
        if(fragmentManager.getBackStackEntryCount() > 1)
        {         fragmentTransaction.addToBackStack(null);

        }
        fragmentTransaction.commit();

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

