package com.yellobeansoft.happymovie;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Beboyz on 1/18/15 AD.
 */

public class MovieStaggeredActivity extends ActionBarActivity implements ActionBar.OnNavigationListener{

    private StaggeredGridView mGridView;
    private MovieStaggeredAdapter mAdapter;
    private ActionBar actionBar;
    private static long back_pressed;
    private ArrayList<SpinnerNavItem> navSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staggered);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.ic_launch_hpmv);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        navSpinner = new ArrayList<SpinnerNavItem>();
        navSpinner.add(new SpinnerNavItem(getResources().getString(R.string.spin_date)));
        navSpinner.add(new SpinnerNavItem(getResources().getString(R.string.spin_popular)));
        navSpinner.add(new SpinnerNavItem(getResources().getString(R.string.spin_imdb)));
        navSpinner.add(new SpinnerNavItem(getResources().getString(R.string.spin_name)));


        // title drop down adapter
        SpinnerNavAdapter spinneradapter = new SpinnerNavAdapter(getApplicationContext(), navSpinner);
        // assigning the spinner navigation
        actionBar.setListNavigationCallbacks(spinneradapter, this);
        // Sort default by Movie Release Date
        getMoviesListAndSetAdapter(getResources().getString(R.string.sort_date));


    }
    private void getMoviesListAndSetAdapter(String sortby) {

        MovieTable objMovieTab = new MovieTable(MovieStaggeredActivity.this);
        ArrayList<Movies> movieList = new ArrayList<Movies>();
        try {
            movieList = objMovieTab.getAllMoviesSortBy(sortby);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mAdapter = new MovieStaggeredAdapter(MovieStaggeredActivity.this, R.layout.layout_staggered_list, movieList);
        mGridView.setAdapter(mAdapter);

    }
    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        String sortby = new String();
        switch (i) {
            case 0: sortby = getResources().getString(R.string.sort_date); break;
            case 1: sortby = getResources().getString(R.string.sort_popular); break;
            case 2: sortby = getResources().getString(R.string.sort_imdb); break;
            case 3: sortby = getResources().getString(R.string.sort_name); break;
        }
        getMoviesListAndSetAdapter(sortby);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.instruction:

                return true;

            case R.id.feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", getResources().getString(R.string.feedback_mail), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_subj));
                emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.feedback_text));
                startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.feedback_send_action)));
                return true;
            case R.id.review:
                Intent reviewIntent = new Intent(Intent.ACTION_VIEW);
                //Try Google play
                reviewIntent.setData(Uri.parse(getString(R.string.review_market)));
                if (!toStartActivity(reviewIntent)) {
                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                    reviewIntent.setData(Uri.parse(getString(R.string.review_http)));
                    if (!toStartActivity(reviewIntent)) {
                        //Well if this also fails, we have run out of options, inform the user.
                        Toast.makeText(this, getString(R.string.review_errmsg), Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            case R.id.aboutus:
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean toStartActivity(Intent intent) {
        try
        {
            startActivity(intent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), getString(R.string.back_exit), Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();

    }

}