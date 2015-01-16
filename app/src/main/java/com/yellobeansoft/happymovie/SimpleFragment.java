package com.yellobeansoft.happymovie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Beboyz on 1/16/15 AD.
 */
public class SimpleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int position = 0;

        int[] colors = {
                Color.rgb(0xF6, 0x47, 0x47), // #F64747
                Color.rgb(0x9A, 0x12, 0xB3), // #9A12B3
                Color.rgb(0x22, 0xA7, 0xF0), // #22A7F0
        };

        final String[] names = {
                 "Favourite", "Nearby", "All Cinemas"
        };

        Bundle bundle = getArguments();

        position = bundle.getInt(SimplePagerAdapter.ARGS_POSITION);

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.layout_cinema_fav, container, false);

        TextView favCinema = (TextView) rootView.findViewById(R.id.txtFav);
        LinearLayout linearLayout = (LinearLayout)
        rootView.findViewById(R.id.linear_layout);

        linearLayout.setBackgroundColor(colors[position % colors.length]);
        favCinema.setText("Tab " + names[position % names.length]);

        return rootView;

    }
}
