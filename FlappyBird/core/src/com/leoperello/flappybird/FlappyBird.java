package com.leoperello.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leoperello.flappybird.states.GameStateManager;
import com.leoperello.flappybird.states.MenuState;

public class FlappyBird extends ApplicationAdapter {

	public static final int WIGHT = 480;
	public static final int HEIGHT= 720;
	public static final String TITLE = "Flappy Bird para android";
	private Music music;

	private GameStateManager gsm;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0,1,0,1);
		gsm.push(new MenuState(gsm));
		setUpMusic();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
	}

	private void setUpMusic(){
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();
	}

}
