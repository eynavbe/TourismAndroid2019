package com.example.myprojectandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class TourRecyclerviewTabLayoutFragment extends Fragment {
    public static final String EXTRA_LIST_TOUR = "list_tour";
    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tiMap;
    TabItem tiInformation;
    City city;
    List <Tour> listTours;
    static View view1;
    public static final String EXTRA_CITY = "city";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_fragment_tour_recyclerview, container, false);
    }
    @SuppressLint("WrongConstant")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tiMap = view.findViewById(R.id.tiMap);
        tiInformation = view.findViewById(R.id.tiInformation);
         Bundle bundle = this.getArguments();

        if (bundle != null) {
            city = bundle.getParcelable(EXTRA_CITY);
            if (city == null){
                listTours = bundle.getParcelableArrayList(EXTRA_LIST_TOUR);
            }
        }
            if (HomeIntroActivithy.listAll[0] != null) {
                ToursDataSourceJson toursDataSourceJson;
                TourDatabaseHelper db = new TourDatabaseHelper(getContext());
                if (db.getToursCount() == 0) {
                    toursDataSourceJson = new ToursDataSourceJson();
                    List<Tour> tourListJson = toursDataSourceJson.doInBackground();
                    for (Tour tour : tourListJson) {
                        db.addTour(tour);
                    }

                }
                String[] listAll = HomeIntroActivithy.listAll;
                List<String> regions = new ArrayList<>();
                if (listAll[6] != null) {
                    regions = Arrays.asList(listAll[6].split(","));
                    regions.set(0, regions.get(0).replace("[", ""));
                    regions.set(regions.size() - 1, regions.get(regions.size() - 1).replace("]", ""));
                    if (regions.size() > 1) {
                        for (int i = 1; i < regions.size(); i++) {
                            regions.set(i, regions.get(i).replace(" ", ""));
                        }
                    }
                }

                List<String> types = new ArrayList<>();
                if (listAll[4] != null) {
                    types = Arrays.asList(listAll[4].split(","));
                    types.set(0, types.get(0).replace("[", ""));
                    types.set(types.size() - 1, regions.get(types.size() - 1).replace("]", ""));
                    if (types.size() > 1) {
                        for (int i = 1; i < types.size(); i++) {
                            types.set(i, types.get(i).replace(" ", ""));
                        }
                    }
                }


                String trackType = listAll[1];
                String accessibility = listAll[2];
                String suitableForChildren = listAll[3];
                int time = 6;
                if (listAll[0] != null) {
                    time = Integer.parseInt(listAll[0]);
                }
                boolean weather = Boolean.parseBoolean(listAll[5]);
                listTours = db.getToursByTypeHome(HomeIntroActivithy.myLocationMap, regions, types, trackType, accessibility, suitableForChildren, time, weather);
                HomeIntroActivithy.listAll[0] = null;

        }
        view1 = view;
        tabLayout = view.findViewById(R.id.tablayout_id);

        Fragment fragment = new TourRecyclerviewFragment();

        Bundle bundle3 = new Bundle();
        bundle3.putParcelable(TourRecyclerviewFragment.EXTRA_CITY, city);
        bundle3.putParcelableArrayList(TourRecyclerviewFragment.EXTRA_LIST_TOUR,(ArrayList<? extends Parcelable>) listTours);
        fragment.setArguments(bundle3);
        replaceFragment(fragment);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                            try {
                                Fragment fragment = new TourRecyclerviewFragment();

                                Bundle bundle3 = new Bundle();
                                bundle3.putParcelable(TourRecyclerviewFragment.EXTRA_CITY, city);
                                bundle3.putParcelableArrayList(TourRecyclerviewFragment.EXTRA_LIST_TOUR,(ArrayList<? extends Parcelable>) listTours);
                                bundle3.putBoolean(TourRecyclerviewFragment.EXTRA_WAS_MAP,true);
                                fragment.setArguments(bundle3);
                                replaceFragment(fragment);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        break;

                    case 1:
                            try  {
                                Fragment fragment = new ToursMapFragment();
                                Bundle bundle1 = new Bundle();
                                bundle1.putParcelable(ToursMapFragment.EXTRA_CITY,city);
                                List<Tour> tourArrayList = new ArrayList<>();
                                StreamDBHelper streamDBHelper = new StreamDBHelper(getContext());
                                List<TourMyLocationDistance> tourMyLocationDistances = streamDBHelper.getListTourMyLocationDistances();
                                for (int i = 0; i < tourMyLocationDistances.size(); i++) {
                                    tourArrayList.add(tourMyLocationDistances.get(i).getTour());
                                }
                                if (tourArrayList.size() == 0){
                                    tourArrayList = listTours;
                                }
                                bundle1.putParcelableArrayList(ToursMapFragment.EXTRA_LIST_TOURS, (ArrayList<? extends Parcelable>) tourArrayList);
                                fragment.setArguments(bundle1);
                                replaceFragment(fragment);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {




            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


    });

    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null).replace(R.id.FrameLayoutTabLayout, fragment);

        transaction.commit();
    }
}
