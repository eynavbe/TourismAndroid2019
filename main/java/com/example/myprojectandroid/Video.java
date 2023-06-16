package com.example.myprojectandroid;

import android.os.Parcel;
import android.os.Parcelable;

class Video implements Parcelable {
    String title;
    String id;
    String city;
    String length;
    String pic;

    public Video() {
    }

    public Video(String title, String id, String city, String length, String pic) {
        this.title = title;
        this.id = id;
        this.city = city;
        this.length = length;
        this.pic = pic;
    }

    protected Video(Parcel in) {
        title = in.readString();
        id = in.readString();
        city = in.readString();
        length = in.readString();
        pic = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getTitle() {
        return title;
    }
    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }
    public String getLength() {
        return length;
    }

    public String getPic() {
        return pic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(city);
        dest.writeString(length);
        dest.writeString(pic);
    }

    @Override
    public String toString() {
        return "Video{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", length='" + length + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}

