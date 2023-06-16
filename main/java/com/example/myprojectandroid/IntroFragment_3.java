package com.example.myprojectandroid;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
class IntroFragment_3 extends Fragment {
    RadioGroup rgHomeAccessibility;
    String  accessibility = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_fragment_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rgHomeAccessibility = view.findViewById(R.id.rgHomeAccessibility);
        ((TextView)view.findViewById(R.id.tvAccessibility)).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG );

        rgHomeAccessibility.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbAccessibilityYes:
                        accessibility = "כן";
                        break;
                    case R.id.rbAccessibilityNo:
                        accessibility = "לא";
                        break;
                    case R.id.rbAccessibilityNotMatter:
                        accessibility = "";
                        break;

                }
                HomeIntroActivithy.listAll[2]=accessibility;
            }
        });

    }
}
