package com.leoperello.flappybird.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leoperello.flappybird.FlappyBird;

public class MenuState extends State {

    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gsm){
        super(gsm);
        camera.setToOrtho(false, FlappyBird.WIGHT/2, FlappyBird.HEIGHT/2);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");
    }

    // metodo que controla todas las entradas (E/S)
    @Override
    public void handleInput() {
        // evento de click
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            // libera la memoria de los elementos que estan en ese momento (pantalla inicial)
           /* dispose();*/
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    // tiene la capacidad de administrar todos los elementos graficos de nuestro juego
    @Override
    public void render(SpriteBatch spriteBatch) {

    spriteBatch.setProjectionMatrix(camera.combined);

    spriteBatch.begin();

    spriteBatch.draw(background, 0,0)/*, FlappyBird.WIGHT, FlappyBird.HEIGHT)*/;
    spriteBatch.draw(playButton, camera.position.x - playButton.getWidth() /2 , camera.position.y) /*(FlappyBird.WIGHT/2)- (playButton.getWidth()/ 2), (FlappyBird.HEIGHT/2)- (playButton.getHeight()/2))*/;
    spriteBatch.end();
    }

    // dispose es para eliminar y cambiar de estados en el juego. Pasar de una pantalla a otra
    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        System.out.println("MENU STATE DISPOSE");
    }
}
