package com.yellobeansoft.happymovie;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    private ArrayList<SpinnerMoviesSort> sortSpinner;
    private MovieSortSpinnerAdapter sortAdapter;
    // Progress Dialog
    private ProgressDialog pDialog;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_staggered);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setIcon(getResources().getDrawable(R.drawable.ic_launch_hpmv));
        // Spinner title navigation data
        sortSpinner = new ArrayList<SpinnerMoviesSort>();
        sortSpinner.add(new SpinnerMoviesSort("Sort by Date"));
        sortSpinner.add(new SpinnerMoviesSort("Name"));
        sortSpinner.add(new SpinnerMoviesSort("IMDB Rating"));

        sortAdapter = new MovieSortSpinnerAdapter(sortSpinner, getApplicationContext());
        actionBar.setListNavigationCallbacks(sortAdapter, this);

        MovieTable objMovieTab = new MovieTable(MovieStaggeredActivity.this);
        ArrayList<Movies> movieList = new ArrayList<Movies>();
        try {
            movieList = objMovieTab.getAllMovies();
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
            case R.id.filter:
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