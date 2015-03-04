package com.yellobeansoft.happymovie;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

/**
 * Created by Jirawut-Jack on 22/01/2015
 */
public class DataLoader {

    //Explicit
    public static final String PREFS_NAME = "CINEMA_APP";

    private static final String MOVIE_LOC_TYP = "MOVIE_LOC";
    private static final String MOVIE_SERV_TYP = "MOVIE_SERV";
    private static final String SHOWTIME_LOC_TYP = "SHOWTIME_LOC";
    private static final String SHOWTIME_SERV_TYP = "SHOWTIME_SERV";

    private MovieTable objMovieTab;
    private CinemaTABLE objCinemaTab;
    private String urlMovie = "http://happymovie.elasticbeanstalk.com/php_get_movie.php";
//    private String urlCinema = "http://happymovie.esy.es/php_get_cinema.php";
    private String urlShowTime = "http://happymovie.elasticbeanstalk.com/php_get_showtime_concat_short.php";
    private String urlOverView = "http://happymovie.elasticbeanstalk.com/php_get_overview.php";
    private Context sContext;


    //Constructor
    public DataLoader(Context context) {
        sContext = context;
    }//Constructor


    //syncAll
    public void syncAll() {
        this.clearServerDate();
        this.downloadServerDate();

        if (!getDate(MOVIE_LOC_TYP).equals(getDate(MOVIE_SERV_TYP))) {
            this.makeMovieRequest();
        }
        this.makeShowTimeRequestJSON();
        if (!getDate(SHOWTIME_LOC_TYP).equals(getDate(SHOWTIME_SERV_TYP))) {
            this.makeShowTimeRequestJSON();
        }

        this.syncCinema();
    }//syncAll



    // clearServerDate
    private void clearServerDate() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = sContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(MOVIE_SERV_TYP, "");
        editor.commit();

        editor.putString(SHOWTIME_SERV_TYP, "");
        editor.commit();
    }// clearServerDate



    //downloadServerDate
    private void downloadServerDate() {
        this.makeOverViewRequest();
        while (getDate(MOVIE_SERV_TYP).equals("") || getDate(SHOWTIME_SERV_TYP).equals("")) {
        };
    }//downloadServerDate


    //checkMovieSyncDone
    public Boolean checkMovieSyncDone() {
        if (getDate(MOVIE_LOC_TYP).equals(getDate(MOVIE_SERV_TYP))) {
            return true;
        } else {
            return false;
        }
    }//checkMovieSyncDone


    //checkShowTimeSyncDone
    public Boolean checkShowTimeSyncDone() {
        if (getDate(SHOWTIME_LOC_TYP).equals(getDate(SHOWTIME_SERV_TYP))) {
            return true;
        } else {
            return false;
        }
    }//checkShowTimeSyncDone



    //getShowTimeDate
    public String getShowTimeDate() {
        return getDate(SHOWTIME_LOC_TYP);
    }//getShowTimeDate



    //syncCinema
    private void syncCinema() {

        try {
            //open the inputStream to the file
            AssetManager assetManager = sContext.getAssets();
            InputStream inputStream = assetManager.open("Cinema.json");

            int sizeOfJSONFile = inputStream.available();

            //array that will store all the data
            byte[] bytes = new byte[sizeOfJSONFile];

            //reading data into the array from the file
            inputStream.read(bytes);

            //close the input stream
            inputStream.close();

            String JSONString = null;
            JSONString = new String(bytes, "UTF-8");
            final JSONArray objJsonArray = new JSONArray(JSONString);

            objCinemaTab = new CinemaTABLE(sContext);
            objCinemaTab.deleteAllCinema();

            for (int i = 0; i < objJsonArray.length(); i++) {
                JSONObject objJSONObject = objJsonArray.getJSONObject(i);
                String strName = objJSONObject.getString("CinemaName");
                String strNameTH = objJSONObject.getString("CinemaName_TH");
                String strBrand = objJSONObject.getString("Brand");
                String strGroup = objJSONObject.getString("SubBrand");
                String strLat = objJSONObject.getString("Latitude");
                String strLong = objJSONObject.getString("Longitude");
                String strPhone = objJSONObject.getString("Phone");
                objCinemaTab.addNewCinema(strName, strNameTH, strPhone, strBrand, strGroup, strLat, strLong);
            }

            objCinemaTab.closeDB();
            Log.d("Cinema", "Cinema Done!");

        } catch (IOException ex) {
            ex.printStackTrace();
            objCinemaTab.closeDB();
        } catch (JSONException x) {
            x.printStackTrace();
            objCinemaTab.closeDB();
        }
    }//syncCinema


    // setDate
    private void setDate(String strType, String strFlag) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = sContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(strType, strFlag);
        editor.commit();
    }// setDate



    //getDate
    private String getDate(String strTyp) {
        SharedPreferences settings;
        settings = sContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        return settings.getString(strTyp, "");
    }//getDate



    //connectivityCheck
    public Boolean connectivityCheck() {
        ConnectivityManager connectivity = (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] inf = connectivity.getAllNetworkInfo();
            if (inf != null)
                for (int i = 0; i < inf.length; i++)
                    if (inf[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }//connectivityCheck



    //////  Valley   ////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    //makeMovieRequest
    private void makeMovieRequest() {

        Request.Priority priority = Request.Priority.HIGH;

        JsonArrayRequest req = new JsonArrayRequest(urlMovie,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            objMovieTab = new MovieTable(sContext);
                            objMovieTab.deleteAllMovie();

                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonMovie = (JSONObject) response.get(i);
                                String strTitle = jsonMovie.getString("title_en");
                                String strTitleTH = jsonMovie.getString("title_th");
                                String strImage = jsonMovie.getString("image_url");
                                String strLength = jsonMovie.getString("duration");
                                String strYoutube = jsonMovie.getString("youtube_url");
                                String strRating = jsonMovie.getString("imdb_rating");
                                String strIMDB = jsonMovie.getString("imdb_url");
                                String strDate = jsonMovie.getString("create_date");
//                                String strReleaseDate = jsonMovie.getString("release_date");
                                String strReleaseDate = "";
                                Integer intShowTimeCount = jsonMovie.getInt("showtime_count");
                                objMovieTab.addNewMovie(strTitle, strTitleTH, strImage, strLength, strYoutube, strRating,
                                                        strDate, strIMDB, strReleaseDate, intShowTimeCount);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Volley", "Movie Error: " + e.getMessage());
                            showErrorDialog();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("Volley", "Movie Error: " + e.getMessage());
                            showErrorDialog();
                        }

                        setDate(MOVIE_LOC_TYP, getDate(MOVIE_SERV_TYP));
                        Log.d("Movie", "Load Success");
                        Toast.makeText(sContext,
                                "Load Movie Success",
                                Toast.LENGTH_LONG).show();
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "Movie Error: " + error.getMessage());
                showErrorDialog();
            }
        }) {
            @Override
            public Request.Priority getPriority() {
                return Priority.HIGH;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }//makeMovieRequest


    //makeShowTimeRequestJSON
    private void makeShowTimeRequestJSON() {

        JsonArrayRequest req = new JsonArrayRequest(urlShowTime,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
                            ShowTimeTABLE objTab = new ShowTimeTABLE(sContext);
                            objTab.deleteShowTimeJSON();

                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonShowTime = response.getJSONObject(i);
                                ShowTime objShowTime = new ShowTime();
                                objShowTime.setName(jsonShowTime.getString("3"));
                                objShowTime.setMovieTitle(jsonShowTime.getString("2"));
                                objShowTime.setDate(jsonShowTime.getString("4"));
                                objShowTime.setScreen(jsonShowTime.getString("5"));
                                Integer intTimeID = jsonShowTime.getInt("6");
                                objShowTime.setTimeID(intTimeID);
                                objShowTime.setType(jsonShowTime.getString("9"));
                                objShowTime.setTime(jsonShowTime.getString("7"));
                                showTimeList.add(objShowTime);
                            }

                            objTab.addNewShowTimeJSON(showTimeList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Volley", "ShowTime Error: " + e.getMessage());
                            showErrorDialog();
                        }
                        Log.d("ShowTime", "Load Success");
                        setDate(SHOWTIME_LOC_TYP, getDate(SHOWTIME_SERV_TYP));
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "ShowTime Error: " + error.getMessage());
                showErrorDialog();
            }

        }) {
            @Override
            public Request.Priority getPriority() {
                return Priority.LOW;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }//makeShowTimeRequestJSON


    //makeOverViewRequest
    private void makeOverViewRequest() {

        JsonArrayRequest req = new JsonArrayRequest(urlOverView,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonOverView = response.getJSONObject(i);

                                if (jsonOverView.getString("tab_name").equals("movie")) {
                                    setDate(MOVIE_SERV_TYP, jsonOverView.getString("last_update"));
                                } else if (jsonOverView.getString("tab_name").equals("showtime_concat")) {
                                    setDate(SHOWTIME_SERV_TYP, jsonOverView.getString("last_update"));
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            showErrorDialog();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorDialog();
            }

        }) {
            @Override
            public Request.Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    } //makeOverViewRequest


    private void showErrorDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(sContext).create();
        alertDialog.setTitle("Data loading failed");
        alertDialog.setMessage("Please try again later.");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });

        alertDialog.show();
    }


}
