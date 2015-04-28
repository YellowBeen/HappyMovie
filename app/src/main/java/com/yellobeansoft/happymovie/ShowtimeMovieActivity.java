package com.yellobeansoft.happymovie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ShowtimeMovieActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private TextView txtMovieNameTH;
    private TextView txtMovieNameEN;
    private TextView txtMovieLength;
    private TextView txtTomatoRating;
    private TextView txtRating;
    private TextView txtShowDate;
    private ImageView imgMovie;
    private Bundle bundle;
    private Movies chooseObjMovie;
    private String mMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_showtime_movie);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.hide(); // Hide Tab Viewpager

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        // View Matching
        imgMovie = (ImageView) findViewById(R.id.imgMovie);
        txtMovieNameTH = (TextView) findViewById(R.id.txtMovieNameTH);
        txtMovieNameEN = (TextView) findViewById(R.id.txtMovieNameEN);
        txtMovieLength = (TextView) findViewById(R.id.txtMovieLength);
        txtTomatoRating = (TextView) findViewById(R.id.txtTomatoRating);
        txtRating = (TextView) findViewById(R.id.txtRating);
        txtShowDate = (TextView) findViewById(R.id.txtShowDate);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        mViewPager = (ViewPager) findViewById(R.id.pager_showtime);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.notifyDataSetChanged();
        // Add Tabstrip
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_showtime_strip);
        pagerTabStrip.setTabIndicatorColor(Color.RED);

// Get input extras
        bundle = getIntent().getExtras();
        if (bundle != null){
            chooseObjMovie = bundle.getParcelable("chooseMovie");
            txtMovieNameEN.setText(chooseObjMovie.getMovieTitle());
            txtMovieNameTH.setText(chooseObjMovie.getMovieTitleTH());
            txtTomatoRating.setText(chooseObjMovie.getTomatoRating());
            txtMovieLength.setText("Runtime : "+chooseObjMovie.getMovieLength());
            txtRating.setText(chooseObjMovie.getRating());
            // Set image
            String path = chooseObjMovie.getMovieImg();


//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//            imageLoader.get(path, ImageLoader.getImageListener(
//                    imgMovie, R.drawable.ic_loadmovie, R.drawable.ic_loadmovie));

            // Loading image with Universal Image Loader
            mMode = getString(R.string.fmode);
            if (mMode.equalsIgnoreCase(getString(R.string.nmode))) {
                com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).resetViewBeforeLoading(true)
                        .showImageForEmptyUri(R.drawable.ic_loadmovie)
                        .showImageOnFail(R.drawable.ic_loadmovie)
                        .showImageOnLoading(R.drawable.ic_loadmovie).build();

                //download and display image from url
                imageLoader.displayImage(path, imgMovie, options);
            }
        }

        txtShowDate.setText(GetShowtimeUpdatedDate());


        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                Log.d("onPageSelect",Integer.toString(position));
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));

        }
        setDefaultTab();
    }//OnCreate

    public String GetShowtimeUpdatedDate() {
        String txtShowtimeUpdDate;
        Date dateShowtimeUpdDate = new Date();
        DataLoader dataLoader = new DataLoader(this);
        DateFormat dateToFormat = DateFormat.getDateInstance(DateFormat.FULL);

        dateShowtimeUpdDate = ConvertToDate(dataLoader.getShowTimeDate());
        txtShowtimeUpdDate = dateToFormat.format(dateShowtimeUpdDate);
        return txtShowtimeUpdDate;
    }

    private Date ConvertToDate(String dateString){
        SimpleDateFormat dateFromFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFromFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    private void setDefaultTab() {
//1.use fav tab if there is fav
        CinemaFavorite objCinemaFav = new CinemaFavorite();
        ArrayList<Cinema> cinemaList = objCinemaFav.getFavorites(getBaseContext());

        if (cinemaList.size() != 0 ){
            mViewPager.setCurrentItem(0);
        }
//2.use near by if GPS is on
        else {
            CinemaTABLE objCinemaTABLE = new CinemaTABLE(this);
            cinemaList = objCinemaTABLE.getNearByCinemas();
            if (cinemaList != null) {
                mViewPager.setCurrentItem(1);
            } else {
                mViewPager.setCurrentItem(2);
            }
        }
//3.use all cinema if 1,2 not match
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cinema, menu);
        // disable menu options
        return false;
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        //((FavFragment.OnRefreshListener) mSectionsPagerAdapter.getItem(tab.getPosition())).onRefresh();
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private final int PAGE_TOTAL = 3;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            Log.d("getItem ", Integer.toString(position));

            switch (position) {
                case 0:
                    return ShowtimeFavFragment.newInstance(chooseObjMovie.getMovieTitle());
                case 1:
                    return ShowtimeNearbyFragment.newInstance(chooseObjMovie.getMovieTitle());
                case 2:
                    return ShowtimeAllFragment.newInstance(chooseObjMovie);
            }
            return null;
        }


        @Override
        public int getCount() {
            return PAGE_TOTAL;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.viewpager1).toUpperCase(l);
                case 1:
                    return getString(R.string.viewpager2).toUpperCase(l);
                case 2:
                    return getString(R.string.viewpager3).toUpperCase(l);
            }
            return null;
        }
    }


}
