package org.furygames.screens;

import org.furygames.furyshimp.FuryShimp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class GenericScreen implements Screen {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	protected float genericScreenX=Gdx.graphics.getWidth();
	protected float genericScreenY=Gdx.graphics.getHeight();
	protected FuryShimp universalMonkey;
	protected Stage stage;
	protected FitViewport fitViewport;
	protected SpriteBatch batch;
	
	public GenericScreen (FuryShimp universalMonkey) {
		this.universalMonkey = universalMonkey;
		
		fitViewport = new FitViewport(WIDTH, HEIGHT);
		stage = new Stage(fitViewport);
		
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}