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


public class MainActivity_Menu extends AppCompatActivity  {

    MediaPlayer mMediaPlayer;

    private Button sonido, jugar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.deadmau5_ft_rob_swire_ghosts_n_stuff);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.start();

        Toast.makeText(getApplicationContext(), "Hola jugador", Toast.LENGTH_SHORT).show();


        // boton de sonido
        sonido = this.findViewById(R.id.sound);
        sonido.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                } else {
                    mMediaPlayer.start();
                }
            }
        });

        jugar = this.findViewById(R.id.playagain);
        jugar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Menu.this, PelotaRebota.class);
                startActivity(intent);
                mMediaPlayer.stop();
            }
        });
    }
}

