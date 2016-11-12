package com.duybach.porodorotimer;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button main;
    Button focus;
    Button shortBreak;
    Button longBreak;
    int latestTime = 0;
    ArrayList<String> loggings;
    ArrayAdapter<String> adapter;
    TextView timerField;
    boolean activeTimer = false;
    CountDownTimer timer;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        main = (Button) findViewById(R.id.button);
        focus = (Button) findViewById(R.id.button2);
        shortBreak = (Button) findViewById(R.id.button3);
        longBreak = (Button) findViewById(R.id.button4);

        timerField = (TextView) findViewById(R.id.textView2);

        loggings = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                                           android.R.id.text1, loggings);

        timerField.setText("0:00");

        ListView listView = (ListView) findViewById(R.id.logging);
        listView.setAdapter(adapter);

        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();

                if (id == R.id.button) {
                    Log.v("Button Pressed", "main");

                    if (latestTime > 0 & !activeTimer) {
                        timer = new CountDownTimer(latestTime * 60000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                int minutes = (int) ((millisUntilFinished / 1000) / 60);
                                int seconds = (int) ((millisUntilFinished / 1000) % 60);

                                activeTimer = true;

                                if (minutes == 0) {
                                    timerField.setText(
                                            seconds + "s"
                                    );
                                } else {
                                    if (seconds < 10) {
                                        timerField.setText(
                                                minutes + ":0" + seconds
                                        );
                                    } else {
                                        timerField.setText(
                                                minutes + ":" + seconds
                                        );
                                    }
                                }
                            }

                            public void onFinish() {
                                activeTimer = false;
                                timerField.setText("done!");
                                v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                long[] pattern = {0, 500, 500, 500, 500, 500};
                                v.vibrate(pattern, -1);
                                loggings.add(0, Integer.toString(latestTime) + ":00");
                                adapter.notifyDataSetChanged();
                                main.setText("Start");
                            }
                        }.start();

                        main.setText("Stop");
                    } else {
                        if (activeTimer) {
                            timer.cancel();
                            activeTimer = false;
                        }

                        timerField.setText("0:00");
                        main.setText("Start");
                    }
                } else if (id == R.id.button2) {
                    Log.v("Button Pressed", "focus");
                    if (!activeTimer) {
                        timerField.setText("25:00");
                        latestTime = 25;
                    }
                } else if (id == R.id.button3) {
                    Log.v("Button Pressed", "shortBreak");
                    if (!activeTimer) {
                        timerField.setText("5:00");
                        latestTime = 5;
                    }
                } else if (id == R.id.button4) {
                    Log.v("Button Pressed", "longBreak");
                    if (!activeTimer) {
                        timerField.setText("15:00");
                        latestTime = 15;
                    }
                }
            }
        };

        main.setOnClickListener(oclBtnOk);
        focus.setOnClickListener(oclBtnOk);
        shortBreak.setOnClickListener(oclBtnOk);
        longBreak.setOnClickListener(oclBtnOk);
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
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
