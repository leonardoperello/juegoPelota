package com.shenkar.nik.pelotarebota;

import android.graphics.RectF;

public class Paleta {

    //se crea un objeto que tiene cuatro coordenadas
    private RectF rect;

    //la longitud que tendra nuestra paleta
    private float anchoPaleta;

    //X es el extremo izquierdo del rectangulo de nuestra paleta
    private float x;
    private float y;

    //indica la velocidad en pixel por segundo que la paleta se movera
    private float velocidadPaleta;

    //las direcciones en la que se puede mover la paleta
    final int STOP = 0;
    final int IZQUIERDA = 1;
    final int DERECHA = 2;

    //posicion inicial de la paleta
    private int posicionPaleta = STOP;

    float topeDerecho;

    //constructor de la paleta
    Paleta(int pantallaX, int pantallaY){
        anchoPaleta = 130;
        float alturaPaleta = 20;
        x = pantallaX / 2;
        y = pantallaY - 20;
        rect = new RectF(x,y,x+ anchoPaleta,y + alturaPaleta);
        rect.offset(-50,-50);

        topeDerecho=pantallaX;

        //velocidad de la paleta en pixel
        velocidadPaleta = 350;

    }

    //return react to definr our paddale in breakoutView class
    RectF getRect(){
        return rect;
    }

    //change/set if the paddale is going left or else
    void setMovmentState(int estado){
        posicionPaleta = estado;
    }

    //actualizar el movimiento de la paleta
    void actualizar(long fps) {

            if (posicionPaleta == IZQUIERDA && rect.left>0) {
                x = x - velocidadPaleta / fps;
            }

            if (posicionPaleta == DERECHA && rect.right< topeDerecho) {
                x = x + velocidadPaleta / fps;
            }

            rect.left = x;
            rect.right = x + anchoPaleta;

    }

    void reiniciar(int pantallaX, int pantallaY){
        float alto = 20;
        x = pantallaX / 2;
        float y = pantallaY - 20;
        rect = new RectF(x,y,x+ anchoPaleta,y + alto);
        rect.offset(-50, -50);
    }
}
