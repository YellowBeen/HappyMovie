package com.yellobeansoft.happymovie;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity{

    private TabHost tabHost;
    private Intent intentMovie;
    private Intent intentCinema;
    LocalActivityManager mLocalActivityManager;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        actionBar = getSupportActionBar();
        getSupportActionBar().hide();

        tabHost = (TabHost) findViewById(R.id.tabHost);
        mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);
        setTabhost();

    }


    private void setTabhost() {

        tabHost.setup();

        TabHost.TabSpec tabSpec;

        intentMovie = new Intent().setClass(this, MovieStaggeredActivity.class);
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
        Intent intent = getIntent();
        String chooseMovie = intent.getStringExtra("flagmovie");
        String movieId = intent.getStringExtra("id");
        if (chooseMovie != null) {
            if (chooseMovie.equalsIgnoreCase("1")) {
                tabHost.setCurrentTab(1);
                Toast.makeText(getBaseContext(), movieId, Toast.LENGTH_SHORT).show();
            } else {
                tabHost.setCurrentTab(0); // Movie tab
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(!isFinishing());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
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

        switch (item.getItemId()){
            case R.id.action_settings:
                return true;

        }
/*        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
