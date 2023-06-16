package com.example.myprojectandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ToursFilteringDialog {
    Button btnClean;
    Button btnExit;
    Button btnResultsTours;
    RadioGroup rgTrackType;
    CheckBox cbSuitableForChildren,cbTracks,cbParking,cbGuidedTours,cbAttractions, cbAccessibility;
    String trackType;
    String parking;
    String  accessibility;
    String  suitableForChildren;
    StreamDBHelper streamDBHelper;

    @SuppressLint("ResourceType")
        public void showDialog(Activity activity, String msg, Context context, String nameCity , City city ){
            final Dialog dialog = new Dialog(activity,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.activity_tours_filtering);

            List<String> types = new ArrayList<>();
            btnClean = dialog.findViewById(R.id.btnClean);
            btnExit = dialog.findViewById(R.id.btnExit);
            btnResultsTours = dialog.findViewById(R.id.btnResultsTours);

            cbAccessibility = dialog.findViewById(R.id.cbAccessibility);
            cbAttractions = dialog.findViewById(R.id.cbAttractions);
            cbGuidedTours = dialog.findViewById(R.id.cbGuidedTours);
            cbParking = dialog.findViewById(R.id.cbParking);
            cbSuitableForChildren = dialog.findViewById(R.id.cbSuitableForChildren);
            cbTracks = dialog.findViewById(R.id.cbTracks);
            rgTrackType = dialog.findViewById(R.id.rgTrackType);
            List<Tour> list = new ArrayList<>();
            String nameCity1 = nameCity;
            City city1 = city;

            rgTrackType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.rbCar:
                            trackType = "רכב";
                            List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                            list.clear();
                            for (Tour tour : tourList) {
                                list.add(tour);
                            }
                            btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                            break;
                        case R.id.rbWalk:
                            trackType = "הליכה";
                            tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                            list.clear();
                            for (Tour tour : tourList) {
                                list.add(tour);
                            }
                            btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                            break;
                        case R.id.rbBicycle:
                            trackType = "אופניים";
                            tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                            list.clear();
                            for (Tour tour : tourList) {
                                list.add(tour);
                            }
                            btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                            break;

                    }
                }
            });
            cbAccessibility.setOnClickListener((v)->{
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    accessibility = "כן";
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }
                else{
                    accessibility = "";
                    List<Tour> tourList = countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }
            });
            cbAttractions.setOnClickListener((v)->{
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    types.add("אטרקציות");
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }else {
                    for (int i = 0; i < types.size(); i++) {
                        if (types.get(i).equals("אטרקציות")){types.remove(i);}
                    }
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }
            });
            cbGuidedTours.setOnClickListener((v)->{
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    types.add("סיורים מאורגנים");
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }else {
                    for (int i = 0; i < types.size(); i++) {
                        if (types.get(i).equals("סיורים מאורגנים")){types.remove(i);}
                    }
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }
            });

            cbParking.setOnClickListener((v)->{
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    parking = "כן";
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }else{
                    parking = "";
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }
            });
            cbSuitableForChildren.setOnClickListener((v)->{
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    suitableForChildren = "כן";
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }else {
                    suitableForChildren = "";
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }
            });
            cbTracks.setOnClickListener((v)->{
                boolean checked = ((CheckBox) v).isChecked();
                if (checked){
                    types.add("מסלולים");
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }else {
                    for (int i = 0; i < types.size(); i++) {
                        if (types.get(i).equals("מסלולים")){types.remove(i);}
                    }
                    List<Tour> tourList =countResultsTours(context,nameCity,types, trackType, parking, accessibility, suitableForChildren);
                    list.clear();
                    for (Tour tour : tourList) {
                        list.add(tour);
                    }
                    btnResultsTours.setText(" תוצאות" + tourList.size() +"הצג ");
                }
            });

            btnClean.setOnClickListener((v -> {
                cbSuitableForChildren.setChecked(false);
                cbTracks.setChecked(false);
                cbParking.setChecked(false);
                cbGuidedTours.setChecked(false);
                cbAttractions.setChecked(false);
                cbAccessibility.setChecked(false);
                rgTrackType.clearCheck();
                btnResultsTours.setText("הצג x תוצאות");

            }));
            btnExit.setOnClickListener((v -> {
                    dialog.dismiss();

            }));


        btnResultsTours.setOnClickListener((v)->{
                if (list.size() != 0){
                    RecyclerView recyclerView = activity.findViewById(R.id.rvTour);
                    streamDBHelper = new StreamDBHelper(context);
                    streamDBHelper.cleanAllData();
                    TourAdapter toursAdapter = new TourAdapter(list,context);
                    recyclerView.setAdapter(toursAdapter);
                    dialog.dismiss();
                }
        });
            dialog.show();
        }


    public List<Tour> countResultsTours(Context context,String nameCity, List<String> types, String trackType, String parking, String  accessibility, String  suitableForChildren){
        TourDatabaseHelper db = new TourDatabaseHelper(context);
        List<Tour> tourList = db.getToursByType(nameCity,types, trackType, parking, accessibility, suitableForChildren);
        return tourList;
    }

}
