package com.example.myprojectandroid;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
class IntroFragment_7 extends Fragment {
    CheckBox cbHomeRegionNorth;
    CheckBox cbHomeRegionCenter;
    CheckBox cbHomeRegionSouth;
    List<String> regions = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_fragment_7, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cbHomeRegionNorth = view.findViewById(R.id.cbHomeRegionNorth);
        cbHomeRegionCenter = view.findViewById(R.id.cbHomeRegionCenter);
        cbHomeRegionSouth = view.findViewById(R.id.cbHomeRegionSouth);
        ((TextView)view.findViewById(R.id.tvHomeRegion)).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG );
        cbHomeRegionNorth.setOnClickListener((v)->{
            boolean checked = ((CheckBox) v).isChecked();
            if (checked){
                regions.add("צפון");
            }
            else{
                for (int i = 0; i < regions.size(); i++) {
                    if (regions.get(i).equals("צפון")){regions.remove(i);}
                }
            }
            HomeIntroActivithy.listAll[6] = String.valueOf(regions);
        });
        cbHomeRegionCenter.setOnClickListener((v)->{
            boolean checked = ((CheckBox) v).isChecked();
            if (checked){
                regions.add("מרכז");
            }
            else{
                for (int i = 0; i < regions.size(); i++) {
                    if (regions.get(i).equals("מרכז")){regions.remove(i);}
                }
            }
            HomeIntroActivithy.listAll[6] = String.valueOf(regions);
        });
        cbHomeRegionSouth.setOnClickListener((v)->{
            boolean checked = ((CheckBox) v).isChecked();
            if (checked){
                regions.add("דרום");
            }
            else{
                for (int i = 0; i < regions.size(); i++) {
                    if (regions.get(i).equals("דרום")){regions.remove(i);}
                }
            }
            HomeIntroActivithy.listAll[6] = String.valueOf(regions);
        });

    }
}
