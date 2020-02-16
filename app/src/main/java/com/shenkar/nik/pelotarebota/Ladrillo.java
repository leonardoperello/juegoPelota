package com.shenkar.nik.pelotarebota;
import android.graphics.RectF;

public class Ladrillo {
    private RectF rect;
    private boolean esVisible;
    private char color;
    private int golpe;

    //Constructor
    Ladrillo(int fila, int columna, int ancho, int alto, char c, int p){
        esVisible = true;
        color=c;
        golpe = p;
        int relleno = 2;
        rect = new RectF(columna * ancho + relleno,
                fila * alto +relleno,
                columna * ancho + ancho - relleno,
                fila * alto + alto - relleno);

    }


    public int getGolpe(){
        return golpe;
    }

    public void restarGolpe(){
        golpe--;
    }

    public char getColorLadrillo(){
        return color;
    }

    //retorna si el ladrillo esta visible
    public boolean getVisible(){
        return esVisible;
    }

    //retorna el ladrillo actual
    RectF getRect(){
        return this.rect;
    }

    //cambia el estado de visibilidad del ladrillo
    void setVisible(){
        esVisible = false;

    }

}
