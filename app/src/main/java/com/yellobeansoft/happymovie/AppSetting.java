package com.yellobeansoft.happymovie;

import android.content.Context;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by jirawuts on 4/28/15 AD.
 */
public class AppSetting {

    String mode;

    public String getmode(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        boolean bMode = SP.getBoolean("ch_mode",true);

        if (bMode) {
            return context.getString(R.string.nmode);
        } else {
            return context.getString(R.string.fmode);
        }

    }

}
