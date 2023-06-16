package com.example.myprojectandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class HomeIntroActivithy extends AppIntro {
    private PreferenceHelper preferenceHelper;
    static Location myLocationMap = null;
    FusedLocationProviderClient clinet;
    static String[] listAll = new String[7];
    static ArrayList<Tour> tourList;

    //    boolean done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        listAll = new String[7];
        preferenceHelper = new PreferenceHelper(this);

        if (preferenceHelper.getIntro().equals("no")) {
            Intent intent = new Intent(HomeIntroActivithy.this, WelcomeActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            this.finish();
            onBackPressed();
        }

        addSlide(new IntroFragment_1());  //extend AppIntro and comment setContentView
        addSlide(new IntroFragment_2());
        addSlide(new IntroFragment_3());
        addSlide(new IntroFragment_4());
        addSlide(new IntroFragment_5());
        addSlide(new IntroFragment_6());
        addSlide(new IntroFragment_7());


        int w = Float.valueOf(getResources().getDisplayMetrics().density).intValue() * 300;
        int h = Float.valueOf(getResources().getDisplayMetrics().density).intValue() * 300;

        getWindow().setLayout(w, h);

        getWindow().getDecorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Einav", "onClick: ");
            }
        });
//לבקש מקום נוכחי כדי שיתאים את עצמו לזמן הנסיעה ליעד לבין הזמן הפנוי
        String location = "0";


        location = Manifest.permission.ACCESS_FINE_LOCATION;
        ActivityCompat.requestPermissions(((Activity) HomeIntroActivithy.this), new String[]{location}, 0);
        clinet = LocationServices.getFusedLocationProviderClient(HomeIntroActivithy.this);
//        Location locationTour =new Location("locationTour");
//        locationTour.setLatitude(tour.getY());
//        locationTour.setLongitude(tour.getX());
        if ((ContextCompat.checkSelfPermission(HomeIntroActivithy.this, location) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(((Activity) HomeIntroActivithy.this), new String[]{location}, 0);
//            String nameCity = tour.getCity();
//            System.out.println("nameCity "+nameCity);
//            String distanceString;
//            if (nameCity != null) {
//                if (nameCity.equals("תל אביב יפו")){
//                    nameCity = "תל אביב";
//                }
//                System.out.println(nameCity + "nameCity");
//
//                CitiesDatabase citiesDatabase = new CitiesDatabase(context);
//                List<City> cities = citiesDatabase.getCitiesByName(nameCity);
//                Location locationCity = new Location("locationCity");
//                System.out.println(cities + "cities");
//                for (int i = 0; i < cities.size(); i++) {
//                    if (cities.get(i).getX() > 0) {
//                        System.out.println("cities.get(i).getY() " +cities.get(i).getY());
//                        locationCity.setLatitude(cities.get(i).getY());
//                        System.out.println(cities.get(i).getX()+"cities.get(i).getX()");
//                        locationCity.setLongitude(cities.get(i).getX());
//
//                    }
//                }
//                distanceString = distance(locationTour,locationCity,tour);
//                holder.tvDistance.setText("מרחק ממרכז העיר " + distanceString + " ק״מ ");
//            }
        } else {
            clinet.getLastLocation().addOnSuccessListener(((Activity) HomeIntroActivithy.this), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location myLocation) {
                    if (myLocation != null) {
                        myLocationMap = myLocation;
//                        String distanceString = distance(myLocation,locationTour,tour);
//                        holder.tvDistance.setText("מרחק ממקומך הנוכחי " + distanceString + " ק״מ ");
//                        System.out.println(test);
//                        String origin = "origin="+ myLocation.getLatitude()+","+myLocation.getLongitude();
//                        String dest = "destination="+ myLocation.getLatitude()+","+myLocation.getLongitude();
//                        String mode = "mode=driving";
//                        String parameters = origin + "&" + dest +"&" +mode;
//                        String url = "https://maps.googleapis.com/maps/api/directions/json?"+parameters+"&key=AIzaSyCQDu85xwg8XUUUzVKd9ZJ6iYnHa_amObA";
//                        System.out.println(url);
//                        try{
//                            OkHttpClient client = new OkHttpClient();
//                            okhttp3.Request request = new Request.Builder().url(url).build();
//                            Call call = client.newCall(request);
//                            try (Response response = call.execute()) {
//                                if (response.body() == null) System.out.println(" fff");
//                                String json = response.body().string();
//                                JSONObject jsonObject = new JSONObject(json);
//                                 JSONArray routesArray = jsonObject.getJSONArray("routes");
//                                JSONObject routesObject = routesArray.getJSONObject(0);
//                                JSONArray legsArray = routesObject.getJSONArray("legs");
//                                JSONObject legsObject = legsArray.getJSONObject(0);
//                                JSONObject durationObject = legsObject.getJSONObject("duration");
//                                String timeTo = durationObject.getString("text");
//                                System.out.println("timeTo "+timeTo);
//                            }catch (JSONException e) {
//                                System.out.println("error JSONException");
//                            }
//
//                        }catch (IOException e){
//                            System.out.println("error IOException");
//                        }
                    }

                }
            });
        }

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        preferenceHelper.putIntro("no");
//        Intent intent = new Intent(HomeIntroActivithy.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        this.finish();
//        onBackPressed();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (done){
//
//            AppCompatActivity activity = (AppCompatActivity)HomeIntroActivithy.this;
//            Bundle bundle2 = new Bundle();
////                    bundle2.putParcelable(TourRecyclerviewTabLayoutFragment.EXTRA_CITY, "");
//            bundle2.putParcelableArrayList(TourRecyclerviewTabLayoutFragment.EXTRA_LIST_TOUR,(ArrayList<? extends Parcelable>) tourList);
//            Fragment fragment2 = new TourRecyclerviewTabLayoutFragment();
//            fragment2.setArguments(bundle2);
//
//
//            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment2).addToBackStack(null).commit();
//
//        }

//    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        preferenceHelper.putIntro("no");
//
        if (listAll[0] == null) {
            listAll[0] = String.valueOf(6);
        }
////get all tours
//        ToursDataSourceJson toursDataSourceJson;
//        TourDatabaseHelper db = new TourDatabaseHelper(HomeIntroActivithy.this);
//        if (db.getToursCount() == 0){
//            toursDataSourceJson = new ToursDataSourceJson();
//            List<Tour> tourListJson = toursDataSourceJson.doInBackground();
//            for (Tour tour : tourListJson) {
//                db.addTour(tour);
//            }
//
//        }
//        if (myLocationMap != null){
//            listAll[7] = String.valueOf(myLocationMap.getLongitude());
//            listAll[8] = String.valueOf(myLocationMap.getLatitude());
//            List<String> regions = new ArrayList<>();
//            if (listAll[6] != null){
//                regions = Arrays.asList(listAll[6].split(","));
//                regions.set(0,regions.get(0).replace("[",""));
//                regions.set(regions.size()-1,regions.get(regions.size()-1).replace("]",""));
//                if (regions.size() > 1) {
//                    for (int i = 1; i < regions.size(); i++) {
//                        regions.set(i, regions.get(i).replace(" ", ""));
//                    }
//                }
//                for (String region : regions) {
//                    System.out.println(region);
//                }
//            }
//
//            List<String> types = new ArrayList<>();
//            if (listAll[4] != null){
//                types = Arrays.asList(listAll[4].split(","));
//                types.set(0,types.get(0).replace("[",""));
//                types.set(types.size()-1,regions.get(types.size()-1).replace("]",""));
//                if (types.size() > 1){
//                    for (int i = 1; i < types.size(); i++) {
//                        types.set(i,types.get(i).replace(" ",""));
//                    }
//                }
//
//                for (String type : types) {
//                    System.out.println(type);
//                }
//            }
//
//
//            String trackType = listAll[1];
//            String accessibility = listAll[2];
//            String suitableForChildren = listAll[3];
//            int time = 6;
//            if (listAll[0] != null) {
//                time = Integer.parseInt(listAll[0]);
//            }
//            boolean weather = Boolean.parseBoolean(listAll[5]);
//            tourList = db.getToursByTypeHome(myLocationMap, regions,types,trackType,accessibility,suitableForChildren,time,weather);
////                for (Tour tour : tourList) {
////                    System.out.println(tour);
//
////
//    }
//            }

    //        Intent intent = new Intent(HomeIntroActivithy.this, MainActivity.class);
//        startActivity(intent);
//        done =true;
    Intent intent = new Intent(HomeIntroActivithy.this, MainActivity.class);

    startActivity(intent);
//        this.finish();

    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

//    @Override  // this method is used for removing top and bottom navbars(fullscreen)
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//
//    }

}
