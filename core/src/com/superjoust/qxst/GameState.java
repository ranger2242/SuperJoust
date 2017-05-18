package com.superjoust.qxst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.superjoust.qxst.commands.LeftComm;
import com.superjoust.qxst.commands.RightComm;

import java.util.ConcurrentModificationException;

import static com.superjoust.qxst.EMath.rn;
import static com.superjoust.qxst.Game.*;


/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class GameState extends State {
    public static float viewX=0;
    public static float viewY=0;
    float dtSwap = 0;
    float dtPrint = 0;
    float dtJump = 0;
    float dtJumpHold=0;
    static LevelBuilder builder = new LevelBuilder();

    static World world;
    Box2DDebugRenderer box2DDebugRenderer;

    public static World getWorld() {
        return world;
    }
    Vector2 prevTouch= null;
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
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    if(prevTouch==null) {
                        prevTouch=new Vector2((screenX + viewX * SCL),(screenY+viewY*SCL));
                    }else{
                        Vector2 a=new Vector2((screenX + viewX * SCL),(screenY+viewY*SCL));
                        //System.out.println(a.toString());
                        int w= (int) Math.abs(a.x- prevTouch.x);
                        int h= (int)  Math.abs(prevTouch.y-a.y);
                        int cx= (int) (prevTouch.x+w/2);
                        int cy= (int) ((int)  ( prevTouch.y-h/2));
                        Platform p=new Platform(cx,cy,w,h,0);
                        p.onStart();
                        builder.addPlatform(p);
                       //builder.displayLevel(builder.currentLvl+1);
                        //System.out.println(cx + " "+ cy + " "+ w + " "+ h+" 0" );
                        prevTouch=null;
                    }
                }
                if (button == Input.Buttons.RIGHT) {
                    builder.deletePlatform((screenX + viewX * SCL),(screenY+viewY*SCL));
                }
                return true;
            }
        });
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                for (Platform p : builder.levels.get(player1.getLevel() - 1).platforms) {

                    if ((contact.getFixtureA().getBody().equals(player1.getBody()) && contact.getFixtureB().getBody().equals(p.getBody()))
                            || (contact.getFixtureA().getBody().equals(p.getBody()) && contact.getFixtureB().getBody().equals(player1.getBody()))) {
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


    private static void updateCamPosition() {
        Vector3 position = cam.position;

        Vector3 v = new Vector3(player1.getBody().getPosition().x, player1.getBody().getPosition().y-2, 0);
        position.lerp(v, .2f);

        cam.position.set(position);
        cam.update();
        viewX = cam.position.x - cam.viewportWidth / 2;
        viewY = cam.position.y - cam.viewportHeight / 2;
    }

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
                builder.levels.get(builder.currentLvl).print();
                out("");
                dtPrint = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)&&dtJump >.033f && dtJumpHold<.005) {
            dtJumpHold+=Gdx.graphics.getDeltaTime();
            if(player1.canJump())
                player1.jump(dtJumpHold,.02f);
            dtJump=0;
        }else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)&&dtJump >.033f && (dtJumpHold>.005)){
            dtJumpHold+=Gdx.graphics.getDeltaTime();
            if(player1.canJump())
                player1.jump(dtJumpHold,.01f);
            dtJump=0;
        }
        else{
            dtJumpHold=0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player1.addRun(Gdx.graphics.getDeltaTime());
            player1.queueComm(new LeftComm());
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
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
        updateCamPosition();
    }

    @Override
    public void render(SpriteBatch sb, ShapeRendererExt sr) {
        sr.setProjectionMatrix(cam.combined);
       // sb.setProjectionMatrix(cam.combined);
        box2DDebugRenderer.render(world, cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        sr.rect(0,0,Game.WIDTH,Game.HEIGHT/SCL);
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
        builder.removeDeadPlatforms();
    }

    static void removeBody(Body b){
        world.destroyBody(b);
    }
    @Override
    public void dispose() {

    }
}
