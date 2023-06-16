package com.example.myprojectandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

/**
 * A simple {@link Fragment} subclass.
 */
public class YouTubeFragment extends YouTubePlayerFragment {
    static String url = null;
    static YouTubePlayer activePlayer;
    String KEY_DEVELOPER = "AIzaSyA7CmQNA0Iw-14gkRKgNYLtSW2BUqZJ7qQ";
    static View view1;
    static String doenloadURL;
    static int time;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view1 = view;
    }

    public static YouTubeFragment newInstance(String url1,int timeToStart) {
        VideoRecyclerviewFragment videoRecyclerviewFragment = new VideoRecyclerviewFragment();
        if (VideoRecyclerviewFragment.videoView != null){
            VideoRecyclerviewFragment.videoView.stopPlayback();
        }
        YouTubeFragment playerYouTubeFrag = new YouTubeFragment();
        url = url1;
        playerYouTubeFrag.init(url, timeToStart);
        return playerYouTubeFrag;
    }

    private void init(String url , int timeToStart) {
        initialize(KEY_DEVELOPER, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
            }
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                activePlayer = player;
                activePlayer.setFullscreen(false);
                activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                if (!wasRestored) {
                    downloadVideo(view1);

                    activePlayer.loadVideo(url, timeToStart);
                }

            }

        });

    }


    @Override
    public void onPause() {
        super.onPause();
        time = activePlayer.getCurrentTimeMillis();
    }
    public void downloadVideo(View view){
        String link = "http://www.youtube.com/watch?v="+url;
        @SuppressLint("StaticFieldLeak")
        YouTubeUriExtractor ytEx = new YouTubeUriExtractor(view.getContext()) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if (ytFiles != null){
                    int itag = 22;
                    doenloadURL = ytFiles.get(itag).getUrl();
                    VideoService videoService = new VideoService();
                    videoService.url(Uri.parse(doenloadURL));
                    AppCompatActivity activity = (AppCompatActivity) view1.getContext();
                    activity.startService(new Intent(view1.getContext(),VideoService.class));
                }
            }
        };
        ytEx.execute(link);
    }
    public void onYouTubeVideoPaused() {
        if (activePlayer != null){
            activePlayer.pause();
        }
    }

}




















