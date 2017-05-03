package com.superjoust.qxst;

import java.util.ArrayList;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class Level {
    ArrayList<Platform> platforms = new ArrayList<>();
    ArrayList<Spawn> spawners = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    int levelNum;

    public Level(int levelNum, ArrayList<Platform> platforms, ArrayList<Enemy> enemies, ArrayList<Spawn> spawners) {
        this.levelNum = levelNum;
        this.spawners = spawners;
        this.enemies = enemies;
        this.platforms = platforms;
    }

    public Level() {
    }

    public void clearSpawnerList() {spawners.clear();}
    public void clearPlatformList() {
        platforms.clear();
    }
    public void clearEnemiesList() {enemies.clear();}

    public void deleteSpawn(int ind) {spawners.remove(ind);}
    public void deletePlatfrom(int ind) {platforms.remove(ind);}
    public void deleteEnemy(int ind) {enemies.remove(ind);}

    public void addSpawner(Spawn s) {spawners.add(s);}
    public void addPlatform(Platform p) {platforms.add(p);}
    public void addEnemies(Enemy e) {enemies.add(e);}
    public void draw(ShapeRendererExt sr){
        drawList(sr);
    }


    public void drawList(ShapeRendererExt sr) {
        for (Platform p : platforms) {
            p.draw(sr);
        }
    }


}
