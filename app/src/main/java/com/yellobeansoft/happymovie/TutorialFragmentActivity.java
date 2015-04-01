package com.yellobeansoft.happymovie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.Locale;

/**
 * Created by Beboyz on 3/30/15 AD.
 */
public class TutorialFragmentActivity extends FragmentActivity {

     ViewPager pagerTutorial;
     CirclePageIndicator indicator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tutorial);

        TutorialPagerAdapter adapter = new TutorialPagerAdapter(getSupportFragmentManager());
        pagerTutorial = (ViewPager) findViewById(R.id.pagerTutorial);
        pagerTutorial.setAdapter(adapter);

        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pagerTutorial);
        indicator.setSnap(true);
    }

}
