package com.example.myprojectandroid;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import com.google.android.youtube.player.YouTubePlayer;

import androidx.annotation.Nullable;

public class VideoService extends Service {
    static Uri url;
    MediaPlayer player;
    static YouTubePlayer g;
    public void url(Uri url1){
        url = url1;
    }
    Handler handler;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Seconds, Minutes, MilliSeconds ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//                player = new MediaPlayer();

        handler = new Handler() ;


//        try {
//            player.setDataSource(this, url);
//            player.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);


//        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        player.stop();
    }

    public void youTubePlayer(YouTubePlayer activePlayer) {
        g= activePlayer;
    }
    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);
            handler.postDelayed(this, 0);
        }

    };

}
