package com.example.myprojectandroid;


import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesRecyclerviewFragment extends Fragment {
    RecyclerView rvCities;
    MaterialSearchBar citiesSearchBar;
    private List<String> lastSearches;

    CitiesAdapter citiesAdapter;
    List<String> suggestList = new ArrayList<>();
    CitiesDatabase citiesDatabase;

    VideoView videoView;
    Button btnVideoViewClosed;
    int timeVideo = 0;

    private RelativeLayout mainLayout;
    private int xDelta;
    private int yDelta;

    public CitiesRecyclerviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_cities_recyclerview, container, false);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCities = view.findViewById(R.id.rvCities);
        rvCities.setLayoutManager(new GridLayoutManager(getContext() , 2));
        rvCities.setHasFixedSize(true);
        citiesSearchBar = view.findViewById(R.id.citiesSearchBar);

        citiesDatabase = new CitiesDatabase(getContext());
        citiesSearchBar.setHint("חפש");
        citiesSearchBar.setCardViewElevation(10);


        loadSuggestList();

        mainLayout = view.findViewById(R.id.citiesFrame);

        videoView = view.findViewById(R.id.videoView);
        btnVideoViewClosed = view.findViewById(R.id.btnVideoViewClosed);
        if (isMyServiceRunning(VideoService.class)){
            videoView.setVisibility(View.VISIBLE);
            btnVideoViewClosed.setVisibility(View.VISIBLE);
            YouTubeFragment youTubeFragment1 = new YouTubeFragment();

            videoView.setVideoURI(Uri.parse(youTubeFragment1.doenloadURL));
            videoView.bringToFront();
            videoView.invalidate();


//            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    String highScore = getSaveData("saveTimeVideo",getContext());
//                    if((highScore == "") || (highScore.equals("0"))){
//                        mp.seekTo(youTubeFragment1.activePlayer.getCurrentTimeMillis());
//                    }else {
//                        mp.seekTo(Integer.parseInt(highScore));
//                    }
//                }
//            });

            videoView.start();
            videoView.setOnPreparedListener(mediaPlayer ->
                    mediaPlayer.setOnVideoSizeChangedListener(
                            (player, width, height) -> {
                                String highScore = getSaveData("saveTimeVideo", Objects.requireNonNull(getContext()));
                                System.out.println("highScore "+highScore);
                                if((highScore.equals("")) || (highScore.equals("0"))){
                                    mediaPlayer.seekTo(YouTubeFragment.time);
                                }else {
                                    mediaPlayer.seekTo(Integer.parseInt(highScore));
                                }
                                MediaController controller = new MediaController(getContext());
                                videoView.setMediaController(controller);
                                controller.setAnchorView(videoView);
                                LinearLayout viewGroupLevel1 = (LinearLayout)  controller.getChildAt(0);
                                //Set your color with desired transparency here:
                                viewGroupLevel1.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                                controller.setOnTouchListener(onTouchListener());

                            }
                    )
            );
            btnVideoViewClosed.setOnClickListener((v)-> {
                videoView.setVisibility(View.GONE);
                btnVideoViewClosed.setVisibility(View.GONE);
                videoView.stopPlayback();
                Intent myService = new Intent(view.getContext(), VideoService.class);
                getActivity().stopService(myService);
            });
            videoView.setOnClickListener((v)->{
                timeVideo = videoView.getCurrentPosition();
                videoView.stopPlayback();
                YouTubeFragment youTubeFragment = YouTubeFragment.newInstance(youTubeFragment1.url, timeVideo);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getFragmentManager().beginTransaction().replace(R.id.frame,youTubeFragment).addToBackStack(null).commit();
            });
        }
        videoView.setOnTouchListener(onTouchListener());

        citiesAdapter = new CitiesAdapter(getContext(),citiesDatabase.getCities());
        rvCities.setAdapter(citiesAdapter);
        citiesSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.contains(citiesSearchBar.getText())){
                        suggest.add(search);
                    }
                }
                citiesSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        citiesSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled){
                    citiesAdapter = new CitiesAdapter(getContext(),citiesDatabase.getCities());
                    rvCities.setAdapter(citiesAdapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }


//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
////        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_main, menu);
//        MenuItem item = menu.findItem(R.id.action_settings);
//        citiesSearchBar.setMenuIcon(item);
//        super.onCreateOptionsMenu(menu,inflater);
//
//    }


    private void startSearch(String text){

        citiesAdapter = new CitiesAdapter(getContext(),citiesDatabase.getCitiesByName(text));
        rvCities.setAdapter(citiesAdapter);
    }
    private void loadSuggestList() {
        suggestList = citiesDatabase.getNames();
        citiesSearchBar.setLastSuggestions(suggestList);
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onPause() {
        super.onPause();
        timeVideo = videoView.getCurrentPosition();
        saveData("saveTimeVideo", String.valueOf(timeVideo),getContext());
    }
    public static void saveData(String key, String value, Context context) {
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences("appData", 0);
        SharedPreferences.Editor editor;
        editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }



    public static String getSaveData(String key, Context context) {
        SharedPreferences sp = context.getApplicationContext()
                .getSharedPreferences("appData", 0);
        String data = sp.getString(key, "");
        return data;

    }
    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                videoView.onTouchEvent(event);

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }

}
