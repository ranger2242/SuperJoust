package com.superjoust.qxst.shapes;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class Rectangle extends com.badlogic.gdx.math.Rectangle {
    protected float[] verts = new float[8];
    public Rectangle(float x, float y, float w, float h, float a){
        float r = (float) Math.toRadians(a);
        Vector2 center=new Vector2(x,y);
        Vector2 pa,pb,pc,pd;
        pa=new Vector2(x-(w/2),y-(h/2));
        pb=new Vector2(x+(w/2),y-(h/2));
        pc=new Vector2(x+(w/2),y+(h/2));
        pd=new Vector2(x-(w/2),y+(h/2));

        verts[0]=rotatePoint(pa,center,r).x;
        verts[1]=rotatePoint(pa,center,r).y;
        verts[2]=rotatePoint(pb,center,r).x;
        verts[3]=rotatePoint(pb,center,r).y;
        verts[4]=rotatePoint(pc,center,r).x;
        verts[5]=rotatePoint(pc,center,r).y;
        verts[6]=rotatePoint(pd,center,r).x;
        verts[7]=rotatePoint(pd,center,r).y;
    }
    public Vector2 rotatePoint(Vector2 point, Vector2 axis, float ang){
        float x= (float) ((point.x-axis.x)*Math.cos(ang)-(point.y-axis.y)*Math.sin(ang)+axis.x);
        float y= (float) ((point.y-axis.y)*Math.cos(ang)+(point.x-axis.x)*Math.sin(ang)+axis.y);
        return new Vector2(x,y);
    }
    public float[] getVerts(){
        return verts;
    }
    public Polygon asPolygon(){
        return new Polygon(getVerts());
    }
}
