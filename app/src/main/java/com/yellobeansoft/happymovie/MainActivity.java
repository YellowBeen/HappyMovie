package com.yellobeansoft.happymovie;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;


public class MainActivity extends ActionBarActivity{

    private TabHost tabHost;
    private Intent intentMovie;
    private Intent intentCinema;
    LocalActivityManager mLocalActivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        //ActionBar actionBar = getActionBar();
        //actionBar.hide();

        tabHost = (TabHost) findViewById(R.id.tabHost);
        mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);
        setTabhost();

    }


    private void setTabhost() {

        tabHost.setup();

        TabHost.TabSpec tabSpec;

        intentMovie = new Intent().setClass(this, MovieActivity.class);
        tabSpec = tabHost.newTabSpec("movies");
        tabSpec.setIndicator("Movies");
        tabSpec.setContent(intentMovie);
        tabHost.addTab(tabSpec);

        intentCinema = new Intent().setClass(this, CinemaActivity.class);
        tabSpec = tabHost.newTabSpec("cinemas");
        tabSpec.setIndicator("Cinemas");
        tabSpec.setContent(intentCinema);
        tabHost.addTab(tabSpec);

        // Set Default Tab
        tabHost.setCurrentTab(0); // Movie tab

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
