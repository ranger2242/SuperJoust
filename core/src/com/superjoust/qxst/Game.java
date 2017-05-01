package com.superjoust.qxst;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRendererExt sr ;
	GameStateManager gsm = new GameStateManager();
	@Override
	public void create () {
		batch = new SpriteBatch();
		sr= new ShapeRendererExt();
		gsm.push(new GameState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.render(batch,sr);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
