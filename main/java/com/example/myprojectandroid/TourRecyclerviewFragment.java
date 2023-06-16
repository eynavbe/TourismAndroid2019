package com.example.myprojectandroid;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
class TourRecyclerviewFragment extends Fragment{
    public static final String EXTRA_CITY = "city";
    public static final String EXTRA_WAS_MAP = "false";
    public static final String EXTRA_LIST_TOUR = "tour_list";
    List<Tour> listT;
    Boolean wasMap = false;
    TextView tvNameCity;
    TextView tvInformation;
    TextView tvWeather,tvInf;
    ImageView ivIconWeather;
    View vColor;
    Button btnSort;
    Button btnFilter;
//    Button btnLocation;
    static RecyclerView rvTour;
    TourAdapter toursAdapter;
    List<String> suggestList = new ArrayList<>();
    TourDatabaseHelper tourDatabaseHelper;
    ToursDataSourceJson toursDataSourceJson;
    CityWeatherSourceJson cityWeatherSourceJson;
    ImageView image;
//    static List<TourMyLocationDistance> tourMyLocationDistances = new ArrayList<>();
    String nameCity;
    static  Context context;
    static City city = null;
    StreamDBHelper streamDBHelper;

    VideoDatabase database;
    List<Video> videos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_recyclerview, container, false);
    }
    @SuppressLint("WrongConstant")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNameCity = view.findViewById(R.id.tvNameCity);
        tvInformation = view.findViewById(R.id.tvInformation);
        tvWeather = view.findViewById(R.id.tvWeather);
        image = view.findViewById(R.id.image);
        ivIconWeather = view.findViewById(R.id.ivIconWeather);
        tvInf = view.findViewById(R.id.textView4);
//        vColor = view.findViewById(R.id.vColor);
        btnSort = view.findViewById(R.id.btnSort);
        btnFilter = view.findViewById(R.id.btnFilter);
//        btnLocation = view.findViewById(R.id.btnLocation);
        rvTour = view.findViewById(R.id.rvTour);
        rvTour.setHasFixedSize(true);
        context = getContext();
        tourDatabaseHelper = new TourDatabaseHelper(getContext());
        cityWeatherSourceJson = new CityWeatherSourceJson();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            city = bundle.getParcelable(EXTRA_CITY);
            wasMap = bundle.getBoolean(EXTRA_WAS_MAP);
            if (city == null){
                listT = bundle.getParcelableArrayList(EXTRA_LIST_TOUR);
                tvNameCity.setVisibility(View.GONE);
                tvInformation.setText("האטרקציות שנמצאו לפי הנתונים שרשמתם בחיפוש: ");
                tvInf.setVisibility(View.GONE);
                btnFilter.setVisibility(View.GONE);
                image.getLayoutParams().height = 100;
                TourAdapter.ifNameCity = true;
            }
            else {
                TourAdapter.ifNameCity = false;
                btnFilter.setVisibility(View.VISIBLE);
                tvNameCity.setVisibility(View.VISIBLE);
                tvInformation.setVisibility(View.VISIBLE);
                tvInf.setVisibility(View.VISIBLE);
                tvNameCity.setText(city.getName());
                String string = city.getFullDescription();
                Document doc = Jsoup.parse(string);
                Elements element = doc.getElementsByTag("p");
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < element.size(); i++) {
                    Element e = element.get(i);
                    if ((i == element.size() - 1) || (i == element.size() - 2) || (i == element.size() - 3)) {
                        continue;
                    }
                    stringBuilder.append(e.text() + "\n");
                    tvInformation.setText(stringBuilder.toString());
                }
            }

        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager rvLiLinayoutManager = layoutManager;
        rvTour.setLayoutManager(rvLiLinayoutManager);


        //דיאלוג שמציע סרטונים קשורים
        if ((!wasMap) && (listT == null)){

            if (city != null){
                database = new VideoDatabase(getContext());
                videos = database.getVideosByCity(city.getName());
                if (videos != null){
                    showDialogAnimation(videos);
                }
            }

        }




        City finalCity = city;
        btnFilter.setOnClickListener((v)->{
            ToursFilteringDialog alert = new ToursFilteringDialog();
            alert.showDialog(getActivity(), "Error",getContext(),nameCity,finalCity);
        });

//        btnLocation.setOnClickListener((v)->{
//            Fragment fragment = new ToursMapFragment();
//            AppCompatActivity activity = (AppCompatActivity) getActivity();
//            List<Tour> list = findTours(finalCity);
//
//            Bundle bundle1 = new Bundle();
//            bundle1.putParcelableArrayList(ToursMapFragment.EXTRA_LIST_TOURS, (ArrayList<? extends Parcelable>) list);
//            bundle1.putParcelable(ToursMapFragment.EXTRA_CITY,finalCity);
//            fragment.setArguments(bundle1);
//
//            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
//
//        });
        btnSort.setOnClickListener((v) -> {
            TourSortBottomSheet tourSortBottomSheet = new TourSortBottomSheet();
            tourSortBottomSheet.show(getChildFragmentManager(),"tourSortBottomSheet");

        });

        Thread thread = new Thread(() -> {
            try  {
                findWeather(city,context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        List<Tour> list = new ArrayList<>();
//        if ((wasMap == null) || (!wasMap)){
        if (listT == null)
        {
            if (city != null){
                list = findTours(city);
            }

        }else {
            list = listT;
        }

//        }
//        else {
        if (wasMap){

            list = new ArrayList<>();
            StreamDBHelper streamDBHelper = new StreamDBHelper(context);
            List<TourMyLocationDistance> tourMyLocationDistancesList = streamDBHelper.getListTourMyLocationDistances();
            for (TourMyLocationDistance tourMyLocationDistance : tourMyLocationDistancesList) {
                list.add(tourMyLocationDistance.getTour());
            }
        }

        if (list.size() != 0){
            setrvTour(list);
        }
    }

    public void sortDistance(boolean test){
        List<Tour> tourList = new ArrayList<>();
        StreamDBHelper streamDBHelper = new StreamDBHelper(context);
        List<TourMyLocationDistance> tourMyLocationDistancesList = streamDBHelper.getListTourMyLocationDistances();
        for (TourMyLocationDistance tourMyLocationDistance : tourMyLocationDistancesList) {
            tourList.add(tourMyLocationDistance.getTour());
        }
        streamDBHelper.cleanAllData();
        FusedLocationProviderClient clinet = null;
        String location = "0";
        if (test){
            location = Manifest.permission.ACCESS_FINE_LOCATION;
            ActivityCompat.requestPermissions(((Activity) context),new String[]{location},0);
        }
//        List<Tour> tourList = streamDBHelperTour.getToursByCity(city.getName());
        List<TourMyLocationDistance> tourMyLocationDistances = new ArrayList<>();
        if ((ContextCompat.checkSelfPermission(context, location) != PackageManager.PERMISSION_GRANTED) || (!test)){
            for (Tour tour : tourList) {
                Location locationTour =new Location("locationTour");
                locationTour.setLatitude(tour.getY());
                locationTour.setLongitude(tour.getX());
                ActivityCompat.requestPermissions(((Activity) context),new String[]{location},0);
                String distanceString;
                Location locationCity = new Location("locationCity");
                locationCity.setLatitude(city.getY());
                locationCity.setLongitude(city.getX());
                distanceString = distance(locationTour,locationCity,tour);
                TourMyLocationDistance tourMyLocationDistance = new TourMyLocationDistance(tour,distanceString);
                tourMyLocationDistances.add(tourMyLocationDistance);
            }
            Collections.sort(tourMyLocationDistances, TourMyLocationDistance.BY_DISTANCE);
            List<Tour> tourList1 = new ArrayList<>();
            for (TourMyLocationDistance tourMyLocationDistance : tourMyLocationDistances) {
                tourList1.add(tourMyLocationDistance.getTour());
            }
            TourAdapter toursAdapter = new TourAdapter(tourList1,context);
            rvTour.setAdapter(toursAdapter);
        }
        else {
            location = Manifest.permission.ACCESS_FINE_LOCATION;
            ActivityCompat.requestPermissions(((Activity) context),new String[]{location},0);
            clinet = LocationServices.getFusedLocationProviderClient(context);
            clinet.getLastLocation().addOnSuccessListener(((Activity) context), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location myLocation) {
                    if (myLocation != null) {
                        List<TourMyLocationDistance> tourMyLocationDistances = new ArrayList<>();
                        for (Tour tour : tourList) {
                            Location locationTour =new Location("locationTour");
                            locationTour.setLatitude(tour.getY());
                            locationTour.setLongitude(tour.getX());
                            String distanceString = distance(myLocation, locationTour, tour);
                            TourMyLocationDistance tourMyLocationDistance = new TourMyLocationDistance(tour,distanceString);
                            tourMyLocationDistances.add(tourMyLocationDistance);
                        }
                        Collections.sort(tourMyLocationDistances, TourMyLocationDistance.BY_DISTANCE);
                        List<Tour> tourList1 = new ArrayList<>();
                        for (TourMyLocationDistance tourMyLocationDistance : tourMyLocationDistances) {
                            tourList1.add(tourMyLocationDistance.getTour());
                        }
                        TourAdapter toursAdapter = new TourAdapter(tourList1,context);
                        rvTour.setAdapter(toursAdapter);
                    }
                }
            });
        }
        Collections.sort(tourMyLocationDistances, TourMyLocationDistance.BY_DISTANCE);
    }
    private String distance(Location locationStart,Location locationEnd , Tour tour){
        double distance = locationStart.distanceTo(locationEnd) /1000;
        String distanceString = String.valueOf(distance);
        int distanceindexOfPoint = distanceString.indexOf(".");
        if (distanceString.length() >= 4){
            if (distanceString.length()>= (distanceindexOfPoint+ 4)){
                distanceString = distanceString.substring(0,distanceindexOfPoint+3);
            }
        }
        return distanceString;
    }
    private void findWeather(City city,Context context) {
        String api = null;


        List<WeatherJson> weatherJsons = cityWeatherSourceJson.doInBackground();
        for (WeatherJson weatherJson : weatherJsons) {
            if ((weatherJson.getName().equals(city.getName())) || (weatherJson.getName().equals(city.getRegion()))){
                api = weatherJson.getApi();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, api, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject current = response.getJSONObject("current");
                            String temp = String.valueOf((int) current.getDouble("temp_c"));
                            JSONObject condition = current.getJSONObject("condition");
                            String icon = condition.getString("icon");
                            tvWeather.setText("מזג האוויר: " + temp+"c");
                            Picasso.get().load("https:"+icon).into(ivIconWeather);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(jsonObjectRequest);
            }
        }
    }

    private List<Tour> findTours(City city) {
        TourDatabaseHelper db = new TourDatabaseHelper(getContext());
        if (db.getToursCount() == 0){
            toursDataSourceJson = new ToursDataSourceJson();
            List<Tour> tourListJson = toursDataSourceJson.doInBackground();
            for (Tour tour : tourListJson) {
                db.addTour(tour);
            }

        }
        List<Tour> tourList = db.getToursByCity(city.getName());
        nameCity = city.getName();
        if (db.getToursByCity(city.getName()).size()  == 0){
            if (city.getVendorName().equals("ישוב")){
                if (city.getRegion().equals("ירדן )יריחו(")){
                    tourList = db.getToursByCity("יריחו");
                    nameCity = "יריחו";
                    return tourList;
                }
                tourList = db.getToursByCity(city.getRegion());
                nameCity = city.getRegion();
                return tourList;
            }
            else {
                String region = city.getRegion();
                switch (region){
                    case "ירושלים והסביבה":
                        tourList = db.getToursByCity("ירושלים");
                        nameCity = "ירושלים";
                        break;
                    case "תל אביב והמרכז":
                        tourList = db.getToursByCity("תל אביב");
                        nameCity = "תל אביב";
                        break;
                }
            }

        }
        return tourList;

    }
    public void setrvTour(List<Tour> tourList) {
        streamDBHelper = new StreamDBHelper(context);
        streamDBHelper.cleanAllData();
        toursAdapter = new TourAdapter(tourList,getContext());
        rvTour.setAdapter(toursAdapter);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    private void showDialogAnimation(List<Video> videos){
        String []videosName = new String[videos.size()];
        for (int i = 0; i < videos.size(); i++) {
            videosName[i] = videos.get(i).getTitle();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("סרטונים שעשויים לעניין אותך: ")
                .setItems(videosName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        YouTubeFragment youTubeFragment = YouTubeFragment.newInstance(videos.get(which).getId(), 0);
                        AppCompatActivity activity = (AppCompatActivity) getContext();
                        activity.getFragmentManager().beginTransaction().replace(R.id.frame,youTubeFragment).addToBackStack(null).commit();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlide;
        dialog.show();
        if (videos.size() > 4){
            dialog.getWindow().setLayout(900, 900);
        }
    }
}
