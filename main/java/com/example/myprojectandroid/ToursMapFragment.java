package com.example.myprojectandroid;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToursMapFragment extends Fragment implements OnMapReadyCallback, RadioGroup.OnCheckedChangeListener {
    GoogleMap mMap;
    MapView mMapView;
    View mView;
    RadioGroup rgMapType;
    Button btnLegend;
    View vLegend;
    ImageView ivIconTrack, ivIconGuidedTours, ivIconAttraction;
    Button btnCloseLegend1;
    List<Tour> tourArrayList = new ArrayList<>();
    City city;
    FusedLocationProviderClient apiClient;
    final static String EXTRA_LIST_TOURS = "toursList";
    final static String EXTRA_CITY = "city";
    public ToursMapFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tours_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.map);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        rgMapType = view.findViewById(R.id.rgMapType);
        rgMapType.setOnCheckedChangeListener(this);
        btnLegend = view.findViewById(R.id.btnLegend);
        vLegend = view.findViewById(R.id.vLegend1);
        ivIconTrack = view.findViewById(R.id.ivIconTrack);
        ivIconGuidedTours = view.findViewById(R.id.ivIconGuidedTours);
        ivIconAttraction = view.findViewById(R.id.ivIconAttraction);
        btnCloseLegend1 = view.findViewById(R.id.btnCloseLegend1);


        btnLegend.setOnClickListener((v)->{
            vLegend.setVisibility(View.VISIBLE);
            btnLegend.setVisibility(View.GONE);
        });
        btnCloseLegend1.setOnClickListener((v)->{
            vLegend.setVisibility(View.GONE);
            btnLegend.setVisibility(View.VISIBLE);
        });
        apiClient = new FusedLocationProviderClient(getContext());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tourArrayList = bundle.getParcelableArrayList(EXTRA_LIST_TOURS);

            if ( (tourArrayList == null)){
                city = bundle.getParcelable(EXTRA_CITY);
            }
        }
        System.out.println(tourArrayList);
        if (tourArrayList.size() == 0){
            tourArrayList = new ArrayList<>();
            StreamDBHelper streamDBHelper = new StreamDBHelper(getContext());
            System.out.println(streamDBHelper.getTourMyLocationDistanceCount());
            List<TourMyLocationDistance> tourMyLocationDistances = streamDBHelper.getListTourMyLocationDistances();
            for (int i = 0; i < tourMyLocationDistances.size(); i++) {
                tourArrayList.add(tourMyLocationDistances.get(i).getTour());
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if ((tourArrayList.size() != 0)){
            for (Tour tour : tourArrayList) {
                LatLng tourLocation = new LatLng(tour.getY(),tour.getX());
                switch (tour.getType()){
                    case "אטרקציות":
                        mMap.addMarker(new MarkerOptions().position(tourLocation).title(tour.getName()).icon(getMarkerIcon("#e81431")));
                        break;
                    case "מסלולים":
                        mMap.addMarker(new MarkerOptions().position(tourLocation).title(tour.getName()).icon(getMarkerIcon("#1cbc51")));
                        break;
                    case "סיורים מאורגנים":
                        mMap.addMarker(new MarkerOptions().position(tourLocation).title(tour.getName()).icon(getMarkerIcon("#0952e5")));
                        break;
                }
            }
            LatLng cityLocation;
            if (city != null){
                cityLocation = new LatLng(city.getY(),city.getX());
            }
            else {
                cityLocation = new LatLng(tourArrayList.get(0).getY(),tourArrayList.get(0).getX());
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation,15));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(marker.getTitle());
                    String address = findAddress(marker.getPosition());
                    Tour tourInfo = null;
                    for (Tour tour : tourArrayList) {
                        if (tour.getName().equals(marker.getTitle())){
                            builder.setMessage(tour.getType() +"\n"+ tour.getTrackType() +"\n"+ address +"\n"+ tour.getTypeRecreation());
                            tourInfo = tour;
                        }
                    }
                    Tour finalTourInfo = tourInfo;
                    builder.setPositiveButton("יותר מידע", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Bundle bundle = new Bundle();
                            if (finalTourInfo != null){
                                bundle.putParcelable(TourInfo.EXTRA_TOUR, finalTourInfo);
                            }
                            Fragment fragment = new TourInfo();
                            fragment.setArguments(bundle);
                            AppCompatActivity activity = (AppCompatActivity) getContext();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
                        }
                    });
                    builder.setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return false;
                }
            });
        }

        showLocation();
    }

    private BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    final static int RC_LOCASION = 0;
    private void showLocation() {
        String location = Manifest.permission.ACCESS_FINE_LOCATION;

        if (ContextCompat.checkSelfPermission(getContext(), location) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{location},RC_LOCASION);
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    private String findAddress(LatLng l){

        try {
            Geocoder geo = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(l.latitude,l.longitude,1);
            if (addresses.isEmpty()) {
                System.out.println("Waiting for Location");
            }
            else {
                if (addresses.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append((addresses.get(0).getAddressLine(0) ));
                    return (stringBuilder.toString());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
        return null;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode == RC_LOCASION) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            showLocation();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rbStandart:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.rbSatalite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.rbHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }
    }
}
