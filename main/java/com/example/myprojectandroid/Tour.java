package com.example.myprojectandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class Tour implements Parcelable {
    private int Id;
    private double X,Y;
    private String Type,DifficultyLevel,TrackType,TrackDuration,TrackLength,AdmissionCharge,BestSeason,Precautions,ByAppointment;
    private String Name,TypeRecreation ,City,NearTo,OpeningHours,Parking,Phone,Region,SuitableForChildren,URL;
    private String FullDescription,ProductUrl,PicUrl,Accessibility,Address;

    public Tour() {
    }

    public Tour(int id, String name, String phone) {
        Id = id;
        Name = name;
        Phone = phone;
    }
    public Tour(String name, String phone) {
        Name = name;
        Phone = phone;
    }
    public Tour(double x, double y, String type, String difficultyLevel, String trackType, String trackDuration, String trackLength, String admissionCharge, String bestSeason, String precautions, String byAppointment, String name, String typeRecreation, String city, String nearTo, String openingHours, String parking, String phone, String region, String suitableForChildren, String URL, String fullDescription, String productUrl, String picUrl, String accessibility, String address) {
        X = x;
        Y = y;
        Type = type;
        DifficultyLevel = difficultyLevel;
        TrackType = trackType;
        TrackDuration = trackDuration;
        TrackLength = trackLength;
        AdmissionCharge = admissionCharge;
        BestSeason = bestSeason;
        Precautions = precautions;
        ByAppointment = byAppointment;
        Name = name;
        TypeRecreation = typeRecreation;
        City = city;
        NearTo = nearTo;
        OpeningHours = openingHours;
        Parking = parking;
        Phone = phone;
        Region = region;
        SuitableForChildren = suitableForChildren;
        this.URL = URL;
        FullDescription = fullDescription;
        ProductUrl = productUrl;
        PicUrl = picUrl;
        Accessibility = accessibility;
        Address = address;
    }
    public Tour(int id, double x, double y, String type, String difficultyLevel, String trackType, String trackDuration, String trackLength, String admissionCharge, String bestSeason, String precautions, String byAppointment, String name, String typeRecreation, String city, String nearTo, String openingHours, String parking, String phone, String region, String suitableForChildren, String URL, String fullDescription, String productUrl, String picUrl, String accessibility, String address) {
        Id = id;
        X = x;
        Y = y;
        Type = type;
        DifficultyLevel = difficultyLevel;
        TrackType = trackType;
        TrackDuration = trackDuration;
        TrackLength = trackLength;
        AdmissionCharge = admissionCharge;
        BestSeason = bestSeason;
        Precautions = precautions;
        ByAppointment = byAppointment;
        Name = name;
        TypeRecreation = typeRecreation;
        City = city;
        NearTo = nearTo;
        OpeningHours = openingHours;
        Parking = parking;
        Phone = phone;
        Region = region;
        SuitableForChildren = suitableForChildren;
        this.URL = URL;
        FullDescription = fullDescription;
        ProductUrl = productUrl;
        PicUrl = picUrl;
        Accessibility = accessibility;
        Address = address;
    }

    protected Tour(Parcel in) {
        Id = in.readInt();
        X = in.readDouble();
        Y = in.readDouble();
        Type = in.readString();
        DifficultyLevel = in.readString();
        TrackType = in.readString();
        TrackDuration = in.readString();
        TrackLength = in.readString();
        AdmissionCharge = in.readString();
        BestSeason = in.readString();
        Precautions = in.readString();
        ByAppointment = in.readString();
        Name = in.readString();
        TypeRecreation = in.readString();
        City = in.readString();
        NearTo = in.readString();
        OpeningHours = in.readString();
        Parking = in.readString();
        Phone = in.readString();
        Region = in.readString();
        SuitableForChildren = in.readString();
        URL = in.readString();
        FullDescription = in.readString();
        ProductUrl = in.readString();
        PicUrl = in.readString();
        Accessibility = in.readString();
        Address = in.readString();
    }

    public static final Creator<Tour> CREATOR = new Creator<Tour>() {
        @Override
        public Tour createFromParcel(Parcel in) {
            return new Tour(in);
        }

        @Override
        public Tour[] newArray(int size) {
            return new Tour[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDifficultyLevel() {
        return DifficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        DifficultyLevel = difficultyLevel;
    }

    public String getTrackType() {
        return TrackType;
    }

    public void setTrackType(String trackType) {
        TrackType = trackType;
    }

    public String getTrackDuration() {
        return TrackDuration;
    }

    public void setTrackDuration(String trackDuration) {
        TrackDuration = trackDuration;
    }

    public String getTrackLength() {
        return TrackLength;
    }

    public void setTrackLength(String trackLength) {
        TrackLength = trackLength;
    }

    public String getAdmissionCharge() {
        return AdmissionCharge;
    }

    public void setAdmissionCharge(String admissionCharge) {
        AdmissionCharge = admissionCharge;
    }

    public String getBestSeason() {
        return BestSeason;
    }

    public void setBestSeason(String bestSeason) {
        BestSeason = bestSeason;
    }

    public String getPrecautions() {
        return Precautions;
    }

    public void setPrecautions(String precautions) {
        Precautions = precautions;
    }

    public String getByAppointment() {
        return ByAppointment;
    }

    public void setByAppointment(String byAppointment) {
        ByAppointment = byAppointment;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTypeRecreation() {
        return TypeRecreation;
    }

    public void setTypeRecreation(String typeRecreation) {
        TypeRecreation = typeRecreation;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getNearTo() {
        return NearTo;
    }

    public void setNearTo(String nearTo) {
        NearTo = nearTo;
    }

    public String getOpeningHours() {
        return OpeningHours;
    }

    public void setOpeningHours(String openingHours) {
        OpeningHours = openingHours;
    }

    public String getParking() {
        return Parking;
    }

    public void setParking(String parking) {
        Parking = parking;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getSuitableForChildren() {
        return SuitableForChildren;
    }

    public void setSuitableForChildren(String suitableForChildren) {
        SuitableForChildren = suitableForChildren;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }


    public String getFullDescription() {
        return FullDescription;
    }

    public void setFullDescription(String fullDescription) {
        FullDescription = fullDescription;
    }

    public String getProductUrl() {
        return ProductUrl;
    }

    public void setProductUrl(String productUrl) {
        ProductUrl = productUrl;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getAccessibility() {
        return Accessibility;
    }

    public void setAccessibility(String accessibility) {
        Accessibility = accessibility;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "Id=" + Id +
                ", X=" + X +
                ", Y=" + Y +
                ", Type='" + Type + '\'' +
                ", DifficultyLevel='" + DifficultyLevel + '\'' +
                ", TrackType='" + TrackType + '\'' +
                ", TrackDuration='" + TrackDuration + '\'' +
                ", TrackLength='" + TrackLength + '\'' +
                ", AdmissionCharge='" + AdmissionCharge + '\'' +
                ", Bestseason='" + BestSeason + '\'' +
                ", Precautions='" + Precautions + '\'' +
                ", ByAppointment='" + ByAppointment + '\'' +
                ", Name='" + Name + '\'' +
                ", TypeRecreation='" + TypeRecreation + '\'' +
                ", City='" + City + '\'' +
                ", NearTo='" + NearTo + '\'' +
                ", OpeningHours='" + OpeningHours + '\'' +
                ", Parking='" + Parking + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Region='" + Region + '\'' +
                ", SuitableForChildren='" + SuitableForChildren + '\'' +
                ", URL='" + URL + '\'' +
                ", FullDescription='" + FullDescription + '\'' +
                ", ProductUrl='" + ProductUrl + '\'' +
                ", PicUrl='" + PicUrl + '\'' +
                ", Accessibility='" + Accessibility + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeDouble(X);
        dest.writeDouble(Y);
        dest.writeString(Type);
        dest.writeString(DifficultyLevel);
        dest.writeString(TrackType);
        dest.writeString(TrackDuration);
        dest.writeString(TrackLength);
        dest.writeString(AdmissionCharge);
        dest.writeString(BestSeason);
        dest.writeString(Precautions);
        dest.writeString(ByAppointment);
        dest.writeString(Name);
        dest.writeString(TypeRecreation);
        dest.writeString(City);
        dest.writeString(NearTo);
        dest.writeString(OpeningHours);
        dest.writeString(Parking);
        dest.writeString(Phone);
        dest.writeString(Region);
        dest.writeString(SuitableForChildren);
        dest.writeString(URL);
        dest.writeString(FullDescription);
        dest.writeString(ProductUrl);
        dest.writeString(PicUrl);
        dest.writeString(Accessibility);
        dest.writeString(Address);
    }
}
