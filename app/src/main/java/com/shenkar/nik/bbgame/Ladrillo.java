package com.shenkar.nik.bbgame;
import android.graphics.RectF;

public class Ladrillo {
    private RectF rect;
    private boolean esVisible;

    //Constructor
    Ladrillo(int fila, int columna, int ancho, int alto){
        esVisible = true;
        int relleno = 2;
        rect = new RectF(columna * ancho + relleno,
                fila * alto +relleno,
                columna * ancho + ancho - relleno,
                fila * alto + alto - relleno);

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
        if(!esVisible){
            esVisible = true;
        }
        else{
            esVisible = false;
        }

    }

}
