package com.example.vlad.itiseasy;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {

    MediaPlayer player;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.lobby_time);
        player.setLooping(true); // зацикливаем
    }



    @Override
    public void onDestroy() {
        player.stop();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        player.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
