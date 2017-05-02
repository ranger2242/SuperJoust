package com.superjoust.qxst.shapes;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Chris Cavazos on 1/23/2017.
 */
public class Triangle {
    float[] points= new float[6];
    Vector2 center=new Vector2();
    float length=0;
    public Triangle(){
        this(new float[]{0,0,0,0,0,0});
    }
    public Triangle(float[] p){
        points=p;
    }

    public Triangle(Vector2 ctr, float l){
        center.set(ctr);
        points=new float[]{ctr.x-l/2,ctr.y+1+l/2,ctr.x+l/2,ctr.y+l/2,ctr.x,ctr.y-l/2};
        length =l;
    }
    public void translate(Vector2 ctr){
        center.set(ctr);
        points=new float[]{ctr.x-length/2,ctr.y+length/2,ctr.x+length/2,ctr.y+1+length/2,ctr.x,ctr.y-length/2};
    }
    public float[] getPoints(){
        return points;
    }

    public boolean overlaps(Rectangle r){
        ArrayList<Line> triLines =Line.asLines(this);
        ArrayList<Line> rectLines=Line.asLines(r);
        boolean ov=false;
        for(Line l1: triLines){
            for(Line l2:rectLines){
                ov=ov|| Line.intersectsLine(l1,l2);
            }
        }
        return ov;
    }

}
