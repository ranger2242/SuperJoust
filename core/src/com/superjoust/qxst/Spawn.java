package com.superjoust.qxst;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import static com.superjoust.qxst.Game.SCL;

/**
 * Created by Dago on 5/1/2017.
 */
public class Spawn {



    Body body =null;
    BodyDef playerDef=new BodyDef();
    FixtureDef fixtureDef = new FixtureDef();
    protected PolygonShape shape;
    Vector3 xya=new Vector3();

    ;

    Enemy enemy;
    public Spawn(float x, float y,float a){
        xya.set(x,y,a);
        onStart();


    }
    void onStart(){

        playerDef.type= BodyDef.BodyType.StaticBody;
        playerDef.position.set(new Vector2(xya.x/SCL,xya.y/SCL));
        shape=new PolygonShape();
        shape.setAsBox(70/SCL,2/SCL);
        fixtureDef.shape= shape;
        MassData m= new MassData();
            m.mass=5;

            fixtureDef.friction=0;
            body = GameState.getWorld().createBody(playerDef);
            body.createFixture(fixtureDef);
            body.setTransform(xya.x/SCL,xya.y/SCL,xya.z/SCL);
            body.setMassData(m);
        }


    public void Player(){

    }
    void drawSR(ShapeRendererExt sr){
        sr.setColor(Color.GREEN);
        sr.rect(body.getPosition().x,body.getPosition().y,5/SCL,5/SCL);
    }
    public Enemy genEnemy(){
        return enemy;
    }



}
