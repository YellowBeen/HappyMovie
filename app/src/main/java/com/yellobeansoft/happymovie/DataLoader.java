package com.yellobeansoft.happymovie;

import android.content.Context;

import com.google.gson.Gson;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

/**
 * Created by Jirawut-Jack on 22/01/2015.
 */
public class DataLoader {

    //Explicit
    public static final String PREFS_NAME = "CINEMA_APP";
    private static final String MOVIE_LOC_TYP = "MOVIE_LOC";
    private static final String SHOWTIME_LOC_TYP = "SHOWTIME_LOC";
    private static final String MOVIE_SERV_TYP = "MOVIE_SERV";
    private static final String SHOWTIME_SERV_TYP = "SHOWTIME_SERV";
    private MovieTable objMovieTab;
    private ShowTimeTABLE objShowTab;
    private CinemaTABLE objCinemaTab;
    private String urlMovie = "http://happymovie.esy.es/php_get_movie.json";
    private String urlCinema = "http://happymovie.esy.es/php_get_cinema.php";
    private String urlShowTime = "http://happymovie.esy.es/php_get_showtime_concat.php";
    private Context sContext;


    //Constructor
    public DataLoader(Context context) {
        sContext = context;
    }//Constructor


    //syncAll
    public void syncAll() {
        this.clearDateSetting();

        this.downloadServerDate();
        this.syncCinema();
        this.syncMovie();
        this.syncShowTime();
    }//syncAll


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


    //getDate
    private String getDate(String strTyp) {
        SharedPreferences settings;
        settings = sContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        return settings.getString(strTyp, "");
    }//getDate


    //syncMovie
    private void syncMovie() {
        if (this.checkSync(MOVIE_LOC_TYP)) {
            objMovieTab = new MovieTable(sContext);
            objMovieTab.deleteAllMovie();
            this.makeMovieRequest();
        }
    }//syncMovie


    //syncShowTme
    private void syncShowTime() {
        if (this.checkSync(SHOWTIME_LOC_TYP)) {
            objShowTab = new ShowTimeTABLE(sContext);
            objShowTab.deleteAllShowTime();
            this.makeShowTimeRequest();
        }
    }//syncShowTme


    //syncCinema
    private void syncCinema() {
        objCinemaTab = new CinemaTABLE(sContext);
        objCinemaTab.deleteAllCinema();
        this.makeCinemaRequest();
    }//syncCinema


    //getServerDate
    private void downloadServerDate() {
        String strServerDate = "X"; //********************
        setDate(MOVIE_SERV_TYP, strServerDate);
        setDate(SHOWTIME_SERV_TYP, strServerDate);
    }//getServerDate


    // setDate
    private void setDate(String strType, String strDateTime) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = sContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(strType, strDateTime);
        editor.commit();
    }// setDate

    // clearDateSetting
    private void clearDateSetting() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = sContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(MOVIE_LOC_TYP, "");
        editor.commit();

        editor.putString(MOVIE_SERV_TYP, "");
        editor.commit();

        editor.putString(SHOWTIME_LOC_TYP, "");
        editor.commit();

        editor.putString(SHOWTIME_SERV_TYP, "");
        editor.commit();
    }// clearDateSetting


    //checkSync
    private Boolean checkSync(String strType) {

        String strServerUpdate = "";
        String strLocalUpdate = "";

        switch (strType) {
            case MOVIE_LOC_TYP:
                strServerUpdate = getDate(MOVIE_SERV_TYP);
                strLocalUpdate = getDate(MOVIE_LOC_TYP);
            case SHOWTIME_LOC_TYP:
                strServerUpdate = getDate(SHOWTIME_SERV_TYP);
                strLocalUpdate = getDate(SHOWTIME_LOC_TYP);
        }

        if (strLocalUpdate.equals("")) {
            return true;
        } else {

            if (strLocalUpdate.equals(strServerUpdate)) {
                return false;
            } else {
                return true;
            }

        }

    }//checkSync


//////  Valley   ////////
//////////////////////////////////////////////////////////////////////////////////////////////////

    //makeCinemaRequest
    private void makeCinemaRequest() {

        JsonArrayRequest req = new JsonArrayRequest(urlCinema,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            objCinemaTab = new CinemaTABLE(sContext);
                            objCinemaTab.deleteAllCinema();

                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonCinema = (JSONObject) response.get(i);
                                String strName = jsonCinema.getString("name_en");
                                String strNameTH = jsonCinema.getString("name_th");
                                String strPhone = jsonCinema.getString("phone");
//                                String strBrand = objJSONObject.getString("brand");
                                String strBrand = "Major";
                                String strGroup = jsonCinema.getString("subbrand");
                                String strLat = jsonCinema.getString("lat");
                                String strLong = jsonCinema.getString("lng");
                                objCinemaTab.addNewCinema(strName, strNameTH, strPhone, strBrand, strGroup, strLat, strLong);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(sContext,
                                "Load Cinema Success",
                                Toast.LENGTH_LONG).show();


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }//makeCinemaRequest


    //makeMovieRequest
    private void makeMovieRequest() {

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
                                JSONObject jsonCinema = (JSONObject) response.get(i);
                                String strTitle = jsonCinema.getString("title_en");
                                String strTitleTH = jsonCinema.getString("title_th");
                                String strImage = jsonCinema.getString("image_url");
                                String strLength = jsonCinema.getString("duration");
                                String strYoutube = jsonCinema.getString("youtube_url");
                                objMovieTab.addNewMovie(strTitle, strTitleTH, strImage, strLength, strYoutube);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(sContext,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        setDate(MOVIE_LOC_TYP, getDate(MOVIE_SERV_TYP));
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "Error: " + error.getMessage());
                Toast.makeText(sContext,
                        "Error: " + error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }//makeMovieRequest


    //makeShowTimeRequest
    private void makeShowTimeRequest() {

        JsonArrayRequest req = new JsonArrayRequest(urlShowTime,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            objShowTab = new ShowTimeTABLE(sContext);
                            objShowTab.deleteAllShowTime();

                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonShowTime = response.getJSONObject(i);
                                String strName = jsonShowTime.getString("name_en");
                                String strTitle = jsonShowTime.getString("title_en");
                                String strScreen = jsonShowTime.getString("screen");
                                String strDate = jsonShowTime.getString("date");
                                String strTime = jsonShowTime.getString("time_info");
                                objShowTab.addNewShowTime(strName, strTitle, strScreen, strDate, strTime);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(sContext,
                                    "ShowTime Error:  " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        } catch (IOException e) {

                            e.printStackTrace();
                            Toast.makeText(sContext,
                                    "ShowTime Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }

                        setDate(SHOWTIME_LOC_TYP, getDate(SHOWTIME_SERV_TYP));
                        Toast.makeText(sContext,
                                "Load ShowTime Success",
                                Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "Error: " + error.getMessage());
                Toast.makeText(sContext,
                        "Error: " + error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }//makeShowTimeRequest


//---------------------- NOT USE -----------------------------------------------------


//    //////  Sync   ////////
////////////////////////////////////////////////////////////////////////////////////////////////////
//    //initJason
//    private String initJason(String strPhp) {
//
//        //Setup Policy
//        //เช็คว่าถ้า SDK มีค่า 9 (ginger bread เป็นต้นไป), ต้องเปิด Strict mode
//        //ถ้าไม่ทำ จะ connect ไม่ได้
//        if (Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(myPolicy);
//        }
//
//        //ทำ Streaming ข้อมูลจาก mySQL มาที่เครื่อง
//        InputStream objInputStream = null; //ประกาศ obj กำหนดให้เป็นค่าว่าง
//        String strJason = "";
//
//        //Create InputStream
//        try {
//            HttpClient objHttpClient = new DefaultHttpClient();
//            HttpPost objHttpPost = new HttpPost(strPhp);
//            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
//            HttpEntity objHttpEntity = objHttpResponse.getEntity();
//            objInputStream = objHttpEntity.getContent();
//
//        } catch (Exception e) {
//            //ขึ้น error ที่ message log
//            Log.d("Cinema", "Error from InputStream => " + e.toString());
//        }
//
//        //Create strJSON --> convert InputStream ให้เป็น String
//        try {
//
//            //ประกาศ Class Buffer
//            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
//            StringBuilder objStringBuilder = new StringBuilder();
//            // String ท่อนเล็กๆ ที่ถูกตัด และหลังจากโหลดทั้งหมดเสร็จ ก็จะถูกรวมกันอีกทีด้วย StringBuilder
//            String strLine = null;
//
//            while ((strLine = objBufferedReader.readLine()) != null) {
//                objStringBuilder.append(strLine);
//            }
//
//            objInputStream.close();
//            strJason = objStringBuilder.toString();
//
//        } catch (Exception e) {
//            //ขึ้น error ที่ message log
//            Log.d("Cinema", "Error from strJSON => " + e.toString());
//        }
//
//        return strJason;
//
//    }//initJason
//
//
//    //updateCinemaTable
//    private void updateCinemaTable(String strJason) {
//        try {
//
//            final JSONArray objJsonArray = new JSONArray(strJason);
//
//            objCinemaTab = new CinemaTABLE(sContext);
//            //objJsonArray.length คือจำนวน array หรือ record ของ data
//            for (int i = 0; i < objJsonArray.length(); i++) {
//                JSONObject objJSONObject = objJsonArray.getJSONObject(i);
//                String strName = objJSONObject.getString("name_en");
//                String strNameTH = objJSONObject.getString("name_th");
//                String strPhone = objJSONObject.getString("phone");
////                String strBrand = objJSONObject.getString("brand");
//                String strBrand = "Major";
//                String strGroup = objJSONObject.getString("subbrand");
//                String strLat = objJSONObject.getString("lat");
//                String strLong = objJSONObject.getString("lng");
//                objCinemaTab.addNewCinema(strName, strNameTH, strPhone, strBrand, strGroup, strLat, strLong);
//            }
//
//        } catch (Exception e) {
//            //ขึ้น error ที่ message log
//            Log.d("Cinema", "Error from update Movie SQLite => " + e.toString());
//        }
//
//    }//updateCinemaTable
//
//
//    //updateMovieTable
//    private void updateMovieTable(String strJason) {
//        //Update SQLite --> Loop strJason ลงใน table SQLite
//        try {
//
//            final JSONArray objJsonArray = new JSONArray(strJason);
//
//            objMovieTab = new MovieTable(sContext);
//            //objJsonArray.length คือจำนวน array หรือ record ของ data
//            for (int i = 0; i < objJsonArray.length(); i++) {
//                JSONObject objJSONObject = objJsonArray.getJSONObject(i);
//                String strTitle = objJSONObject.getString("title_en");
//                String strTitleTH = objJSONObject.getString("title_th");
//                String strImage = objJSONObject.getString("image_url");
//                String strLength = objJSONObject.getString("duration");
//                String strYoutube = objJSONObject.getString("youtube_url");
//                objMovieTab.addNewMovie(strTitle, strTitleTH, strImage, strLength, strYoutube);
//            }
//
//        } catch (Exception e) {
//            //ขึ้น error ที่ message log
//            Log.d("Cinema", "Error from update Movie SQLite => " + e.toString());
//        }
//    }//updateMovieTable
//
//
//    //updateShowTimeTable
//    private void updateShowTimeTable(String strJason) {
//        //Update SQLite --> Loop strJason ลงใน table SQLite
//        try {
//
//            final JSONArray objJsonArray = new JSONArray(strJason);
//
//            objShowTab = new ShowTimeTABLE(sContext);
//            //objJsonArray.length คือจำนวน array หรือ record ของ data
//            for (int i = 0; i < objJsonArray.length(); i++) {
//                JSONObject objJSONObject = objJsonArray.getJSONObject(i);
//                String strName = objJSONObject.getString("name_en");
//                String strTitle = objJSONObject.getString("title_en");
//                String strScreen = objJSONObject.getString("screen");
//                String strDate = objJSONObject.getString("date");
//                String strTime = objJSONObject.getString("time_info");
//                objShowTab.addNewShowTime(strName, strTitle, strScreen, strDate, strTime);
//            }
//
//        } catch (Exception e) {
//            //ขึ้น error ที่ message log
//            Log.d("Cinema", "Error from update ShowTime SQLite => " + e.toString());
//        }
//    }//updateShowTimeTable
//
//
//    //////  Async   ////////
////////////////////////////////////////////////////////////////////////////////////////////////////
//    // Async Task to access the web
//    private class JsonReadTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost(params[0]);
//            try {
//                HttpResponse response = httpclient.execute(httppost);
//                jsonResult = inputStreamToString(
//                        response.getEntity().getContent()).toString();
//                updateMovieTable(jsonResult);
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        private StringBuilder inputStreamToString(InputStream is) {
//            String rLine = "";
//            StringBuilder answer = new StringBuilder();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//
//            try {
//                while ((rLine = rd.readLine()) != null) {
//                    answer.append(rLine);
//                }
//            } catch (IOException e) {
//
//            }
//            return answer;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//        }
//    }// end async task

}
