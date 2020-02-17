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
    // Devolución de llamada disparada a intervalos regulares.
    public void onTick(long tiempoIntervalo) {
        if(dirY <0){
            pelota.setVelocidadY(pelota.getyVlo()-25);
        }else{
            pelota.setVelocidadY(pelota.getyVlo()+25);
        }

        if(dirX <0){
            pelota.setVelocidadX(pelota.getxVlo()-5);
        }else{
            pelota.setVelocidadX(pelota.getxVlo()+5);
        }

    }

    @Override
    // Devolución de llamada activada cuando se acabe el tiempo
    public void onFinish() {
        Intent intent = new Intent(context, JuegoTerminado.class);
        context.startActivity(intent);
        this.cancel();
    }
}
