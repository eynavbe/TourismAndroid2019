package com.example.myprojectandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
class IntroFragment_2 extends Fragment {
    Spinner sHomeTypeTransportation;
    String trackType = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_fragment_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sHomeTypeTransportation = view.findViewById(R.id.sHomeTypeTransportation);
        //Spinner Type Transportation
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.HomeTypeTransportation,R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sHomeTypeTransportation.setAdapter(arrayAdapter);
        sHomeTypeTransportation.setOnItemSelectedListener(onItemSelectedListener());
        System.out.println("trackType "+trackType);

    }
    private AdapterView.OnItemSelectedListener onItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                if (text.equals("בחר תחבורה")){
                    trackType = "";
                    HomeIntroActivithy.listAll[1] = trackType;

                }else {trackType = text;
                    HomeIntroActivithy.listAll[1] = trackType;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        };

    }
}
