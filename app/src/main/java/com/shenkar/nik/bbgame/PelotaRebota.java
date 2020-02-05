package com.shenkar.nik.bbgame;

import android.app.Activity;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;


public class PelotaRebota extends Activity {

    public static LogicaPelotaRebota logicaPelotaRebota;
    MediaPlayer mMediaPlayer;
    int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this,R.raw.deadmau5_fall);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();



        //detectar la resolución de la pantalla del dispositivo y responder a ella
        Display visualizacion = getWindowManager().getDefaultDisplay();
        Point tamanio = new Point();
        visualizacion.getSize(tamanio);
        //initialize gameView and set it as a view
        logicaPelotaRebota = new LogicaPelotaRebota(this,tamanio.x,tamanio.y);
        setContentView(logicaPelotaRebota);
    }
    @Override
    protected void onDestroy() {
        //other codes
        super.onDestroy();
        mMediaPlayer.stop();
    }
    protected void onResume() {
        super.onResume();
        logicaPelotaRebota.resume();
        mMediaPlayer.seekTo(length);
        mMediaPlayer.start();

    }
    protected void onPause() {
        super.onPause();
        logicaPelotaRebota.pause();
        mMediaPlayer.pause();
        length = mMediaPlayer.getCurrentPosition();

    }

}


