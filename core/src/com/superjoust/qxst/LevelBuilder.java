package com.superjoust.qxst;

import java.util.ArrayList;

/**
 * Created by Dago on 5/1/2017.
 */
public class LevelBuilder {
    ArrayList<Level> levels = new ArrayList<>();

    int count =0;
    int currentLvl=0;
    public void build(){
        level1();
        level2();
        for (Level l:levels) {
            count++;
        }
    }
    void displayLevel(int i){
        i--;
        currentLvl=i;
        for(Platform p:levels.get(i).platforms){
            p.onStart();
        }
        for(Enemy e:levels.get(i).enemies){
            e.onStart();
        }
    }

    public Level level1(){
        Level lvl1=new Level();
        Enemy e=new Enemy();
        lvl1.addEnemies(e);
        //lvl1.addPlatform(new Platform(400, 200, 300, 15, 45));
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


    public void update(float dt) {
        Level l= levels.get(currentLvl);
        for(Enemy e:l.enemies){
            e.update(dt);
        }

    }
}
