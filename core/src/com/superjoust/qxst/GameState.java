package com.superjoust.qxst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.superjoust.qxst.commands.FlapComm;
import com.superjoust.qxst.commands.LeftComm;
import com.superjoust.qxst.commands.RightComm;

import java.util.ConcurrentModificationException;

import static com.superjoust.qxst.EMath.rn;
import static com.superjoust.qxst.Game.*;


/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class GameState extends State {
    float dtSwap = 0;
    float dtPrint = 0;
    float dtJump = 0;
    static LevelBuilder builder = new LevelBuilder();

    static World world;
    Box2DDebugRenderer box2DDebugRenderer;

    public static World getWorld() {
        return world;
    }

    protected GameState(GameStateManager gsm) {
        super(gsm);
        Vector2 v = new Vector2(WIDTH, HEIGHT);
        v = SWORLD(v);
        cam.setToOrtho(true, v.x, v.y);
        createBox2DWorld();
        createPlatforms();
        player1.onStart();
       /* Spawn spawn = new Spawn(100,300,0);
        spawn.onStart();*/
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                for (Platform p : builder.levels.get(player1.getLevel() - 1).platforms) {

                    if ((contact.getFixtureA().getBody().equals(player1.getBody()) && contact.getFixtureB().getBody().equals(p.getBody()))
                            || (contact.getFixtureA().getBody().equals(p.getBody()) && contact.getFixtureB().getBody().equals(player1.getBody()))) {
                        player1.setJumping(false);
                    }
                }
                for (Enemy e : builder.levels.get(player1.getLevel() - 1).enemies) {

                    if ((contact.getFixtureA().getBody().equals(player1.getBody()) && contact.getFixtureB().getBody().equals(e.getBody()))
                            || (contact.getFixtureA().getBody().equals(e.getBody()) && contact.getFixtureB().getBody().equals(player1.getBody()))) {

                        if (player1.getBody().getPosition().y < e.getBody().getPosition().y - .1) {
                            e.setDead(true);

                        }
                        if (player1.getBody().getPosition().y > e.getBody().getPosition().y + .1f) {
                            player1.setDead(true);

                        }

                    }
                }
                for (Egg e : builder.levels.get(player1.getLevel() - 1).eggs) {

                    if ((contact.getFixtureA().getBody().equals(player1.getBody()) && contact.getFixtureB().getBody().equals(e.getBody()))
                            || (contact.getFixtureA().getBody().equals(e.getBody()) && contact.getFixtureB().getBody().equals(player1.getBody()))) {
                         e.setDead(true);
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });


    }
    //builder.testLvl=l;


    void createBox2DWorld() {
        box2DDebugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 18f), true);
    }


    void createPlatforms() {
        builder.build();
        builder.displayLevel(1);

    }

    void clearWorld() {
        for (Platform p : builder.testLvl.platforms) {
            world.destroyBody(p.getBody());
        }
        for (Enemy p : builder.testLvl.enemies) {
            world.destroyBody(p.getBody());
        }
        if (player1.getLevel() - 1 < builder.levels.size()) {
            for (Platform p : builder.levels.get(player1.getLevel() - 1).platforms) {
                if (p.body != null)
                    world.destroyBody(p.getBody());
            }
            for (Enemy p : builder.levels.get(player1.getLevel() - 1).enemies) {
                if (p.body != null)
                    world.destroyBody(p.getBody());
            }

        }

    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            if (dtPrint > 1f) {
                builder.testLvl.print();
                out("");
                dtPrint = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(player1.canJump())
                player1.jump();
        }else if(!player1.isJumping()){
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && dtJump >.1f) {
            player1.addRun(Gdx.graphics.getDeltaTime());
            player1.queueComm(new LeftComm());
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)&& dtJump >.08f) {
            player1.addRun(Gdx.graphics.getDeltaTime());
            player1.queueComm(new RightComm());
        }else{
            player1.clearRun();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F3)){
            Enemy e = new Enemy();
            e.onStart();
            builder.levels.get(player1.getLevel()-1).enemies.add(e);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F2)) {
            if (dtSwap > 1f) {
                clearWorld();
                player1.addLevel();
                builder.displayLevel(player1.getLevel());
                dtSwap = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F1)) {
            if (dtSwap > 1f) {

                clearWorld();
                player1.addLevel();
                Level l = new Level();
                for (int i = 0; i <10; i++) {
                    int a = 0;
                    if (rn.nextBoolean())
                        a = rn.nextInt(360);
                    else
                        a = 0;
                    Platform p = new Platform(rn.nextInt((int) Game.WIDTH), rn.nextInt((int) Game.HEIGHT), rn.nextInt(500) + 100, 10, a);
                    p.onStart();
                    l.addPlatform(p);
                }
                for (int i = 0; i < 5; i++) {
                    Enemy e = new Enemy();
                    e.onStart();
                    l.addEnemies(e);
                }
                builder.testLvl = l;
                builder.testLvl = l;

                /*
                builder.displayLevel(player1.getLevel());
                dtSwap = 0;*/
                dtSwap = 0;
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        player1.update(dt);
        dtSwap += dt;
        dtPrint += dt;
        dtJump+=dt;
        builder.update(dt);
        builder.updateTestLvl(dt);
        world.step(dt, 6, 2);
        try {
            for (HoverText h : HoverText.texts) {
                h.updateDT();

            }
        }catch (ConcurrentModificationException ex){
        }
    }

    @Override
    public void render(SpriteBatch sb, ShapeRendererExt sr) {
        sr.setProjectionMatrix(cam.combined);
        box2DDebugRenderer.render(world, cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        player1.drawSR(sr);
        builder.drawSR(sr);
        sr.end();
        sb.begin();
        player1.drawSB(sb);
        for (HoverText h:HoverText.texts){
            h.draw(sb);

        }
        sb.end();
        builder.removeDeadEnemies();
        builder.removeDeadEggs();
        player1.death();
        if(!world.isLocked())
            builder.spawnEggs();

    }

    static void removeBody(Body b){
        world.destroyBody(b);
    }
    @Override
    public void dispose() {

    }
}
