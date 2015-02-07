package com.yellobeansoft.happymovie;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/18/15 AD.
 */

public class MovieStaggeredActivity extends ActionBarActivity implements ActionBar.OnNavigationListener{

    private StaggeredGridView mGridView;
    private MovieStaggeredAdapter mAdapter;
    private ActionBar actionBar;
    private ArrayList<SpinnerMoviesSort> sortSpinner;
    private MovieSortSpinnerAdapter sortAdapter;
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staggered);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Spinner title navigation data
        sortSpinner = new ArrayList<SpinnerMoviesSort>();
        sortSpinner.add(new SpinnerMoviesSort("Sort by Date"));
        sortSpinner.add(new SpinnerMoviesSort("Name"));
        sortSpinner.add(new SpinnerMoviesSort("IMDB Rating"));

        sortAdapter = new MovieSortSpinnerAdapter(sortSpinner, getApplicationContext());
        actionBar.setListNavigationCallbacks(sortAdapter, this);

        MovieTable objMovieTab = new MovieTable(MovieStaggeredActivity.this);
        ArrayList<Movies> movieList = new ArrayList<Movies>();
        movieList = objMovieTab.getAllMovies();

        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mAdapter = new MovieStaggeredAdapter(MovieStaggeredActivity.this, R.layout.layout_staggered_list, movieList);
        mGridView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }
}