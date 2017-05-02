package com.superjoust.qxst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superjoust.qxst.commands.FlapComm;
import com.superjoust.qxst.commands.LeftComm;
import com.superjoust.qxst.commands.RightComm;

import static com.superjoust.qxst.Game.player1;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class GameState extends State {
    float dtSwap=0;
    static LevelBuilder builder = new LevelBuilder();
    protected GameState(GameStateManager gsm) {
        super(gsm);
        createPlatforms();
        player1.onLevelStart();

    }

    void createPlatforms() {
        builder.build();

    }
    @Override
    public void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            player1.queueComm(new FlapComm());
        }
       // if(Logic.xor(Gdx.input.isButtonPressed(Input.Keys.LEFT),Gdx.input.isButtonPressed(Input.Keys.RIGHT))) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player1.queueComm(new LeftComm());
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                player1.queueComm(new RightComm());
            }
            if(Gdx.input.isKeyPressed(Input.Keys.F1)){
                if(dtSwap>.5f){
                    player1.addLevel();
                    dtSwap=0;
                }

            }
        //}
    }

    @Override
    public void update(float dt) {
        handleInput();
        player1.update(dt);
        dtSwap+=dt;

    }

    @Override
    public void render(SpriteBatch sb, ShapeRendererExt sr) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        sr.rect(player1.getShape());
        builder.levels.get(player1.getLevel()-1).draw(sr);
        sr.end();
    }

    @Override
    public void dispose() {

    }
}
