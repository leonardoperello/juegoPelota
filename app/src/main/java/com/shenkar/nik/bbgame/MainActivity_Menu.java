package com.shenkar.nik.bbgame;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.shenkar.nik.bbgame.R;

public class MainActivity_Menu extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.deadmau5_ft_rob_swire_ghosts_n_stuff);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

        Button soundButton =  findViewById(R.id.sound);
        Button playButton =   findViewById(R.id.play);

        playButton.setOnClickListener(this);
        soundButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.play:
                Intent intent = new Intent(this, PelotaRebota.class);
                startActivity(intent);
                mMediaPlayer.stop();
                break;
           /*
            case R.id.sound:
                Intent intent2 = new Intent(this,soundActivity.class);
                startActivity(intent2);
                break;

            */
        }
    }
}
