package com.shenkar.nik.bbgame;

import android.graphics.RectF;

import java.util.Random;

public class Pelota {
    private RectF rect;
    private float xVlo;
    private float yVlo;
    private float anchoPelota = 10;
    private float altoPelota = 10;

    //Constructor
    Pelota(float x){
        //La pelota comienza viajando a 100 píxeles por segundo
        xVlo = x;
        yVlo = -200;
        //La pelota es colocada en el centro de la pantalla en la parte inferior
        //La pelota es de dimenciones 10x10 pixeles
        rect = new RectF();
    }

    RectF getRect(){
        return rect;
    }


    void setVelocidadXY(float x, float y){
        xVlo = x;
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

    void setRandomX(){
        Random gen = new Random();
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
        rect.left = x / 2;
        rect.top = y - 20;
        rect.right = x/2 + anchoPelota;
        rect.bottom = y - 20  - altoPelota;
        rect.offset(0,-50);
    }
}
