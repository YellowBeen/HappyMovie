package com.yellobeansoft.happymovie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Beboyz on 1/13/15 AD.
 */
public class Cinema implements Parcelable{

    private int id;
    private String name;
    private String nameTH;
    private String brand;
    private String group;
    private String phone;
    private String latitude;
    private String longtitude;
    private Double distance;

    public Cinema() {
        ;
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameTH() {
        return nameTH;
    }

    public void setNameTH(String nameTH) {
        this.nameTH = nameTH;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.nameTH);
        dest.writeString(this.brand);
        dest.writeString(this.group);
        dest.writeString(this.phone);
        dest.writeString(this.latitude);
        dest.writeString(this.longtitude);
        dest.writeDouble(this.distance);
    }

    public static final Parcelable.Creator<Cinema> CREATOR
            = new Parcelable.Creator<Cinema>() {

        public Cinema createFromParcel(Parcel in) {
            return new Cinema(in);
        }

        public Cinema[] newArray(int size) {
            return new Cinema[size];
        }
    };

    private Cinema(Parcel in){

        this.id = in.readInt();
        this.name = in.readString();
        this.nameTH = in.readString();
        this.brand = in.readString();
        this.group = in.readString();
        this.phone = in.readString();
        this.latitude = in.readString();
        this.longtitude = in.readString();
        this.distance = in.readDouble();
    }

}
