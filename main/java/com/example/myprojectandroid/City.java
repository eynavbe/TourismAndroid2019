package com.example.myprojectandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable{
    private int id;
    private String name;
    private String fullDescription;
    private String VendorName;
    private String productURL;
    private String picURL;
    private String faq;
    private String region;
    private double x;
    private double y;
    private String youtube;

    public City(int id, String name, String fullDescription, String vendorName, String productURL, String picURL, String faq, String region, double x, double y, String youtube) {
        this.id = id;
        this.name = name;
        this.fullDescription = fullDescription;
        this.VendorName = vendorName;
        this.productURL = productURL;
        this.picURL = picURL;
        this.faq = faq;
        this.region = region;
        this.x = x;
        this.y = y;
        this.youtube = youtube;
    }

    public City() {

    }

    protected City(Parcel in) {
        id = in.readInt();
        name = in.readString();
        fullDescription = in.readString();
        VendorName = in.readString();
        productURL = in.readString();
        picURL = in.readString();
        faq = in.readString();
        region = in.readString();
        x = in.readDouble();
        y = in.readDouble();
        youtube = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
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

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getFaq() {
        return faq;
    }

    public void setFaq(String faq) {
        this.faq = faq;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", VendorName='" + VendorName + '\'' +
                ", productURL='" + productURL + '\'' +
                ", picURL='" + picURL + '\'' +
                ", faq='" + faq + '\'' +
                ", region='" + region + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", youtube='" + youtube + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(fullDescription);
        dest.writeString(VendorName);
        dest.writeString(productURL);
        dest.writeString(picURL);
        dest.writeString(faq);
        dest.writeString(region);
        dest.writeDouble(x);
        dest.writeDouble(y);
        dest.writeString(youtube);
    }
}
