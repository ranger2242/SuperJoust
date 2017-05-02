package com.superjoust.qxst;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dago on 5/1/2017.
 */
public class LevelBuilder {
    ArrayList<Level> levels = new ArrayList<>();

    int count =0;
    public void build(){
        level1();
        level2();
        for (Level l:levels) {
            count++;
        }
    }


    public Level level1(){
        Level lvl1=new Level();

        lvl1.addPlatform(new Platform(400, 200, 300, 15, 45));
        lvl1.addPlatform(new Platform(300, 10, 300, 15, 0));
        lvl1.addPlatform(new Platform(0, 400, 300, 15, 0));

        levels.add(lvl1);
        return  lvl1;

    }
    public Level level2(){
        Level lvl2=new Level();

        lvl2.addPlatform(new Platform(0,0,1800,20,0));
        lvl2.addPlatform(new Platform(0,100,300,10,0));
        lvl2.addPlatform(new Platform(0,250,300,10,0));
        lvl2.addPlatform(new Platform(0,400,300,10,0));

        lvl2.addPlatform(new Platform(600,200,300,10,0));
        lvl2.addPlatform(new Platform(600,300,300,10,0));
        lvl2.addPlatform(new Platform(600,100,300,10,0));
        lvl2.addPlatform(new Platform(600,400,300,10,0));

        levels.add(lvl2);
        return  lvl2;

    }





}
