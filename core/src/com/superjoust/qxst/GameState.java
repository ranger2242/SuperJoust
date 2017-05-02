package com.superjoust.qxst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

import com.superjoust.qxst.commands.FlapComm;
import com.superjoust.qxst.commands.LeftComm;
import com.superjoust.qxst.commands.RightComm;

import static com.superjoust.qxst.Game.player1;


/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class GameState extends State {
    float dtSwap = 0;
    static LevelBuilder builder = new LevelBuilder();

    static World world;
    Box2DDebugRenderer box2DDebugRenderer;

    public static World getWorld() {
        return world;
    }

    protected GameState(GameStateManager gsm) {
        super(gsm);
        createPlatforms();
        createBox2DWorld();
        player1.onStart();

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
        world = new World(new Vector2(0, 9.81f), true);
    }


    void createPlatforms() {
        builder.build();

    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player1.queueComm(new FlapComm());
        }
        // if(Logic.xor(Gdx.input.isButtonPressed(Input.Keys.LEFT),Gdx.input.isButtonPressed(Input.Keys.RIGHT))) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player1.queueComm(new LeftComm());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player1.queueComm(new RightComm());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F1)) {
            if (dtSwap > .5f) {
                player1.addLevel();
                dtSwap = 0;
            }

        }
        //}
    }

    @Override
    public void update(float dt) {
        handleInput();
        player1.update(dt);
        dtSwap += dt;
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
