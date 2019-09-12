package com.homeworkreminder.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.homeworkreminder.R;

/**
 * 音乐服务类
 * 功能：添加闹钟音乐
 */
public class MusicService extends Service {
    String TAG = "MusicService";

    public MusicService() {
    }

    public static boolean isPlay = false;
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.bgm);
        Log.d(TAG, "onCreate: 创建MediaPlayer");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: 判断是否正在播放音乐");
        //判断是否正在播放音乐
        if (!player.isPlaying()){
            player.start();
            isPlay = player.isPlaying();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        player.stop();
        isPlay = player.isPlaying();
        player.release();
        super.onDestroy();

        Log.d(TAG, "onDestroy: ");
    }
}
