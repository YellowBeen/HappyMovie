package com.yellobeansoft.happymovie;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ShowtimeCinemaActivity extends ActionBarActivity {

    private ListView lvShowtime;
    private TextView txtCinemaNameTH;
    private TextView txtShowDate;
    private ShowtimeCinemaAdapter lvShowtimeAdapter;
    private ShowTimeTABLE objShowTimeTABLE;
    private TimePickerDialog timePickerDialog;
    ArrayList<ShowTime> showTimesList;
    ArrayList<ShowTime> showTimesFilterList;
    private Button btnMap;
    private Button btnPhone;
    private Bundle bundle;
    private String mode;
    private Cinema chooseObjCinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_showtime_cinema);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // View Matching
        lvShowtime = (ListView) findViewById(R.id.lvShowtime);
        txtCinemaNameTH = (TextView) findViewById(R.id.txtCinemaNameTH);
        txtShowDate = (TextView) findViewById(R.id.txtShowDate);
        btnMap = (Button) findViewById(R.id.btnMap);
        btnPhone = (Button) findViewById(R.id.btnPhone);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ShowtimeCinemaActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Navigation with Google Map");
                builder.setMessage("Go to " + chooseObjCinema.getName()+" ?");
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();

                                final double latitude = Double.parseDouble(chooseObjCinema.getLatitude());
                                final double longitude = Double.parseDouble(chooseObjCinema.getLongtitude());
                                final double zoom = 11 ;
                                final String label = chooseObjCinema.getName();
                                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=%f&q=%f,%f(%s)",
                                        latitude, longitude, zoom, latitude, longitude, label);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

/*                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(intent);*/
            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ShowtimeCinemaActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirmation Call");
                builder.setMessage("Call " + chooseObjCinema.getName() + " at " + chooseObjCinema.getPhone() + " ?");
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + chooseObjCinema.getPhone()));
                                startActivity(intent);



                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        // Get input extras
        bundle = getIntent().getExtras();
        if (bundle != null){
            chooseObjCinema = bundle.getParcelable("chooseCinema");
            actionBar.setTitle(chooseObjCinema.getName());
            txtCinemaNameTH.setText(chooseObjCinema.getNameTH());
            try {
                addShowtimeData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setupShowtimeAdapter();
        }

        txtShowDate.setText(GetShowtimeUpdatedDate());
    }

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

    private void setupShowtimeAdapter() {
        if (showTimesList != null) {
            lvShowtimeAdapter = new ShowtimeCinemaAdapter(getBaseContext(), showTimesList);
            lvShowtime.setAdapter(lvShowtimeAdapter);
            lvShowtime.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    String showtimeConcat;
                    ArrayList<String> timeList = new ArrayList<String>();
                    // Concatenate showtime
                    ShowTime currShowtime = showTimesList.get(position);
                    timeList = currShowtime.getTimeList();
                    showtimeConcat = ShowtimeCinemaAdapter.JoinArray(timeList, "   ");
                    String shareShowtime =
                    buildShareShowtime(currShowtime.getName(), currShowtime.getMovieTitle(), showtimeConcat);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, shareShowtime);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    return true;
                }
            });
        }
    }

    private String buildShareShowtime(String cinema, String movie, String showtime){

        String shareShowtime = "";
        shareShowtime += movie;
        shareShowtime += "\n";
        shareShowtime += cinema;
        shareShowtime += "\n";
        shareShowtime += showtime;
        shareShowtime += "\n";

        String app = "See more for android... http://play.google.com/store/apps/details?id=" + getPackageName();
        shareShowtime += app;
        return shareShowtime;
    }

    private void addShowtimeData() throws ParseException {

        // Set Default Tab
        Intent intent = getIntent();
        if (!chooseObjCinema.getName().equalsIgnoreCase(null)) {
            objShowTimeTABLE = new ShowTimeTABLE(ShowtimeCinemaActivity.this);
            showTimesList = objShowTimeTABLE.getShowTimeByCinema(chooseObjCinema.getName(), "");
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
            objShowTimeTABLE = new ShowTimeTABLE(ShowtimeCinemaActivity.this);
            try {
                showTimesList.clear();
                showTimesFilterList = objShowTimeTABLE.getShowTimeByCinema(chooseObjCinema.getName(), filterTime.toString());
                showTimesList.addAll(showTimesFilterList);
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
            case android.R.id.home:
                this.finish();
                return true;
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
