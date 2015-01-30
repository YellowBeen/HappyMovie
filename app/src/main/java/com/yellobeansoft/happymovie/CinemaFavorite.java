package com.yellobeansoft.happymovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jirawut-Jack on 16/01/2015.
 */
public class CinemaFavorite {

    public static final String PREFS_NAME = "CINEMA_APP";
    public static final String FAVORITES = "Cinema_Favorite";

    public CinemaFavorite() {
        super();
    }

    public void saveFavorites(Context context, List<Cinema> favorites) {

        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public ArrayList<Cinema> getFavorites(Context context) {
        SharedPreferences settings;
        List<Cinema> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Cinema[] favoriteItems = gson.fromJson(jsonFavorites,
                    Cinema[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Cinema>(favorites);
        } else
            return null;

        return (ArrayList<Cinema>) favorites;
    }

    public void addFavorite(Context context, Cinema cinema) {
        List<Cinema> favorites = getFavorites(context);

        if (favorites == null) {
            favorites = new ArrayList<Cinema>();
        }

        favorites.add(cinema);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Cinema cinema) {
        ArrayList<Cinema> favorites = getFavorites(context);

        if (favorites != null) {
            for(int i=0;i<favorites.size();i++){
                if(favorites.get(i).getName().equalsIgnoreCase(cinema.getName())) {
                    favorites.remove(i);
                    saveFavorites(context, favorites);
                    break;
                }
            }
        }
    }

    public boolean checkExist (Context context, Cinema cinema) {

        ArrayList<Cinema> favorites = this.getFavorites(context);

        if (favorites == null) {
            return false;
        }

        for(int i=0;i<favorites.size();i++){
            if(favorites.get(i).getName().equalsIgnoreCase(cinema.getName())) {
                return true;
            }
        }

        return false;

    }


    public void removeAllFavorite(Context context) {
        ArrayList<Cinema> favorites = this.getFavorites(context);

        if (favorites != null) {

            int i = 0;

            do {
                Cinema objCinema = (Cinema) favorites.get(i);
                favorites.remove(objCinema);
                saveFavorites(context, favorites);
            } while (favorites.size() > 0);
        }
    }


}
