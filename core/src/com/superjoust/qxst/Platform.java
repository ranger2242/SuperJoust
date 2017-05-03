package com.superjoust.qxst;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.superjoust.qxst.shapes.Rectangle;

import static com.superjoust.qxst.Game.SCL;
import static com.superjoust.qxst.Game.SWORLD;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class Platform {
    Polygon dimm= new Polygon();

    Body body =null;
    BodyDef playerDef=new BodyDef();
    FixtureDef fixtureDef = new FixtureDef();
    protected PolygonShape shape;
    Vector3 xya=new Vector3();
    Vector2 wh=new Vector2();
    public Platform(float x, float y, float w, float h, float a){
        Rectangle rect = new Rectangle(x,y,w,h,a);
        setDimm(rect.asPolygon());
        xya.set(x,y,a);
        wh.set(w,h);
    }
    public void onStart(){
        playerDef.type= BodyDef.BodyType.StaticBody;
        playerDef.position.set(new Vector2(xya.x/SCL,xya.y/SCL));
        shape=new PolygonShape();
        com.badlogic.gdx.math.Vector2 v=SWORLD(new Vector2(wh.x/2,wh.y/2));
        shape.setAsBox(v.x,v.y);
        fixtureDef.shape= shape;
        MassData m= new MassData();
        m.mass=5;

        fixtureDef.friction=0;
        body = GameState.getWorld().createBody(playerDef);
        body.createFixture(fixtureDef);
        body.setTransform(xya.x/SCL,xya.y/SCL,xya.z/SCL);
        body.setMassData(m);
    }

    public Polygon getDimm() {
        return dimm;
    }

    public void setDimm(Polygon dimm) {
        this.dimm = dimm;
    }
    void draw(ShapeRendererExt sr){
        sr.setColor(Color.BROWN);
        sr.polygon(dimm.getVertices());
    }

    public Body getBody() {
        return body;
    }
}
