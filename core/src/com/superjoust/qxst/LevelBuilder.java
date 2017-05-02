package com.superjoust.qxst;

import java.util.ArrayList;

/**
 * Created by Dago on 5/1/2017.
 */
public class LevelBuilder {
    ArrayList<Level> levels = new ArrayList<>();

    public void build(){
        level1();
    }
    public Level level1(){
        Level lvl1=new Level();
        lvl1.addPlatform(new Platform(400, 200, 300, 15, 45));
        lvl1.addPlatform(new Platform(300, 10, 300, 15, 0));
        lvl1.addPlatform(new Platform(0, 400, 300, 15, 0));

        levels.add(lvl1);
        return  lvl1;

    }




}
