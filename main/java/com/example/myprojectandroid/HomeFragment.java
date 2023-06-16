package com.example.myprojectandroid;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
    ImageView imageBackgroundHome;
    CitiesDatabase citiesDatabase;
    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";

    private Button btn;
    private PreferenceHelper preferenceHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (HomeIntroActivithy.listAll != null){


        if (HomeIntroActivithy.listAll[0] != null){
            AppCompatActivity activity = (AppCompatActivity)getContext();
            Bundle bundle2 = new Bundle();


//            bundle2.putParcelableArrayList(TourRecyclerviewTabLayoutFragment.EXTRA_LIST_TOUR,HomeIntroActivithy.tourList);


            Fragment fragment2 = new TourRecyclerviewTabLayoutFragment();
            fragment2.setArguments(bundle2);

//            HomeIntroActivithy.tourList = null;
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment2).addToBackStack(null).commit();

        }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = (Button) view.findViewById(R.id.btn);
        preferenceHelper = new PreferenceHelper(view.getContext());
//        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.putIntro("");
//                btn.setVisibility(View.GONE);
//                onBackPressed();
                Intent intent = new Intent(view.getContext(), HomeIntroActivithy.class);
                startActivity(intent);
            }
        });


        imageBackgroundHome = view.findViewById(R.id.imageBackgroundHome);
//        //underline
//


//
//


        //background image city
        citiesDatabase = new CitiesDatabase(getContext());
        List <City> cities = citiesDatabase.getCities();
        Random random = new Random();
        int n = random.nextInt(83);
        Picasso.get().load(imgFolder+cities.get(n).getPicURL()).into( imageBackgroundHome);


    }
}
