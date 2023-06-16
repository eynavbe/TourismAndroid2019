package com.example.myprojectandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TourSortBottomSheet extends BottomSheetDialogFragment {
    Button btnSortDistanceCenter,btnSortDistanceMyLocation,btnSortFinish;
    TourRecyclerviewFragment tourRecyclerviewFragment = new TourRecyclerviewFragment();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tour_sort_bottom_sheet,container,false);
        btnSortDistanceCenter = view.findViewById(R.id.btnSortDistanceCenter);
        btnSortDistanceMyLocation = view.findViewById(R.id.btnSortDistanceMyLocation);
        btnSortFinish =  view.findViewById(R.id.btnSortFinish);
        btnSortFinish.setOnClickListener((v) ->{
            dismiss();
        });
        btnSortDistanceCenter.setOnClickListener((v) ->{
            List<Tour> tourList = new ArrayList<>();
            TourAdapter toursAdapter = new TourAdapter(tourList,getContext());
            toursAdapter.setTest(false);

            tourRecyclerviewFragment.sortDistance(false);

            dismiss();
        });
        btnSortDistanceMyLocation.setOnClickListener((v) ->{
            List<Tour> tourList = new ArrayList<>();
            TourAdapter toursAdapter = new TourAdapter(tourList,getContext());

            toursAdapter.setTest(true);
            tourRecyclerviewFragment.sortDistance(true);
            dismiss();

        });
        return view;
    }
}


