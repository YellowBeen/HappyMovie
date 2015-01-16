package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Beboyz on 1/16/15 AD.
 */
public class SimplePagerAdapter extends FragmentStatePagerAdapter {

    public static final String ARGS_POSITION = "name";
    public static final int NUM_PAGES = 3;

    public SimplePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new SimpleFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_POSITION, i);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  "PAGE#" + (position + 1);
    }
}
