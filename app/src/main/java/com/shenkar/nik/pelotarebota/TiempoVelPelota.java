package com.shenkar.nik.pelotarebota;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;


public class TiempoVelPelota extends CountDownTimer {

    private Pelota pelota;
    private Context context;

    private float dirY;
    private float dirX;
    TiempoVelPelota(Context c, long tiempoTotal, long tiempoIntervalo, Pelota p){
        super(tiempoTotal,tiempoIntervalo);
        pelota=p;
        context = c;
    }

    void valorYX(float dY, float dX){
        dirY = dY;
        dirX = dX;
    }

    @Override
    public void onTick(long tiempoIntervalo) {
        if(dirY <0){
            pelota.setVelocidadXY(pelota.getyVlo()-150);
        }else{
            pelota.setVelocidadXY(pelota.getyVlo()+150);
        }

        if(dirX <0){
            pelota.setVelocidadXY(pelota.getyVlo()-150);
        }else{
            pelota.setVelocidadXY(pelota.getyVlo()+150);
        }

    }

    @Override
    public void onFinish() {
        Intent intent = new Intent(context,FinalLevel1.class);
        context.startActivity(intent);
        this.cancel();
    }


}
