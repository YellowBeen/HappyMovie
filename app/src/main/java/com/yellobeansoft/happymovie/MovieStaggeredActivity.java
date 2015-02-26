package com.yellobeansoft.happymovie;

import android.app.ProgressDialog;
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
    // Progress Dialog
    private ProgressDialog pDialog;
    private static long back_pressed;
    private int sortPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staggered);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.ic_launch_hpmv);
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        MovieTable objMovieTab = new MovieTable(MovieStaggeredActivity.this);
        ArrayList<Movies> movieList = new ArrayList<Movies>();
        try {
            movieList = objMovieTab.getAllMoviesSortBy("Date");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mAdapter = new MovieStaggeredAdapter(MovieStaggeredActivity.this, R.layout.layout_staggered_list, movieList);
        mGridView.setAdapter(mAdapter);

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
            case R.id.feedback:
                return true;
            case R.id.aboutus:
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), getString(R.string.back_exit), Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();

    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }
}