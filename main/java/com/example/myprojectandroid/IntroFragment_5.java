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
class IntroFragment_5 extends Fragment {
    CheckBox cbHomeTours;
    CheckBox cbHomeAttractions;
    CheckBox cbHomeTracks;
    List<String> types = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_fragment_5, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cbHomeTours = view.findViewById(R.id.cbHomeTours);
        cbHomeAttractions = view.findViewById(R.id.cbHomeAttractions);
        cbHomeTracks = view.findViewById(R.id.cbHomeTracks);
        ((TextView)view.findViewById(R.id.tvTypeTour)).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG );
        cbHomeTours.setOnClickListener((v)->{
            boolean checked = ((CheckBox) v).isChecked();
            if (checked){
                types.add("סיורים");
            }
            else{
                for (int i = 0; i < types.size(); i++) {
                    if (types.get(i).equals("סיורים")){types.remove(i);}
                }
            }
            HomeIntroActivithy.listAll[4] = String.valueOf(types);
        });
        cbHomeAttractions.setOnClickListener((v)->{
            boolean checked = ((CheckBox) v).isChecked();
            if (checked){
                types.add("אטרקציות");
            }
            else{
                for (int i = 0; i < types.size(); i++) {
                    if (types.get(i).equals("אטרקציות")){types.remove(i);}
                }
            }
            HomeIntroActivithy.listAll[4] = String.valueOf(types);
        });
        cbHomeTracks.setOnClickListener((v)->{
            boolean checked = ((CheckBox) v).isChecked();
            if (checked){
                types.add("מסלולים");
            }
            else{
                for (int i = 0; i < types.size(); i++) {
                    if (types.get(i).equals("מסלולים")){types.remove(i);}
                }
            }
            HomeIntroActivithy.listAll[4] = String.valueOf(types);
        });


    }
}
