package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Beboyz on 3/30/15 AD.
 */
    public class TutorialPagerAdapter extends FragmentPagerAdapter {
        private final int NUM_ITEMS = 8;

        public TutorialPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public int getCount() {
            return NUM_ITEMS;
        }

        public Fragment getItem(int position) {
            Fragment fragment = new TutorialPage();
            Bundle args = new Bundle();
            args.putInt(TutorialPage.ARG_SECTION_NUMBER,
                    position + 1);
            fragment.setArguments(args);
            return fragment;
        }


    }


