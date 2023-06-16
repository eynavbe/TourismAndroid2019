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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoRecyclerviewFragment extends Fragment {
    RecyclerView rvVideo;
    MaterialSearchBar searchBar;
    VideoAdapter adapter;
    List<String> suggestList = new ArrayList<>();
    VideoDatabase database;
    static VideoView videoView;
    Button btnVideoViewClosed;
     int timeVideo = 0;


    LinearLayout ggg;
    private RelativeLayout mainLayout;
    private int xDelta;
    private int yDelta;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_recyclerview, container, false);
    }
//    @Override
//    public void onDestroy() {
//        if (YouTubeFragment.activePlayer != null) {
//            videoView.setVideoURI(Uri.parse(YouTubeFragment.doenloadURL)).release();
//        }
//        super.onDestroy();
//    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvVideo = view.findViewById(R.id.rvVideo);

        rvVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVideo.setHasFixedSize(true);
        searchBar = view.findViewById(R.id.searchBar);
        database = new VideoDatabase(getContext());
        mainLayout = view.findViewById(R.id.videoFrame);
        videoView = view.findViewById(R.id.videoView);
        btnVideoViewClosed = view.findViewById(R.id.btnVideoViewClosed);
        if (isMyServiceRunning(VideoService.class)){
            videoView.setVisibility(View.VISIBLE);
            btnVideoViewClosed.setVisibility(View.VISIBLE);
            YouTubeFragment youTubeFragment1 = new YouTubeFragment();

            videoView.setVideoURI(Uri.parse(YouTubeFragment.doenloadURL));
            videoView.bringToFront();
            videoView.invalidate();
//            MediaController mediaController = new MediaController(getContext());
//            videoView.setMediaController((mediaController));
//            mediaController.setAnchorView(videoView);

//            MediaController mediaController = new MediaController(getContext());
//            videoView.setMediaController((mediaController));
//            mediaController.setAnchorView(videoView);
//            videoView.setOnPreparedListener(mp -> {
//                String highScore = getSaveData("saveTimeVideo",getContext());
//                if((highScore.equals("")) || (highScore.equals("0"))){
//                    mp.seekTo(YouTubeFragment.time);
//                }else {
//                    mp.seekTo(Integer.parseInt(highScore));
//                }
//            });
            btnVideoViewClosed.setOnClickListener((v)-> {
                videoView.setVisibility(View.GONE);
                btnVideoViewClosed.setVisibility(View.GONE);
                videoView.stopPlayback();
                Intent myService = new Intent(view.getContext(), VideoService.class);
                getActivity().stopService(myService);
            });
            videoView.start();
            videoView.setOnPreparedListener(mediaPlayer ->
                    mediaPlayer.setOnVideoSizeChangedListener(
                            (player, width, height) -> {
                                String highScore = getSaveData("saveTimeVideo", Objects.requireNonNull(getContext()));
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
//            videoView.onTouchEvent()


//            videoView.setOnTouchListener(onTouchListener2());






            videoView.setOnClickListener((v)->{
                timeVideo = videoView.getCurrentPosition();
                videoView.stopPlayback();
                YouTubeFragment youTubeFragment = YouTubeFragment.newInstance(youTubeFragment1.url, timeVideo);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getFragmentManager().beginTransaction().replace(R.id.frame,youTubeFragment).addToBackStack(null).commit();
            });
        }

        searchBar.setHint("חפש");
        searchBar.setCardViewElevation(10);
        loadSuggestList();
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled){
                    rvVideo.setAdapter(adapter);
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

        adapter = new VideoAdapter(database.getVideos(),getContext());
        rvVideo.setAdapter(adapter);

        videoView.setOnTouchListener(onTouchListener());



    }
    private void startSearch(String text){
        adapter = new VideoAdapter(database.getVideosByName(text),getContext());
        rvVideo.setAdapter(adapter);
    }
    private void loadSuggestList() {
        suggestList = database.getNames();
        searchBar.setLastSuggestions(suggestList);
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
}
































