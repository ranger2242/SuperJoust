package com.superjoust.qxst;

import com.badlogic.gdx.physics.box2d.*;
import com.superjoust.qxst.commands.Command;

import java.util.LinkedList;
import java.util.Queue;

import static com.superjoust.qxst.EMath.rn;
import static com.superjoust.qxst.Game.*;

/**
 * Created by Dago on 5/1/2017.
 */
public class Enemy {
    int wrapCount=0;
    int wrapMax=rn.nextInt(3)+3;
    int holdingAlt=HEIGHT/(rn.nextInt(4)+1);;

    protected PolygonShape shape;
    protected Body body =null;
    protected BodyDef playerDef=new BodyDef();
    protected FixtureDef fixtureDef = new FixtureDef();
    float speed=(float) (5*rn.nextFloat()+5);
    float dtWander=0;
    float wanderMax=rn.nextFloat()+.5f;
    Queue<Command> commands = new LinkedList<>();

    public Enemy(){

    }
    public void onStart(){
        playerDef.type= BodyDef.BodyType.DynamicBody;
        playerDef.position.set(WIDTH/2/SCL,HEIGHT/2/SCL);
        shape=new PolygonShape();
        com.badlogic.gdx.math.Vector2 v=SWORLD(new Vector2(15,15));
        shape.setAsBox(v.x,v.y);
        fixtureDef.shape= shape;
        fixtureDef.friction=0;
        fixtureDef.restitution=.3f;
        body = GameState.getWorld().createBody(playerDef);
        body.createFixture(fixtureDef);
        MassData m= new MassData();
        m.mass=5;
        body.setMassData(m);
    }
    public void update(float dt){
        dtWander+=dt;
       moveAI();
        wrap();
    }
    void moveAI(){
        maintainAltitude(holdingAlt);
        if(dtWander>wanderMax){
            wrapCount++;
            wander();
            dtWander=0;
        }
        if(wrapCount>wrapMax) {
            wrapMax=rn.nextInt(3)+3;
            speed = (float) (5 * rn.nextFloat() + 5);
            if(rn.nextBoolean())
                speed*=-1;
            holdingAlt= rn.nextInt(HEIGHT);
            wrapCount=0;
        }
    }
    void maintainAltitude(int alt){
        int r= HEIGHT/SCL-alt/SCL;
        if(body.getPosition().y>r)
            flap();
    }
    void flap(){
        move(SPIXEL(new Vector2(0,-2)));
    }
    public void move(Vector2 vector2){
        body.applyForceToCenter(vector2,true);
    }
    void wander(){
        body.setLinearVelocity(new Vector2(speed,0));
    }
    void wrap(){
        com.badlogic.gdx.math.Vector2 pos= body.getPosition();
        com.badlogic.gdx.math.Vector2 vel=body.getLinearVelocity();
        if(pos.x<0){
            pos.x=Game.WIDTH/SCL;
        }
        if(pos.x>Game.WIDTH/SCL){
            pos.x=0;
        }
        if(pos.y>Game.HEIGHT/SCL){
            pos.y=Game.HEIGHT/SCL;
            vel.y=0;
        }
        if(pos.y<0) {
            pos.y = 0;
            vel.y=-vel.y;
        }
        body.setLinearVelocity(vel);
        body.setTransform(pos,0);

    }



}
