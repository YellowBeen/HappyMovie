package com.yellobeansoft.happymovie;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Beboyz on 1/22/15 AD.
 */
public class ShowtimeExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ShowtimeGroup> showtimeGroupList;
    private ArrayList<ShowTime> mShowtimeList;
    private ShowTime objShowtime;
    private ArrayList<String> timeList = new ArrayList<String>();
    private String showtimeConcat;
    private String nextTime;

    public ShowtimeExpandAdapter(Context context, ArrayList<ShowtimeGroup> showtimeGroupList) {
        this.context = context;
        this.showtimeGroupList = new ArrayList<ShowtimeGroup>();
        this.showtimeGroupList.addAll(showtimeGroupList);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ShowTime> ShowtimeList = showtimeGroupList.get(groupPosition).getShowtime();
        return ShowtimeList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {



        ShowTime showtime = (ShowTime) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.layout_showtime_movie_item, null);
        }

        TextView txtShowtime = (TextView) view.findViewById(R.id.txtShowtime);
        TextView txtScreen = (TextView) view.findViewById(R.id.txtScreen);
        TextView txtType = (TextView) view.findViewById(R.id.txtType);

        objShowtime = showtimeGroupList.get(groupPosition).getShowtime().get(childPosition);

        txtScreen.setText("Screen: "+showtime.getScreen());
        txtType.setText(showtime.getType());

        // Concatenate showtime
        timeList = showtime.getTimeList();
        showtimeConcat = JoinArray(timeList, "   ");

        Spannable showtimeSpan = new SpannableString(showtimeConcat);
        showtimeSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        try {
            nextTime = showtime.getNextTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //mViewHolder.movieShowtime.setText(showtimeSpan);
        setShowtimeColor(txtShowtime, showtimeConcat, nextTime, Color.GRAY, Color.RED, Color.BLACK);

        return view;
    }

    private void setShowtimeColor(TextView view, String fullShowtime, String currentTime, int eColor, int cColor, int lColor) {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (!currentTime.equalsIgnoreCase("XXX")) {
            int cTimePosition = fullShowtime.indexOf(currentTime);
            int lTimePosition = cTimePosition + currentTime.length();
            int showtimeLength = fullShowtime.length();
            String earlyTime = fullShowtime.substring(0, cTimePosition);
            String lateTime = fullShowtime.substring(lTimePosition, showtimeLength);

            if (!earlyTime.equalsIgnoreCase(null)) {
                SpannableString earlyTimeSpannable = new SpannableString(earlyTime);
                earlyTimeSpannable.setSpan(new ForegroundColorSpan(eColor), 0, earlyTime.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(earlyTimeSpannable);
            }

            SpannableString currentTimeSpannable = new SpannableString(currentTime);
            currentTimeSpannable.setSpan(new ForegroundColorSpan(cColor), 0, currentTime.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(currentTimeSpannable);

            if (!lateTime.equalsIgnoreCase(null)) {
                SpannableString lateTimeSpannable = new SpannableString(lateTime);
                lateTimeSpannable.setSpan(new ForegroundColorSpan(lColor), 0, lateTime.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(lateTimeSpannable);
            }
            view.setText(builder, TextView.BufferType.SPANNABLE);
        }else{
            view.setText(fullShowtime);
        }
        //str.setSpan(new ForegroundColorSpan(cColor), cTimePosition, cTimePosition+currentTime.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static String JoinArray(List<String> list, String delim) {

        StringBuilder sb = new StringBuilder();

        String loopDelim = "";

        for(String s : list) {

            sb.append(loopDelim);
            sb.append(s);

            loopDelim = delim;
        }

        return sb.toString();
    }// JoinArray

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ShowTime> ShowtimeList = showtimeGroupList.get(groupPosition).getShowtime();
        return ShowtimeList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return showtimeGroupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return showtimeGroupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        ShowtimeGroup showtimeGroup = (ShowtimeGroup) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.layout_showtime_movie_group, null);
        }

        TextView txtCinemaNameTH = (TextView) view.findViewById(R.id.txtCinemaNameTH);
        TextView txtCinemaNameEN = (TextView) view.findViewById(R.id.txtCinemaNameEN);
        Button btnPhone = (Button) view.findViewById(R.id.btnPhone);
        Button btnNavi = (Button) view.findViewById(R.id.btnNavi);

        Cinema cinema = new Cinema();
        cinema = showtimeGroup.getShowtimeGroup();
        txtCinemaNameEN.setText(cinema.getName());
        txtCinemaNameTH.setText(cinema.getNameTH());
        final Cinema finalCinema = cinema;
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Confirmation Call");
                builder.setMessage("Call" + finalCinema.getPhone() + '?');
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + finalCinema.getPhone().toString()));
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        btnNavi.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCancelable(true);
                builder.setTitle("Navigation with Google Map");
                builder.setMessage("Go to " + finalCinema.getName()+" ?");
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final double latitude = Double.parseDouble(finalCinema.getLatitude());
                        final double longitude = Double.parseDouble(finalCinema.getLongtitude());
                        final double zoom = 11 ;
                        final String label = finalCinema.getName();
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=%f&q=%f,%f(%s)",
                                latitude, longitude, zoom, latitude, longitude, label);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}