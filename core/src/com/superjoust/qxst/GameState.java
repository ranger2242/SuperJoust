package com.superjoust.qxst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superjoust.qxst.commands.LeftComm;
import com.superjoust.qxst.commands.RightComm;

import static com.superjoust.qxst.Game.player1;

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
        if(Gdx.input.isButtonPressed(Input.Keys.SPACE)){

        }
       // if(Logic.xor(Gdx.input.isButtonPressed(Input.Keys.LEFT),Gdx.input.isButtonPressed(Input.Keys.RIGHT))) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player1.queueComm(new LeftComm());
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                player1.queueComm(new RightComm());
            }
        //}
    }

    @Override
    public void update(float dt) {
        handleInput();
        player1.update(dt);
    }

    @Override
    public void render(SpriteBatch sb, ShapeRendererExt sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        lvl1.drawList(sr);
        sr.triangle(player1.getShape());
        sr.end();
    }

    @Override
    public void dispose() {

    }
}
