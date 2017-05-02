package com.superjoust.qxst;

/**
 * Created by Chris Cavazos on 2/14/2017.
 */
public class Vector2 extends com.badlogic.gdx.math.Vector2 {
    public double magnitude;
    public double angle;

    public Vector2(float x, float y) {
        set(x,y);
        this.magnitude = 0;
    }
    public Vector2(float x, float y, double magnitude) {
        set(x,y);
        this.magnitude = magnitude;
    }

    public Vector2() {

    }

    public double getMagnitude(){
        return magnitude;
    }
    public double getMagnitude(Vector2 v1,Vector2 v2){
        double mag;
        mag=(double) Math.sqrt((Math.pow(v2.x-v1.x,2))+(Math.pow(v2.y-v1.y,2)));
        return mag;
    }
    public double dx(Vector2 a) {
        return this.x - a.x;
    }

    public double dy(Vector2 a) {
        return this.y - a.y;
    }


}