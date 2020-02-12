package com.shenkar.nik.pelotarebota;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


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
        mMediaPlayer.start();

        Toast.makeText(getApplicationContext(),"Hola jugador", Toast.LENGTH_SHORT).show();

        Button playButton =   findViewById(R.id.playagain);

        playButton.setOnClickListener(this);

        // boton de sonido
        Button soundButton = (Button) this.findViewById(R.id.sound);
        soundButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                }else{
                    mMediaPlayer.start();
                }
            }});
    }


    public void onClick(View v){
        switch (v.getId()){
            //va de la activity de inicio a la activity de empezar a jugar
            case R.id.playagain:
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
