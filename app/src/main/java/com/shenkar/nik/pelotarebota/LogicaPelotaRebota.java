package com.shenkar.nik.pelotarebota;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


@SuppressLint("ViewConstructor")
public class LogicaPelotaRebota extends SurfaceView implements Runnable {

    //puntajes
    private static final int PUNTAJEROJO = 10;
    private static final int PUNTAJEAMARILLO = 20;
    private static final int PUNTAJEVERDE = 30;

    // Este es el hilo del juego
    Thread hiloJuego = null;

    //variable de la vista que usara el thread hiloJuego
    SurfaceHolder vistaHiloJuego;

    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean estaJugando;

    //El juego al momento de iniciar esta en pausa
    boolean pausa = true;

    // variables usadas para establecer la superficie a "dibujar" y un "pincel"
    Canvas superficieDibujar;
    Paint pincel;

    // Esta variable determina los fotogramas por segundo del juego
    long fps;

    // Esta variable es usada para ayudar en el calculo de los fps
    private long tiempoPantallaCelular;

    // Tamaño de la pantalla en pixeles
    int pantallaCordX;
    int pantallaCordY;

    // variable de la paleta del juego
    Paleta paleta;

    // variable de la pelota del juego
    Pelota pelota;

    // arreglo de hasta 200 ladrillos
    Ladrillo[] ladrillos = new Ladrillo[200];
    int numLadrillo = 0;

    // Sonidos del juego
    SoundPool piletaSonido;
    int sonido1ID = -1;
    int sonido2ID = -1;
    int sonido3ID = -1;
    int vidaPerdidaID = -1;
    int explocionID = -1;

    // El puntaje
    int puntaje = 0;
    int puntajeTotal=0;

    // vidas
    int canttVida = 3;

    TiempoVelPelota velPelota;

    // Permite acceder a los recursos específicos de la aplicación y a sus clases, así como
    // llamar al padre para realizar operaciones a nivel de la aplicación, como lanzar Activities, difundir
    // mensajes por el sistema, recibir Intents
    Context mContext;

    //Constructor
    public LogicaPelotaRebota(Context context, int x, int y) {

        super(context);
        mContext = context;
        // Se inicializa los objetos vistaHiloJuego y pincel
        vistaHiloJuego = getHolder();
        pincel = new Paint();

        pantallaCordX = x;
        pantallaCordY = y;

        //Creacion de la paleta
        paleta = new Paleta(pantallaCordX);

        // Creacion de la pelota
        // Utilizamos un valor random para que la pelota inicie
        // para lados diferentes cada vez
        int r = (int) (Math.random() * 2) + 1;
        if (r == 1) {
            pelota = new Pelota(400);
        } else {
            pelota = new Pelota(-400);
        }
        velPelota = new TiempoVelPelota(mContext, 1000000, 3000, pelota);
        // cargar sonidos del juego
        piletaSonido = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        // Carga nuestros sonidos listos para usar
        sonido1ID = piletaSonido.load(context, R.raw.beep1, 1);


        sonido2ID = piletaSonido.load(context, R.raw.beep2, 1);


        sonido3ID = piletaSonido.load(context, R.raw.beep3,1);


        vidaPerdidaID = piletaSonido.load(context, R.raw.loselife,1);


        explocionID = piletaSonido.load(context, R.raw.explode,1);


        crearComponentesJuegoYReiniciar();

    }

    public void crearComponentesJuegoYReiniciar() {

        //coloca la pelota en el centro
        pelota.reiniciar(pantallaCordX, pantallaCordY);
        paleta.reiniciar(pantallaCordX, pantallaCordY);

        int anchoLadrillo = pantallaCordX / 8;
        int altoLadrillo = pantallaCordY / 20;
        int columna, fila;

        // Se construye el muro de ladrillos
        numLadrillo = 0;

        for (columna = 0; columna < 9; columna++) {
            for (fila = 0; fila < 9; fila++) {
                if (fila < 3) {
                    ladrillos[numLadrillo] = new Ladrillo(fila, columna, anchoLadrillo, altoLadrillo, 'v', 2);
                    numLadrillo++;
                    puntajeTotal = puntajeTotal + PUNTAJEVERDE;
                } else {
                    if(fila == 3 && ((columna+1) % 2) == 0){
                        ladrillos[numLadrillo] = new Ladrillo(fila, columna, anchoLadrillo, altoLadrillo, 'z', -1);
                        numLadrillo++;
                    }else{
                        if (fila < 7 && columna % 2 == 0) {
                            ladrillos[numLadrillo] = new Ladrillo(fila, columna, anchoLadrillo, altoLadrillo, 'a', 1);
                            numLadrillo++;
                            puntajeTotal = puntajeTotal + PUNTAJEAMARILLO;
                        } else {
                            if (fila > 6 && columna % 3 != 0) {
                                ladrillos[numLadrillo] = new Ladrillo(fila, columna, anchoLadrillo, altoLadrillo, 'r', 0);
                                numLadrillo++;
                                puntajeTotal = puntajeTotal + PUNTAJEROJO;
                            }
                        }
                    }
                }
            }
        }

        // si se pierde todas las vidas el puntaje y la cantidad de vida se reinicia
        if (canttVida == 0) {
            puntaje = 0;
            canttVida = 3;
        }
    }


    //en este metodo se encuentra lo que necesita ser actualizado, ya sea
    //movimiento, deteccion de colicion etc.

    public void actualizar() {

        // Comprueba si la pelota choca con un ladrillo
        for (int i = 0; i < numLadrillo; i++) {
            if (ladrillos[i].getColorLadrillo() == 'v') {
                if (ladrillos[i].getVisible()) {
                    if (RectF.intersects(ladrillos[i].getRect(), pelota.getRect())) {
                        if (ladrillos[i].getGolpe() == 0) {
                            if(ladrillos[i].getRect().left <= pelota.getRect().centerX() && pelota.getRect().centerX() <= ladrillos[i].getRect().right){
                                pelota.contrarioY();
                            }else{
                                pelota.contrarioX();
                            }
                            ladrillos[i].setVisible();
                            puntaje = puntaje + PUNTAJEVERDE;
                            piletaSonido.play(explocionID, 1, 1, 0, 0, 1);
                        } else {
                            if(ladrillos[i].getRect().left <= pelota.getRect().centerX() && pelota.getRect().centerX() <= ladrillos[i].getRect().right){
                                pelota.contrarioY();
                            }else{
                                pelota.contrarioX();
                            }
                            ladrillos[i].restarGolpe();
                            piletaSonido.play(sonido1ID, 1, 1, 0, 0, 1);
                        }
                    }
                }
            } else {
                if (ladrillos[i].getColorLadrillo() == 'a') {
                    if (ladrillos[i].getVisible()) {
                        if (RectF.intersects(ladrillos[i].getRect(), pelota.getRect())) {
                            if (ladrillos[i].getGolpe() == 0) {
                                if(ladrillos[i].getRect().left <= pelota.getRect().centerX() && pelota.getRect().centerX() <= ladrillos[i].getRect().right){
                                    pelota.contrarioY();
                                }else{
                                    pelota.contrarioX();
                                }
                                ladrillos[i].setVisible();
                                puntaje = puntaje + PUNTAJEAMARILLO;
                                piletaSonido.play(explocionID, 1, 1, 0, 0, 1);
                            } else {
                                if(ladrillos[i].getRect().left <= pelota.getRect().centerX() && pelota.getRect().centerX() <= ladrillos[i].getRect().right){
                                    pelota.contrarioY();
                                }else{
                                    pelota.contrarioX();
                                }
                                ladrillos[i].restarGolpe();
                                piletaSonido.play(sonido1ID, 1, 1, 0, 0, 1);
                            }
                        }
                    }
                } else {
                    if (ladrillos[i].getColorLadrillo() == 'z') {
                        if(ladrillos[i].getVisible()) {
                            if(RectF.intersects(ladrillos[i].getRect(),pelota.getRect())) {
                                if(ladrillos[i].getRect().left <= pelota.getRect().centerX() && pelota.getRect().centerX() <= ladrillos[i].getRect().right){
                                    pelota.contrarioY();
                                }else{
                                    pelota.contrarioX();
                                }
                                piletaSonido.play(sonido1ID, 1, 1, 0, 0, 1);
                            }
                        }
                    }else{
                        if (ladrillos[i].getVisible()) {
                            if (RectF.intersects(ladrillos[i].getRect(), pelota.getRect())) {
                                if(ladrillos[i].getRect().left < pelota.getRect().centerX() && pelota.getRect().centerX() < ladrillos[i].getRect().right){
                                    pelota.contrarioY();
                                }else{
                                    pelota.contrarioX();
                                }
                                ladrillos[i].setVisible();
                                puntaje = puntaje + PUNTAJEROJO;
                                piletaSonido.play(explocionID, 1, 1, 0, 0, 1);
                            }
                        }
                    }
                }
            }
        }
        float pantalla1 = pelota.getRect().left;
        // comprueba si la pelota choca con la paleta
        if (RectF.intersects(paleta.getRect(), pelota.getRect())) {
            char lado= 'I';
            if(pelota.getRect().left - pantalla1 > 0){
                lado = 'D';
            }
            if(pelota.getRect().centerX() < paleta.getRect().centerX()){
                if(lado == 'I'){
                    pelota.contrarioY();
                    pelota.actualizarY(paleta.getRect().top - 2);
                }else{
                    pelota.contrarioX();
                    pelota.contrarioY();
                    pelota.actualizarY(paleta.getRect().top - 2);
                }
            }else{
                if(lado == 'D'){
                    pelota.contrarioY();
                    pelota.actualizarY(paleta.getRect().top - 2);
                }else{
                    pelota.contrarioX();
                    pelota.contrarioY();
                    pelota.actualizarY(paleta.getRect().top - 2);
                }
            }
            piletaSonido.play(sonido1ID, 1, 1, 0, 0, 1);
        }
        // comprueba si la pelota toca el fondo de la pantalla
        if (pelota.getRect().bottom > pantallaCordY) {
            pelota.contrarioY();
            //   pelota.actualizarY(pantallaCordY - 1000);

            // Se pierde una vida
            canttVida--;
            pausa = true;
            piletaSonido.play(vidaPerdidaID, 1, 1, 0, 0, 1);
            pelota.reiniciar(pantallaCordX, pantallaCordY);
            paleta.reiniciar(pantallaCordX, pantallaCordY);
            if (canttVida == 0) {
                pausa = true;
                Intent intent = new Intent(mContext, GameOver.class);
                mContext.startActivity(intent);
                crearComponentesJuegoYReiniciar();
                ((Activity) mContext).finish();
            }
        }

        // Rebota la pelota cuando toca la parte superior de la pantalla
        if (pelota.getRect().top < 0) {
            pelota.contrarioY();
            pelota.actualizarY(12);
            piletaSonido.play(sonido2ID, 1, 1, 0, 0, 1);
        }

        // Rebota la pelota cuando toca la parte izquierda de la pantalla
        if (pelota.getRect().left < 0) {
            pelota.contrarioX();
            pelota.actualizarX(2);
            piletaSonido.play(sonido3ID, 1, 1, 0, 0, 1);
        }

        // Rebota la pelota cuando toca la parte derecha de la pantalla
        if (pelota.getRect().right > pantallaCordX - 10) {
            pelota.contrarioX();
            pelota.actualizarX(pantallaCordX - 22);
            piletaSonido.play(sonido3ID, 1, 1, 0, 0, 1);
        }


        // Se pausa el juego al llegar al puntaje establecido
        if (puntaje >= 100) {
            pausa = true;
            Intent intent = new Intent(mContext, JuegoTerminado.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
            crearComponentesJuegoYReiniciar();
        }
        // actualiza los movimientos de la pelota y la paleta
        paleta.actualizar(fps);
        pelota.actualizar(fps);

    }

    //se dibuja la escena recien actualizada
    public void dibujar() {
        // verificamos que la superficie de dibujo sea valido
        if (vistaHiloJuego.getSurface().isValid()) {
            // bloquear la superficie lista para dibujar
            superficieDibujar = vistaHiloJuego.lockCanvas();

            // se dibuja el color de fondo
            superficieDibujar.drawColor(Color.argb(255, 0, 0, 0));

            //color del pincel a dibujar
            pincel.setColor(Color.argb(255, 255, 255, 255));

            // dibujar la paleta
            superficieDibujar.drawRoundRect(paleta.getRect(), 20, 20, pincel);

            // dibujar la pelota
            superficieDibujar.drawOval(pelota.getRect(), pincel);
            // superficieDibujar.drawRect(pelota.getRect(), pincel);

            // dibujamos los ladrillos visibles
            for (int i = 0; i < numLadrillo; i++) {
                if (ladrillos[i].getVisible()) {
                    if (ladrillos[i].getColorLadrillo() == 'v') {
                        // cambiamos el color del pincel
                        pincel.setColor(Color.argb(255, 24, 200, 75));
                        superficieDibujar.drawRoundRect(ladrillos[i].getRect(),10,10, pincel);
                    } else {
                        if(ladrillos[i].getColorLadrillo() == 'z'){
                            // cambiamos el color del pincel
                            pincel.setColor(Color.argb(255, 23, 20, 249));
                            superficieDibujar.drawRoundRect(ladrillos[i].getRect(),10,10, pincel);
                        }else{
                            if (ladrillos[i].getColorLadrillo() == 'a') {
                                // cambiamos el color del pincel
                                pincel.setColor(Color.argb(255, 235, 249, 20));
                                superficieDibujar.drawRoundRect(ladrillos[i].getRect(),10,10, pincel);
                            } else {
                                // cambiamos el color del pincel
                                pincel.setColor(Color.argb(255, 239, 38, 35));
                                superficieDibujar.drawRoundRect(ladrillos[i].getRect(),10,10, pincel);
                            }
                        }

                    }
                }
            }


            // cambiamos el color del pincel
            pincel.setColor(Color.argb(255, 255, 255, 255));

            // dibujamos los numeros del puntaje
            pincel.setTextSize(40);
            superficieDibujar.drawText("Puntaje: " + puntaje + "   Vidas: " + canttVida, 10, 50, pincel);

            // se dibuja en la pantalla
            vistaHiloJuego.unlockCanvasAndPost(superficieDibujar);
        }
    }

    // si la actividad PelotaRebota se pausa o detiene
    // detenemos nuestro hilo
    public void pause() {
        estaJugando = false;
        try {
            hiloJuego.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    // si la actividad PelotaRebota es iniciado
    // iniciamos nuestro hilo
    public void resume() {
        estaJugando = true;
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }


    @Override
    public void run() {
        velPelota.start();
        float dirYInicial, dirXInicial;
        while (estaJugando) {
            // La variable startFrameTime guarda la hora actual en milisegundos
            long inicioTiempodePantalla = System.currentTimeMillis();
            dirYInicial = pelota.getRect().top;
            dirXInicial = pelota.getRect().left;
            // actualizar pantalla
            if (!pausa) {
                actualizar();
            }
            // Dibujar en la pantalla
            dibujar();
            //calcular los fps de la pantalla
            tiempoPantallaCelular = System.currentTimeMillis() - inicioTiempodePantalla;
            velPelota.valorYX(pelota.getRect().top - dirYInicial, pelota.getRect().left - dirXInicial);
            if (tiempoPantallaCelular >= 1) {
                fps = 1000 / tiempoPantallaCelular;
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                pausa = false;
                if (motionEvent.getX() > pantallaCordX / 2) {
                    paleta.setMovimiento(paleta.DERECHA);
                } else {
                    paleta.setMovimiento(paleta.IZQUIERDA);
                }
                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                paleta.setMovimiento(paleta.STOP);
                break;
        }
        return true;
    }
}