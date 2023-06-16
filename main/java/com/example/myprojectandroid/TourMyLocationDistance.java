package com.example.myprojectandroid;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class TourMyLocationDistance implements Parcelable {
    Tour tour;
    String distance;

    public TourMyLocationDistance() {
    }

    public TourMyLocationDistance(Tour tour, String distance) {
        this.tour = tour;
        this.distance = distance;
    }

    protected TourMyLocationDistance(Parcel in) {
        tour = in.readParcelable(Tour.class.getClassLoader());
        distance = in.readString();
    }

    public static final Creator<TourMyLocationDistance> CREATOR = new Creator<TourMyLocationDistance>() {
        @Override
        public TourMyLocationDistance createFromParcel(Parcel in) {
            return new TourMyLocationDistance(in);
        }

        @Override
        public TourMyLocationDistance[] newArray(int size) {
            return new TourMyLocationDistance[size];
        }
    };

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "TourMyLocationDistance{" +
                "tour=" + tour +
                ", distance='" + distance + '\'' +
                '}';
    }

    public final static Comparator<TourMyLocationDistance> BY_DISTANCE = new Comparator<TourMyLocationDistance>() {

        @Override
        public int compare(TourMyLocationDistance t1, TourMyLocationDistance t2) {
            Double distance1 = Double.parseDouble(t1.getDistance());
            Double distance2 = Double.parseDouble(t2.getDistance());
            return distance1.compareTo(distance2);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(tour, flags);
        dest.writeString(distance);
    }
}
