package com.example.alarm;

import static java.lang.Math.abs;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Toolbar toolBar;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.alarm);
        fragmentloading(new AlarmFragment(),true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.alarm) {
                    getSupportActionBar().setTitle("Clock");
                    //Toast.makeText(MainActivity.this, "ala", Toast.LENGTH_SHORT).show();
                    fragmentloading(new AlarmFragment(), false);
                } else if (id == R.id.systemtime) {
                    toolBar.setTitle("System Time");
                    fragmentloading(new ClockFragment(), false);
                } else if (id == R.id.stopwatch) {
                    toolBar.setTitle("Stop Watch");
                    fragmentloading(new StopwatchFragment(), false);

                } else if (id == R.id.about) {
                    toolBar.setTitle("About App");
                    fragmentloading(new AboutFragment(), false);
                }
                return true;
            }
        });
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolBar.setCollapseIcon(R.drawable.img);
            //toolBar.setSubtitle("set your alarm");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.actionbaritem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int actionid = item.getItemId();
        if (actionid == R.id.notes) {
            //Toast.makeText(this, "notes", Toast.LENGTH_SHORT).show();
            fragmentloading(new NotesFragment(), false);
            getSupportActionBar().setTitle("Temporary Notes");
        } else if (actionid == R.id.settings) {
            fragmentloading(new SettingFragment(), false);
            getSupportActionBar().setTitle("Settings");
            //Toast.makeText(this, "sett", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fragmentloading(Fragment fragment, boolean flag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (flag) {
            fragmentTransaction.add(R.id.container, fragment);
        } else {
            fragmentTransaction.replace(R.id.container, fragment);
        }
        fragmentTransaction.commit();
    }
}
