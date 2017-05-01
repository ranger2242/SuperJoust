package com.superjoust.qxst;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class GameState extends State {
    Level lvl1 = new Level();

    protected GameState(GameStateManager gsm) {
        super(gsm);
        createPlatforms();


    }

    void createPlatforms() {
        lvl1.addPlatform(new Platform(400, 200, 300, 15, 45));
        lvl1.addPlatform(new Platform(300, 10, 300, 15, 0));
        lvl1.addPlatform(new Platform(0, 400, 300, 15, 0));
    }
    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb, ShapeRendererExt sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        lvl1.drawList(sr);
        sr.end();
    }

    @Override
    public void dispose() {

    }
}
