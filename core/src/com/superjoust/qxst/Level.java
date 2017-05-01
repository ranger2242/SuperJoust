package com.superjoust.qxst;

import java.util.ArrayList;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class Level {
    ArrayList<Platform> platforms = new ArrayList<>();
    public void clearList(){
        platforms.clear();
    }
    public void delete(int ind){
        platforms.remove(ind);
    }
    public void drawList(ShapeRendererExt sr){
        for(Platform p: platforms){
            p.draw(sr);
        }
    }
    public void addPlatform(Platform p){
        platforms.add(p);
    }
}
