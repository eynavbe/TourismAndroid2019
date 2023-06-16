package com.example.myprojectandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class TourAdapter extends RecyclerView.Adapter <TourAdapter.TourAdapterHolder> {
    List<Tour> tours;
    Context context;
    FusedLocationProviderClient clinet;
    static boolean test;
    StreamDBHelper streamDBHelper;
    static Boolean ifNameCity;
    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";
    public TourAdapter(List<Tour> tours, Context context) {
        this.tours=tours;
        this.context=context;
    }

    @NonNull
    @Override
    public TourAdapter.TourAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.tour_card_view,parent,false);
        return new TourAdapter.TourAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TourAdapter.TourAdapterHolder holder, int position) {
        Tour tour = tours.get(position);
        holder.tvNameTour.setText(tour.getName());
        holder.tvTourInformation.setText(tour.getType());

        Picasso.get().load(imgFolder+tour.getPicUrl()).placeholder(R.drawable.ic_autorenew_black_24dp).
                error(R.drawable.ic_block_black_24dp).into(holder.ivTour);
        String location = "0";
        holder.tour = tour;
        if (ifNameCity){
            holder.tvNameCityTour.setText(tour.getCity());
        }
        if (test){
            location = Manifest.permission.ACCESS_FINE_LOCATION;
            ActivityCompat.requestPermissions(((Activity) context),new String[]{location},0);
            clinet = LocationServices.getFusedLocationProviderClient(context);
        }

        Location locationTour =new Location("locationTour");
        locationTour.setLatitude(tour.getY());
        locationTour.setLongitude(tour.getX());
        streamDBHelper = new StreamDBHelper(context);
        if ((ContextCompat.checkSelfPermission(context, location) != PackageManager.PERMISSION_GRANTED) || (!test)){
            ActivityCompat.requestPermissions(((Activity) context),new String[]{location},0);
            String nameCity = tour.getCity();
            String distanceString;
            if (nameCity != null) {
                if (nameCity.equals("תל אביב יפו")){
                    nameCity = "תל אביב";
                }

                CitiesDatabase citiesDatabase = new CitiesDatabase(context);
                List<City> cities = citiesDatabase.getCitiesByName(nameCity);
                Location locationCity = new Location("locationCity");
                for (int i = 0; i < cities.size(); i++) {
                    if (cities.get(i).getX() > 0) {
                        locationCity.setLatitude(cities.get(i).getY());
                        locationCity.setLongitude(cities.get(i).getX());
                    }
                }
                distanceString = distance(locationTour,locationCity,tour);
                holder.tvDistance.setText("מרחק ממרכז העיר " + distanceString + " ק״מ ");
            }
        }
        else {
            clinet.getLastLocation().addOnSuccessListener(((Activity) context), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location myLocation) {
                    if (myLocation != null){
                        String distanceString = distance(myLocation,locationTour,tour);
                        holder.tvDistance.setText("מרחק ממקומך הנוכחי " + distanceString + " ק״מ ");
                        System.out.println(test);

                    }

                }
            });
        }
    }
    private String distance(Location locationStart,Location locationEnd , Tour tour){
        double distance = locationStart.distanceTo(locationEnd) /1000;
        String distanceString = String.valueOf(distance);
        int distanceindexOfPoint = distanceString.indexOf(".");
        if (distanceString.length() >= 4){
            distanceString = distanceString.substring(0,distanceindexOfPoint+3);
        }
        TourMyLocationDistance tourMyLocationDistance = new TourMyLocationDistance(tour,distanceString);
        streamDBHelper.add(tourMyLocationDistance);
        return distanceString;
    }
    public void setTest(boolean test3){
        test = test3;
    }
    @Override
    public int getItemCount() {
        return tours.size();
    }


    public class TourAdapterHolder extends RecyclerView.ViewHolder {
        TextView tvNameTour;
        TextView tvDistance;
        TextView tvTourInformation;
        TextView tvNameCityTour;
        ImageView ivTour;
        Tour tour;
        CheckBox cbFavourite;

        public TourAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvNameTour = itemView.findViewById(R.id.tvNameTour);
            tvTourInformation = itemView.findViewById(R.id.tvTourInformation);
            ivTour = itemView.findViewById(R.id.ivTour);
            cbFavourite = itemView.findViewById(R.id.cbFavourite);
            tvNameCityTour = itemView.findViewById(R.id.tvNameCityTour);
            itemView.setOnClickListener((v) -> {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(TourInfo.EXTRA_TOUR, tour);
                    Fragment fragment = new TourInfo();
                    fragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
            });

            //להוסיף למועדפים
            cbFavourite.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (FirebaseAuth.getInstance().getCurrentUser() == null){
                    new AlertDialog.Builder(context).setTitle("להוסיף למועדפים").setMessage("נא להירשם לפני").
                            setPositiveButton("בסדר", (dialog, which) -> { }).show();
                    buttonView.setChecked(false);
                    return;
                }
                DatabaseReference dbFavourite = FirebaseDatabase.getInstance().getReference("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("favourite")
                        .child("tour");

                int position = getAdapterPosition();
                tour = tours.get(position);
                if (isChecked){
                    dbFavourite.child(String.valueOf(tour.getId())).setValue(tour);
                }else {
                    dbFavourite.child(String.valueOf(tour.getId())).setValue(null);
                }
            });
        }

    }
}


