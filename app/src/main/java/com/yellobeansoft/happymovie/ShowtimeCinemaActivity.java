package com.yellobeansoft.happymovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class ShowtimeCinemaActivity extends ActionBarActivity {

    private ListView lvShowtime;
    private ShowtimeCinemaAdapter lvShowtimeAdapter;
    private String chooseCinema;
    private ShowTimeTABLE objShowTimeTab;
    ArrayList<ShowTime> showTimesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_showtime_cinema);

        lvShowtime = (ListView) findViewById(R.id.lvShowtime);
        addShowtimeData();
        setupShowtimeAdapter();

    }

    private void setupShowtimeAdapter() {
        lvShowtimeAdapter = new ShowtimeCinemaAdapter(getBaseContext(), showTimesList);
        lvShowtime.setAdapter(lvShowtimeAdapter);
    }

    private void addShowtimeData() {

        // Set Default Tab
        Intent intent = getIntent();
        chooseCinema = intent.getStringExtra("Cinema");
        objShowTimeTab = new ShowTimeTABLE(ShowtimeCinemaActivity.this);
        if (!chooseCinema.equalsIgnoreCase(null)) {
            showTimesList = objShowTimeTab.getShowTimeByCinema(chooseCinema, "");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_showtime_cinema, menu);
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
