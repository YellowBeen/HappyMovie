package com.yellobeansoft.happymovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ShowtimeCinemaActivity extends ActionBarActivity {

    private ListView lvShowtime;
    private TextView txtCinemaName;
    private TextView txtCinemaNameTH;
    private TextView txtShowDate;
    private ShowtimeCinemaAdapter lvShowtimeAdapter;
    private String chooseCinema;
    private String chooseCinemaTH;
    private ShowTimeTABLE objShowTimeTABLE;
    private TimePickerDialog timePickerDialog;
    ArrayList<ShowTime> showTimesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_showtime_cinema);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvShowtime = (ListView) findViewById(R.id.lvShowtime);
        txtCinemaName = (TextView) findViewById(R.id.txtCinemaName);
        txtCinemaNameTH = (TextView) findViewById(R.id.txtCinemaNameTH);
        txtShowDate = (TextView) findViewById(R.id.txtShowDate);

        try {
            addShowtimeData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setupShowtimeAdapter();

        txtCinemaName.setText(chooseCinema);
        txtCinemaNameTH.setText(chooseCinemaTH);
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        txtShowDate.setText(dateFormat.format(date));
    }

    private void setupShowtimeAdapter() {
        if (showTimesList != null) {
            lvShowtimeAdapter = new ShowtimeCinemaAdapter(getBaseContext(), showTimesList);
            lvShowtime.setAdapter(lvShowtimeAdapter);
        }
    }

    private void addShowtimeData() throws ParseException {


        // Set Default Tab
        Intent intent = getIntent();
        chooseCinema = intent.getStringExtra("Cinema");
        chooseCinemaTH = intent.getStringExtra("CinemaTH");
        if (!chooseCinema.equalsIgnoreCase(null)) {
            objShowTimeTABLE = new ShowTimeTABLE(ShowtimeCinemaActivity.this);
            showTimesList = objShowTimeTABLE.getShowTimeByCinema(chooseCinema, "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_showtime_cinema, menu);
        return true;
    }

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout radialPickerLayout, int hh, int mm) {
            StringBuilder filterTime = new StringBuilder().append(Integer.toString(hh)).append(":").append(Integer.toString(mm));
            Toast.makeText(getApplicationContext(),filterTime,Toast.LENGTH_SHORT).show();
            objShowTimeTABLE = new ShowTimeTABLE(ShowtimeCinemaActivity.this);
            try {
                showTimesList = objShowTimeTABLE.getShowTimeByCinema(chooseCinema, "");
               // showTimesList = objShowTimeTABLE.getShowTimeByCinema(chooseCinema, filterTime.toString());
                lvShowtime.setAdapter(lvShowtimeAdapter);
                lvShowtimeAdapter.notifyDataSetChanged();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
  /*          case R.id.action_settings:
                return true;*/
            case R.id.action_filterCinema:
                Calendar calendar = Calendar.getInstance();
                int hh = calendar.get(Calendar.HOUR_OF_DAY);
                int mm = calendar.get(Calendar.MINUTE);
                timePickerDialog = TimePickerDialog.newInstance(onTimeSetListener,
                        hh,     // Default hour
                        mm,     // Default min
                        true,   // True 0-23 , False PM , AM
                        false); // Vibrate
                timePickerDialog.show(getSupportFragmentManager(), "timePicker");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
