package com.superjoust.qxst;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.superjoust.qxst.shapes.Line;
import com.superjoust.qxst.shapes.Rectangle;
import com.superjoust.qxst.shapes.Triangle;

/**
 * Created by Chris Cavazos on 1/21/2017.
 */
public class ShapeRendererExt extends ShapeRenderer {

    public void rect(Rectangle r) {
        polygon(r.getVerts());
    }
    public void triangle(Triangle t){
        float[] f=t.getPoints();
        triangle(f[0],f[1],f[2],f[3],f[4],f[5]);
    }
    public void line(Line l){
        line(l.a,l.b);
    }
}
