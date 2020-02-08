package com.shenkar.nik.bbgame;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;


public class TiempoVelPelota extends CountDownTimer {

    private Pelota pelota;
    private Context context;

    private float velY;
    TiempoVelPelota(Context c, long tiempoTotal, long tiempoIntervalo, Pelota p){
        super(tiempoTotal,tiempoIntervalo);
        pelota=p;
        context = c;
    }

    void valorY(float vy){
        velY = vy;
    }

    @Override
    public void onTick(long tiempoIntervalo) {
        if(velY<0){
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
