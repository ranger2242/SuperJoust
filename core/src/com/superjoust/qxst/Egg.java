package com.superjoust.qxst;

import com.badlogic.gdx.physics.box2d.*;

import static com.superjoust.qxst.Game.SCL;

/**
 * Created by Chris Cavazos on 5/4/2017.
 */
public class Egg {
    float dtSpawn=0;
    protected CircleShape shape;
    protected Body body =null;
    protected BodyDef playerDef=new BodyDef();
    protected FixtureDef fixtureDef = new FixtureDef();
    Vector2 pos=new Vector2(), vel=new Vector2();
    boolean started = false;
    boolean dead =false;
    boolean spawning=false;
    public Egg(Vector2 pos, Vector2 vel){
        this.pos=pos;
        this.vel=vel;
    }
    public Egg(com.badlogic.gdx.math.Vector2 pos, com.badlogic.gdx.math.Vector2 vel){
        this.pos.set(pos);
        this.vel.set(vel);
    }


    public void onStart(){
        if(!started) {
            playerDef.type = BodyDef.BodyType.DynamicBody;
            playerDef.position.set(pos);
            shape = new CircleShape();
           // shape.setPosition(pos);
            shape.setRadius(.1f);
            fixtureDef.shape = shape;
            fixtureDef.friction = .1f;
            fixtureDef.restitution = .1f;
            body = GameState.getWorld().createBody(playerDef);
            body.createFixture(fixtureDef);
            MassData m = new MassData();
            m.mass = 3;
            body.setMassData(m);
            body.setLinearVelocity(vel);
            started = true;
        }
    }
    public void update(float dt){
        spawnEnemy(dt);
        wrapPlayer();

    }
    void spawnEnemy(float dt){
        dtSpawn+=dt;
        if(dtSpawn>10f){
            spawning=true;
        }
    }
    void wrapPlayer(){
        com.badlogic.gdx.math.Vector2 pos= body.getPosition();
        com.badlogic.gdx.math.Vector2 vel=body.getLinearVelocity();
        if(pos.x<0){
            pos.x=Game.WIDTH/SCL;
        }
        if(pos.x>Game.WIDTH/SCL){
            pos.x=0;
        }
        if(pos.y>Game.HEIGHT/SCL){
            spawning=true;
        }
        if(pos.y<0) {
            pos.y = 0;
            vel.y=-vel.y;
        }
        body.setLinearVelocity(vel);
        body.setTransform(pos,0);

    }


    public Body getBody() {
        return body;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    public void destroy() {
        body.setUserData(null);
        body=null;
    }

    public boolean isSpawning() {
        return spawning;
    }
    public Enemy spawn(){
        Enemy e= new Enemy(body.getPosition());
        e.onStart();
        return e;
    }
}
