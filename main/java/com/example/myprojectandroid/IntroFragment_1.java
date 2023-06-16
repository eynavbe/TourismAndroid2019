package com.example.myprojectandroid;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
class IntroFragment_1 extends Fragment {
    int time = 6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_fragment_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.tvTime)).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG );
        //NumberPicker
        NumberPicker np = view.findViewById(R.id.numberPicker);
        np.setMaxValue(12);
        np.setMinValue(1);
        np.setValue(6);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                time = newVal;
                System.out.println("Selected Number : " + newVal);
                HomeIntroActivithy.listAll[0] = String.valueOf(time);
            }
        });

    }
}
