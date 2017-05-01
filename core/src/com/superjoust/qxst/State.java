package com.superjoust.qxst;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Brent on 6/26/2015.
 */
@SuppressWarnings("ALL")
public abstract class State {
    public static OrthographicCamera cam;
    private Vector3 mouse;
    protected static GameStateManager gsm;

    protected State(GameStateManager gsm){
        State.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(true);
        mouse = new Vector3();
    }
    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb, ShapeRendererExt sr);
    public abstract void dispose();
}
