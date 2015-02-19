package com.yellobeansoft.happymovie;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import java.util.Timer;


/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class SplashScreen extends Activity {

    // Progress Dialog
    private ProgressDialog mProgress;
    private ProgressBar progressBar;

    private Integer mProgressDialog = 2;
    private Integer intMax = 660;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Remove the Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Get the view from splash_screen.xml
        setContentView(R.layout.layout_splash);

        Thread syncLoading = new Thread() {

            public void run() {
                try {
                    DataLoader objLoader = new DataLoader(SplashScreen.this);
                    objLoader.syncAll();
                    sleep(2000);
                    while (!objLoader.checkShowTimeSyncDone()){
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    Intent myIntent = new Intent(SplashScreen.this,
                            MainActivity.class);
                    startActivity(myIntent);
                }
            }

        };

        syncLoading.start();
    }

}

//    new LoadData().execute();
//
//
////        // Task to do when the timer ends
////        TimerTask ShowSplash = new TimerTask() {
////            @Override
////            public void run() {
////
////                finish();
////
////                // Start MainActivity.class
////                Intent myIntent = new Intent(SplashScreen.this,
////                        MainActivity.class);
////                startActivity(myIntent);
////
////                Log.d("SplashScreen","Before Load");
////                new LoadData().execute();
////                Log.d("SplashScreen","After Load");
////            }
////        };
////
////        // Start the timer
////        RunSplash.schedule(ShowSplash, Delay);
//    }
//
//
//    class LoadData extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected void onPreExecute() {
////            mProgress = new ProgressDialog(SplashScreen.this);
////            mProgress.setMessage("Downloading data...");
////            mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
////            mProgress.setProgress(0);
////            mProgress.setMax(intMax);
////            mProgress.show();
//
//            progressBar = (ProgressBar) findViewById(R.id.progressBar);
//            progressBar.setVisibility(View.VISIBLE);
//            super.onPreExecute();
//        }
//
//
//        @Override
//        protected String doInBackground(String... params) {
//            DataLoader objLoader = new DataLoader(SplashScreen.this);
////
//            objLoader.resetProgress();
//            objLoader.syncAll();
//
////            // Dummy code
////            for (int i = 0; i <= 100; i += 5) {
////                try {
////                    Thread.sleep(200);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                publishProgress(objLoader.getProgress());
////            }
//
//            while (!objLoader.checkShowTimeSyncDone()){
//            }
//
//                return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... integers) {
//            super.onProgressUpdate(integers);
////            progressBar.setProgress(integers[0]);
////            mProgress.setProgress(integers[0]);
//        }
//
//
//        @Override
//        protected void onPostExecute(String testStr) {
//            // updating UI from Background Thread
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    // dismiss the dialog after getting song information
////                    mProgress.dismiss();
//                    progressBar.setVisibility(View.INVISIBLE);
//
//                    finish();
//                    Intent myIntent = new Intent(SplashScreen.this,
//                            MainActivity.class);
//                    startActivity(myIntent);
//                }
//            });
//        }
//
//    }


//}
