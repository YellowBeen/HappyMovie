package com.yellobeansoft.happymovie;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class ShowtimeFragmentAdapter extends BaseExpandableListAdapter {

    protected static int timeDigit = 5;
    ArrayList<ShowTime> mShowtimeList;
    private Context mContext;
    private LayoutInflater mInflater;
    private ViewHolder mViewHolder;
    private GroupViewHolder mGroupViewHolder;
    private MovieTable movieTable;
    private Movies objMovie;
    private Cinema objCinema;
    private String showtimeConcat;
    private String nextTime;
    private ArrayList<String> timeList = new ArrayList<String>();
    private ShowTimeTABLE objShowTimeTABLE;
    private ArrayList<ShowtimeGroup> objShowtimeGroupList;
    private ShowtimeGroup objShowtimeGroup;
    private CinemaFavorite objCinemaFav;
    private ShowtimeFragmentAdapter mShowtimeFragmentAdaptor;//for notify changed data
    private Intent mIntent;
    private RelativeLayout mShowtimeLayout;//for share showtime info


    public ShowtimeFragmentAdapter(Context context,Intent intent) throws ParseException {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        objShowtimeGroupList = new ArrayList<ShowtimeGroup>();
        objCinemaFav = new CinemaFavorite();
        mShowtimeFragmentAdaptor = this;
        mIntent = intent;
        showShowtimeFavor();
    }

    private void showShowtimeFavor() {
        String chooseMovie = mIntent.getStringExtra("MovieTitle");
        ArrayList<Cinema> lists = objCinemaFav.getFavorites(mContext);
        if (lists != null) {
            for (int i = 0; i < lists.size(); i++) {
                objShowTimeTABLE = new ShowTimeTABLE(mContext);
                try {
                    if (chooseMovie == null) {
                        mShowtimeList = objShowTimeTABLE.getShowTimeByCinema(lists.get(i).getName(), "");
                    }else{
                        mShowtimeList = objShowTimeTABLE.getShowTimeByMovieCinema(chooseMovie,lists.get(i).getName().toString(),"");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                objShowtimeGroup = new ShowtimeGroup(lists.get(i), mShowtimeList);
                objShowtimeGroupList.add(objShowtimeGroup);
                //objShowTimeTABLE.closeDB();pon test
            }
        }
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

    @Override
    public int getGroupCount() {
        return objShowtimeGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ShowTime> objShowtime = objShowtimeGroupList.get(groupPosition).getShowtime();
        if (objShowtime==null){
            return 0;
        }else {
            return objShowtimeGroupList.get(groupPosition).getShowtime().size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return objShowtimeGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {//OK
            ArrayList<ShowTime> showtimeList = objShowtimeGroupList.get(groupPosition).getShowtime();
            return showtimeList.get(childPosition);

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ShowtimeGroup showtimeGroup = (ShowtimeGroup) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_showtime_fragment_group, null);
        }
        mGroupViewHolder = new GroupViewHolder();
        mGroupViewHolder.cinemaEN = (TextView) convertView.findViewById(R.id.txtCinemaNameEN);
        mGroupViewHolder.cinemaTH = (TextView) convertView.findViewById(R.id.txtCinemaNameTH);
        mGroupViewHolder.favimg = (Button) convertView.findViewById(R.id.btnFavourite);
        mGroupViewHolder.phone = (Button) convertView.findViewById(R.id.btnPhone);
        mGroupViewHolder.map = (Button) convertView.findViewById(R.id.btnNavi);
        mGroupViewHolder.cinemaTH.setText(showtimeGroup.getShowtimeGroup().getNameTH().toString());
        mGroupViewHolder.cinemaEN.setText(showtimeGroup.getShowtimeGroup().getName().toString());


        mGroupViewHolder.favimg.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                objCinema = showtimeGroup.getShowtimeGroup();
                if (objCinemaFav.checkExist(mContext, objCinema)) {
                    objCinemaFav.removeFavorite(mContext, objCinema);
                    objShowtimeGroupList.remove(groupPosition);
                } else {
                    objCinemaFav.addFavorite(mContext, objCinema);
                }

                mShowtimeFragmentAdaptor.notifyDataSetChanged();
            }
        });

        mGroupViewHolder.phone.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Confirmation Call");
                builder.setMessage("Call" + showtimeGroup.getShowtimeGroup().getPhone().toString() + '?');
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + showtimeGroup.getShowtimeGroup().getPhone().toString()));
                        mContext.startActivity(intent);
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
        mGroupViewHolder.map.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCancelable(true);
                builder.setTitle("Navigation with Google Map");
                builder.setMessage("Go to " + showtimeGroup.getShowtimeGroup().getName()+" ?");
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final double latitude = Double.parseDouble(showtimeGroup.getShowtimeGroup().getLatitude());
                        final double longitude = Double.parseDouble(showtimeGroup.getShowtimeGroup().getLongtitude());
                        final double zoom = 11 ;
                        final String label = showtimeGroup.getShowtimeGroup().getName();
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=%f&q=%f,%f(%s)",
                                latitude, longitude, zoom, latitude, longitude, label);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                        mContext.startActivity(intent);
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

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ShowTime showtime = (ShowTime) getChild(groupPosition, childPosition);
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_showtime_fragment_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.movieName = (TextView) convertView.findViewById(R.id.txtMovieNameEN);
            mViewHolder.movieNameTH = (TextView) convertView.findViewById(R.id.txtMovieNameTH);
            mViewHolder.movieImg = (ImageView) convertView.findViewById(R.id.imgMovie);
            mViewHolder.movieRating = (TextView) convertView.findViewById(R.id.txtRating);
            mViewHolder.movieShowtime = (TextView) convertView.findViewById(R.id.txtShowtime);
            //mViewHolder.movieDate = (TextView) convertView.findViewById(R.id.txtDate);
            mViewHolder.screen = (TextView) convertView.findViewById(R.id.txtScreen);
            mViewHolder.showtimeType = (TextView) convertView.findViewById(R.id.txtType);
            convertView.setTag(mViewHolder);


        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        // Get data from movie class
        movieTable = new MovieTable(mContext);
        try {
            objMovie = movieTable.getMovies(showtime.getMovieTitle());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set image
        String path = objMovie.getMovieImg();
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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
        setShowtimeColor(mViewHolder.movieShowtime, showtimeConcat, nextTime, Color.GRAY, Color.RED, Color.BLACK);
        // Set Textview (Movie object)
        mViewHolder.movieName.setText(objMovie.getMovieTitle());
        mViewHolder.movieNameTH.setText(objMovie.getMovieTitleTH());
        if (objMovie.getRating().equals("n/A")){
            mViewHolder.movieRating.setText(objMovie.getRating());
        }else{
            mViewHolder.movieRating.setText(objMovie.getRating()+"/10");
        }
        //mViewHolder.movieDate.setText(objMovie.getDate());
        // Set Textview (Showtime object)
        mViewHolder.showtimeType.setText(showtime.getType());
        mViewHolder.screen.setText(showtime.getScreen());


//        imageLoader.get(path, ImageLoader.getImageListener(
//                mViewHolder.movieImg, R.drawable.ic_loadmovie, R.drawable.ic_loadmovie));


        // Loading image with Universal Image Loader
        com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.ic_loadmovie)
                .showImageOnFail(R.drawable.ic_loadmovie)
                .showImageOnLoading(R.drawable.ic_loadmovie).build();

        //download and display image from url
        imageLoader.displayImage(path, mViewHolder.movieImg, options);


        mShowtimeLayout = (RelativeLayout) convertView.findViewById(R.id.ShowtimeLayout);
        mShowtimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String showtimeinfo = new String();
                ShowtimeGroup mShowtimeGroup = objShowtimeGroupList.get(groupPosition);
                Date date = new Date();
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
                showtimeinfo = objMovie.getMovieTitle() + "/" +
                        objMovie.getMovieTitleTH() +
                        System.getProperty("line.separator") +
                        showtime.getName() + "/" + mShowtimeGroup.getShowtimeGroup().getNameTH() +
                        System.getProperty("line.separator") +
                        showtime.getTime() +
                        System.getProperty("line.separator") +
                        dateFormat.format(date);
                ShareLine share = new ShareLine(mContext);
                share.sendTextHandler(v,showtimeinfo);
            }
        });
        return convertView;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewHolder {
        public ImageView movieImg;
        public TextView movieName;
        public TextView movieNameTH;
        public TextView movieRating;
        public TextView movieShowtime;
        public TextView movieDate;
        public TextView showtimeType;
        public TextView screen;

    }// class ViewHolder
    private static class GroupViewHolder {
        public TextView cinemaEN;
        public TextView cinemaTH;
        public Button favimg;
        public Button phone;
        public Button map;

    }// class GroupViewHolder
    @Override
    public void notifyDataSetChanged() {
        //showShowtimeFavor();
        super.notifyDataSetChanged();
    }
}
