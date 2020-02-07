package com.shenkar.nik.bbgame;

import android.os.CountDownTimer;


public class TiempoVelPelota extends CountDownTimer {

    private Pelota pelota;
    public TiempoVelPelota(long tiempoTotal, long tiempoIntervalo, Pelota p){
        super(tiempoTotal,tiempoIntervalo);
        pelota=p;

    }

    @Override
    public void onTick(long tiempoIntervalo) {


    }

    @Override
    public void onFinish() {
        float x, y;
        x = pelota.getRect().left;
        y = pelota.getRect().top;
        if(x<0){
            x=-600;
        }else{
            x = 600;
        }

        if(y<0){
            y=-400;
        }else{
            y = 400;
        }
        pelota.setVelocidadXY(x,y);
    }


}
