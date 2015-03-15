package com.yellobeansoft.happymovie;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ShowtimeMovieCinemaActivity extends ActionBarActivity {

    private ListView lvShowtime;
    private TextView txtCinemaNameTH;
    private TextView txtCinemaNameEN;
    private TextView txtMovieNameTH;
    private TextView txtMovieNameEN;
    private TextView txtMovieLength;
    private TextView txtTomatoRating;
    private ImageView imgMovie;
    private TextView txtRating;
    private TextView txtShowDate;
    private ShowtimeMovieCinemaAdapter lvShowtimeAdapter;
    private ShowTimeTABLE objShowTimeTABLE;
    ArrayList<ShowTime> showTimesList;
    private Button btnMap;
    private Button btnPhone;
    private Bundle bundle;
    private Cinema chooseObjCinema;
    private Movies chooseObjMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_showtime_movie_cinema);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.hide();

        // View Matching
        imgMovie = (ImageView) findViewById(R.id.imgMovie);
        txtMovieNameEN = (TextView) findViewById(R.id.txtMovieNameEN);
        txtMovieNameTH = (TextView) findViewById(R.id.txtMovieNameTH);
        txtRating = (TextView) findViewById(R.id.txtRating);
        txtTomatoRating = (TextView) findViewById(R.id.txtTomatoRating);
        txtMovieLength = (TextView) findViewById(R.id.txtMovieLength);
        txtShowDate = (TextView) findViewById(R.id.txtShowDate);

        txtCinemaNameEN = (TextView) findViewById(R.id.txtCinemaNameEN);
        txtCinemaNameTH = (TextView) findViewById(R.id.txtCinemaNameTH);

        btnMap = (Button) findViewById(R.id.btnNavi);
        btnPhone = (Button) findViewById(R.id.btnPhone);
        lvShowtime = (ListView) findViewById(R.id.lvShowtimeMovie);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ShowtimeMovieCinemaActivity.this);
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

            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ShowtimeMovieCinemaActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirmation Call");
                builder.setMessage("Call " + chooseObjCinema.getPhone() + " ?");
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
            txtCinemaNameTH.setText(chooseObjCinema.getNameTH());
            txtCinemaNameEN.setText(chooseObjCinema.getName());
            chooseObjMovie = bundle.getParcelable("chooseMovie");
            txtMovieNameTH.setText(chooseObjMovie.getMovieTitleTH());
            txtMovieNameEN.setText(chooseObjMovie.getMovieTitle());
            txtMovieLength.setText("Runtime : "+chooseObjMovie.getMovieLength());
            txtRating.setText(chooseObjMovie.getRating());
            txtTomatoRating.setText(chooseObjMovie.getTomatoRating());
            // Set image
            String path = chooseObjMovie.getMovieImg();
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageLoader.get(path, ImageLoader.getImageListener(
                    imgMovie, R.drawable.ic_loadmovie, R.drawable.ic_loadmovie));

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
        SimpleDateFormat dateFromFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFromFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    private void setupShowtimeAdapter() {
        if (showTimesList != null) {
            lvShowtimeAdapter = new ShowtimeMovieCinemaAdapter(getBaseContext(), showTimesList);
            lvShowtime.setAdapter(lvShowtimeAdapter);
        }
    }

    private void addShowtimeData() throws ParseException {


        // Set Default Tab
        Intent intent = getIntent();
        if (!chooseObjCinema.getName().equalsIgnoreCase(null)) {
            objShowTimeTABLE = new ShowTimeTABLE(ShowtimeMovieCinemaActivity.this);
            showTimesList = objShowTimeTABLE.getShowTimeByMovieCinema(chooseObjMovie.getMovieTitle(), chooseObjCinema.getName(), "");
        }
    }



}
