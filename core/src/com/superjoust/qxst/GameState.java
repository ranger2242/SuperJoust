package com.superjoust.qxst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.superjoust.qxst.commands.FlapComm;
import com.superjoust.qxst.commands.LeftComm;
import com.superjoust.qxst.commands.RightComm;

import static com.superjoust.qxst.EMath.rn;
import static com.superjoust.qxst.Game.*;


/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class GameState extends State {
    float dtSwap = 0;
    float dtPrint =0;
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
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // Check to see if the collision is between the second sprite and the bottom of the screen
                // If so apply a random amount of upward force to both objects... just because
                //for(Enemy e:builder.levels.get(player1.getLevel()).enemies){
               // if((contact.getFixtureA().getBody() == player1.getBody() && contact.getFixtureB().getBody() == e) {
                //}
                //}

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

        Level l= new Level();
        for(int i=0;i<8;i++) {
            int a=0;
            if(rn.nextBoolean())
                a=rn.nextInt(360);
            else
                a=0;
            Platform p = new Platform(rn.nextInt(Game.WIDTH), rn.nextInt(Game.HEIGHT), rn.nextInt(300)+100, 10,a);
            p.onStart();
            l.addPlatform(p);
        }
        builder.testLvl=l;




        /*BodyDef bodyDef = new BodyDef();
        Body body = null;
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //create body
        bodyDef.position.set(player1.getPosition());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);


        //set shape
        shape.setAsBox(10, 10);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

// platform
        bodyDef.position.set(200, 400);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(100, 5);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);*/
    }

    void createBox2DWorld() {
        box2DDebugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 9.8f), true);
    }



    void createPlatforms() {
        //builder.build();
       //builder.displayLevel(1);

    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            if (dtPrint > 1f) {
                builder.testLvl.print();
                out("");
                dtPrint=0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player1.queueComm(new FlapComm());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player1.queueComm(new LeftComm());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player1.queueComm(new RightComm());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F1)) {
            if (dtSwap > 1f) {


                //for (Platform p : builder.levels.get(player1.getLevel()-1).platforms) {
                for (Platform p : builder.testLvl.platforms) {
                    world.destroyBody(p.getBody());
                }
                player1.addLevel();
                Level l= new Level();
                for(int i=0;i<8;i++) {
                    int a=0;
                    if(rn.nextBoolean())
                        a=rn.nextInt(360);
                    else
                        a=0;
                    Platform p = new Platform(rn.nextInt(Game.WIDTH), rn.nextInt(Game.HEIGHT), rn.nextInt(300)+100, 10,a);
                    p.onStart();
                    l.addPlatform(p);
                }
                builder.testLvl=l;

                /*
                builder.displayLevel(player1.getLevel());
                dtSwap = 0;*/
                dtSwap=0;
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        player1.update(dt);
        dtSwap += dt;
        dtPrint+=dt;
        builder.update(dt);
        world.step(dt, 6, 2);
    }

    @Override
    public void render(SpriteBatch sb, ShapeRendererExt sr) {

        box2DDebugRenderer.render(world, cam.combined);
        /*
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        sr.rect(player1.getShape());
        builder.levels.get(player1.getLevel()-1).draw(sr);
        sr.end();*/
    }

    @Override
    public void dispose() {

    }
}
