package com.shenkar.nik.pelotarebota;

import android.graphics.RectF;

import java.util.Random;

public class Pelota {
    private RectF rect;
    private float xVlo;
   volatile private float yVlo;
    private float anchoPelota = 17;
    private float altoPelota = 17;

    //Constructor
    Pelota(float x){
        //La pelota comienza viajando a 100 píxeles por segundo
        xVlo = x;
        yVlo = -200;

    }

    RectF getRect(){
        return rect;
    }


    float getyVlo(){
        return yVlo;
    }
    float getxVlo(){
        return xVlo;
    }

    void setVelocidadX(float x){
       xVlo = x;
    }

    void setVelocidadY(float y){
        yVlo = y;
    }


    void actualizar(long fps){
        rect.left = rect.left + (xVlo  / fps);
        rect.top = rect.top + (yVlo / fps);
        rect.right = rect.left + anchoPelota;
        rect.bottom = rect.top + altoPelota;


    }

    //Metodo para que la pelota rebote en direccion al eje y; y no se salga del rango de la pantalla
   public void contrarioY(){
        yVlo = -yVlo;
    }

    //Metodo para que la pelota rebote en direccion al eje x; y no se salga del rango de la pantalla
    void contrarioX(){
        xVlo = -xVlo;
    }


    void actualizarY(float y){
        rect.bottom = y;
        rect.top = y - altoPelota;
    }

    void actualizarX(float x){
        rect.left = x;
        rect.right = x + anchoPelota;
    }

    void reiniciar(int x, int y){
        x = x / 2;
        y = y - 70;
        rect = new RectF(x,y, x + anchoPelota, y - altoPelota);

    }
}
