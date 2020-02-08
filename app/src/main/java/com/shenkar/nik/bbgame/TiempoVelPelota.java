package com.shenkar.nik.bbgame;

import android.os.CountDownTimer;


public class TiempoVelPelota extends CountDownTimer {

    private Pelota pelota;
    private int pantallaY;
    private float velY;
    public TiempoVelPelota(long tiempoTotal, long tiempoIntervalo, Pelota p, int pY){
        super(tiempoTotal,tiempoIntervalo);
        pelota=p;
        pantallaY = pY/2;

    }

    void valorY(float vy){
        velY = vy;
    }

    @Override
    public void onTick(long tiempoIntervalo) {
        if(velY<0){
            pelota.setVelocidadXY(pelota.getyVlo()-200);
        }else{
            pelota.setVelocidadXY(pelota.getyVlo()+200);

        }

    }

    @Override
    public void onFinish() {
        if(velY<0){
            pelota.setVelocidadXY(pelota.getyVlo()-600);
        }else{
            pelota.setVelocidadXY(pelota.getyVlo()+600);
        }

    }


}
