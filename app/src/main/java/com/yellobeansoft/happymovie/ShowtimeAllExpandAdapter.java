package com.yellobeansoft.happymovie;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/22/15 AD.
 */
public class ShowtimeAllExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<CinemaGroup> cinemaGroupList;
    private ArrayList<CinemaGroup> cinemaGroupOriList;
    private Cinema objCinema;

    public ShowtimeAllExpandAdapter(Context context, ArrayList<CinemaGroup> cinemaGroupList) {
        this.context = context;
        this.cinemaGroupList = new ArrayList<CinemaGroup>();
        this.cinemaGroupList.addAll(cinemaGroupList);
        this.cinemaGroupOriList = new ArrayList<CinemaGroup>();
        this.cinemaGroupOriList.addAll(cinemaGroupList);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Cinema> cinemaList = cinemaGroupList.get(groupPosition).getCinema();
        return cinemaList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {



        Cinema cinema = (Cinema) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.layout_showtime_allitem, null);
        }

        TextView name = (TextView) view.findViewById(R.id.txtCinema);
        TextView nameTH = (TextView) view.findViewById(R.id.txtCinemaTH);
        TextView distance = (TextView) view.findViewById(R.id.txtDistance);
        name.setText(cinema.getName().trim());
        nameTH.setText(cinema.getNameTH());

        objCinema = cinemaGroupList.get(groupPosition).getCinema().get(childPosition);
        double dist = objCinema.getDistance();
        String txtDistance = String.format("%.2f", dist);
        if (dist > 0){
            distance.setText(txtDistance+"km");
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objCinema = cinemaGroupList.get(groupPosition).getCinema().get(childPosition);
                Intent intent = new Intent(v.getContext(), ShowtimeCinemaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("chooseCinema",objCinema);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<Cinema> CinemaList = cinemaGroupList.get(groupPosition).getCinema();
        return CinemaList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return cinemaGroupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return cinemaGroupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        CinemaGroup CinemaGroup = (CinemaGroup) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.layout_cinema_allgroup, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.txtCinemaGrp);
        heading.setText(CinemaGroup.getCinemaGroup().trim());

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

    public void filterData(String query){

        query = query.toLowerCase();
        cinemaGroupList.clear();

        if(query.isEmpty()){
            cinemaGroupList.addAll(cinemaGroupOriList);
        }
        else {

            for(CinemaGroup CinemaGroup: cinemaGroupOriList){

                ArrayList<Cinema> CinemaList = CinemaGroup.getCinema();
                ArrayList<Cinema> newList = new ArrayList<Cinema>();
                for(Cinema Cinema: CinemaList){
                    if(Cinema.getName().toLowerCase().contains(query) ||
                            Cinema.getNameTH().toLowerCase().contains(query)){
                        newList.add(Cinema);
                    }
                }
                if(newList.size() > 0){
                    CinemaGroup nCinemaGroup = new CinemaGroup(CinemaGroup.getCinemaGroup(),newList);
                    cinemaGroupList.add(nCinemaGroup);
                }
            }
        }
        notifyDataSetChanged();

    }

}