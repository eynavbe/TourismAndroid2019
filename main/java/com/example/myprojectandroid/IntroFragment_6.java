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
class IntroFragment_6 extends Fragment {
    RadioGroup rgWeatherConsider;
    boolean weather = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_fragment_6, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rgWeatherConsider = view.findViewById(R.id.rgWeatherConsider);
        ((TextView)view.findViewById(R.id.tvWeatherConsider)).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG );
        rgWeatherConsider.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbWeatherConsiderYes:
                        weather = true;
                        break;
                    case R.id.rbWeatherConsiderNo:
                        weather = false;
                        break;

                }
                HomeIntroActivithy.listAll[5] = String.valueOf(weather);
            }
        });

    }
}
