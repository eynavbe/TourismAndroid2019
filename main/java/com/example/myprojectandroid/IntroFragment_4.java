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
class IntroFragment_4 extends Fragment {
    RadioGroup rgHomeTripWithChildren;
    String  suitableForChildren = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_fragment_4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rgHomeTripWithChildren = view.findViewById(R.id.rgHomeTripWithChildren);
        ((TextView)view.findViewById(R.id.tvTripWithChildren)).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG );
        rgHomeTripWithChildren.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbTripWithChildrenYes:
                        suitableForChildren = "כן";
                        break;
                    case R.id.rbTripWithChildrenNo:
                        suitableForChildren = "לא";
                        break;
                    case R.id.rbTripWithChildrenNotMatter:
                        suitableForChildren = "";
                        break;

                }
                HomeIntroActivithy.listAll[3] = suitableForChildren;
            }
        });


    }

}
