package com.superjoust.qxst;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.superjoust.qxst.shapes.Rectangle;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class Platform {
    Polygon dimm= new Polygon();

    public Platform(float x, float y, float w, float h, float a){
        Rectangle rect = new Rectangle(x,y,w,h,a);
        setDimm(rect.asPolygon());
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

}
