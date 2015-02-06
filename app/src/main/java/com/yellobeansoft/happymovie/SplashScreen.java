package com.yellobeansoft.happymovie;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class SplashScreen extends Activity {
//    Set Duration of the Splash Screen
    long Delay = 500;

    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Remove the Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Get the view from splash_screen.xml
        setContentView(R.layout.layout_splash);

        // Create a Timer
        Timer RunSplash = new Timer();

        new LoadData().execute();


//        // Task to do when the timer ends
//        TimerTask ShowSplash = new TimerTask() {
//            @Override
//            public void run() {
//
//                finish();
//
//                // Start MainActivity.class
//                Intent myIntent = new Intent(SplashScreen.this,
//                        MainActivity.class);
//                startActivity(myIntent);
//
//                Log.d("SplashScreen","Before Load");
//                new LoadData().execute();
//                Log.d("SplashScreen","After Load");
//            }
//        };
//
//        // Start the timer
//        RunSplash.schedule(ShowSplash, Delay);
    }


    class LoadData extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(SplashScreen.this);
//            pDialog.setMessage("Loading Data ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            DataLoader objLoader = new DataLoader(SplashScreen.this);
            objLoader.syncAll();
//            while ( !objLoader.checkMovieSyncDone() || !objLoader.checkShowTimeSyncDone() ) {
            while ( !objLoader.checkMovieSyncDone() ) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... integers) {
        }


        @Override
        protected void onPostExecute(String testStr) {
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // dismiss the dialog after getting song information
//                    pDialog.dismiss();

                    finish();
                    Intent myIntent = new Intent(SplashScreen.this,
                            MainActivity.class);
                    startActivity(myIntent);

                }
            });
        }

    }


}
