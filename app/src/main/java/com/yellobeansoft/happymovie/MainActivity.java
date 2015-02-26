package com.yellobeansoft.happymovie;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


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

        // Ads
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest.Builder adBuilder = new AdRequest.Builder();
        AdRequest adRequest = adBuilder.build();
        adBuilder.addTestDevice("31E338822F0C538D6154F8600E33F73D"); // BOY
        adBuilder.addTestDevice("1188663E6F205EBEF8E8C111C2B7C406"); // JACK
        //Location location = new Location("AdMobProvider");
        mAdView.loadAd(adRequest);

        actionBar = getSupportActionBar();
        getSupportActionBar().hide();

        tabHost = (TabHost) findViewById(R.id.tabHost);

        mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);
        setTabhost();
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#6d6d6d")); // unselected
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
        }

        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#B90000")); // selected
        TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#ffffff"));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#6d6d6d")); // unselected
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#ffffff"));
                }

                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#B90000")); // selected
                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#ffffff"));

            }
        });
    }

    private void setTabhost() {

        tabHost.setup();

        TabHost.TabSpec tabSpec;

        intentMovie = new Intent().setClass(this, MovieStaggeredActivity.class);
        tabSpec = tabHost.newTabSpec("movies");
        tabSpec.setIndicator("Movies");
        tabSpec.setContent(intentMovie);
        tabHost.addTab(tabSpec);

        intentCinema = new Intent().setClass(this, CinemaFragmentActivity.class);
        tabSpec = tabHost.newTabSpec("cinemas");
        tabSpec.setIndicator("Cinemas");
        tabSpec.setContent(intentCinema);
        tabHost.addTab(tabSpec);

        // Set Default Tab
        Intent intent = getIntent();
        String chooseMovie = intent.getStringExtra("MovieTitle");
        if (chooseMovie != null) {
                tabHost.setCurrentTab(1);
            chooseMovie = null;
         } else {
                tabHost.setCurrentTab(0);
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
