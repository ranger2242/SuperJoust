package com.superjoust.qxst;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class Level {
    ArrayList<Platform> platforms = new ArrayList<>();
    ArrayList<Spawn> spawners= new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    int levelNum;
    public Level(int levelNum,ArrayList<Platform> platforms,ArrayList<Enemy> enemies,ArrayList<Spawn> spawners){
        this.levelNum=levelNum;
        this.spawners = spawners;
        this.enemies=enemies;
        this.platforms= platforms;
    }
    public Level(){}


    public void clearPlatformList(){
        platforms.clear();
    }

    public void deletePlatfrom(int ind){
        platforms.remove(ind);
    }
    public void  clearSpawnerList(){
        spawners.clear();
    }

    public void deleteSpawn(int ind){
        spawners.remove(ind);
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
