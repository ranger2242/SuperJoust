package com.superjoust.qxst;

import java.util.ArrayList;

import static com.superjoust.qxst.EMath.rn;
import static com.superjoust.qxst.Game.player1;

/**
 * Created by Dago on 5/1/2017.
 */
public class LevelBuilder {
    ArrayList<Level> levels = new ArrayList<>();
    static Level testLvl = new Level();
    int count =0;
    int currentLvl=1;
    public void build(){
       /* level1();
        level2();
        level3();
       // ran();
        for (Level l:levels) {
            count++;
        }*/
        levels=Game.initFile();

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
        for(int i=0;i<3;i++) {
            Enemy e = new Enemy();
            lvl1.addEnemies(e);
        }
        //lvl1.addPlatform(new Platform(400, 200, 300, 15, 45));
        lvl1.addPlatform(new Platform(300, 10, 300, 15, 0));
        lvl1.addPlatform(new Platform(0, 400, 300, 15, 0));

        levels.add(lvl1);
        return  lvl1;

    }
    public Level level2(){
        Level lvl2=new Level();
        for(int i=0;i<5;i++) {
            Enemy e = new Enemy();
            lvl2.addEnemies(e);
        }
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
    public Level level3(){
        Level lvl1=new Level();

        lvl1.addPlatform(new Platform(400, 200, 300, 15, 45));
        lvl1.addPlatform(new Platform(300, 10, 300, 15, 0));
        lvl1.addPlatform(new Platform(0, 400, 300, 15, 0));

        levels.add(lvl1);
        return  lvl1;

    }
    public Level ran(){
        Level ran = new Level();
        for(int i=0;i<8;i++) {
            int a;
            if (rn.nextBoolean())
                a = rn.nextInt(360);
            else{
                a = 0;
            Platform p = new Platform(rn.nextInt((int) Game.WIDTH), rn.nextInt((int) Game.HEIGHT), rn.nextInt(300) + 100, 10, a);
            p.onStart();
            ran.addPlatform(p);
        }
    }
    levels.add(ran);
    return ran;

    }


    public void updateTestLvl(float dt) {

            for (Enemy e : testLvl.enemies) {
                e.update(dt);
            }
    }
    public void update(float dt) {
        if(currentLvl>=0) {
            Level l = levels.get(currentLvl);
            for (Enemy e : l.enemies) {
                e.update(dt);
            }
            for(Egg e:l.eggs){
                e.update(dt);
            }

        }
    }

    public void removeDeadEnemies() {
        for(int i=levels.get(currentLvl).enemies.size()-1;i>=0;i--){
            Enemy e= levels.get(currentLvl).enemies.get(i);
            if(e.isDead()){
                Egg egg= new Egg(e.body.getPosition(),e.getBody().getLinearVelocity());
                GameState.removeBody(e.getBody());
                e.destroy();
                egg.onStart();
                levels.get(currentLvl).enemies.remove(i);
                levels.get(currentLvl).addEgg(egg);

            }
        }
    }

    public void drawSR(ShapeRendererExt sr) {
        //for()
    }

    public void spawnEggs() {
    }

    public void removeDeadEggs() {
        for(int i=levels.get(currentLvl).eggs.size()-1;i>=0;i--){
            Egg e= levels.get(currentLvl).eggs.get(i);
            if(e.isSpawning()){
                levels.get(currentLvl).addEnemies(e.spawn());
                GameState.removeBody(e.getBody());
                e.destroy();
                levels.get(currentLvl).eggs.remove(i);
            }
            if(e.isDead()){
                player1.addScore(50);
                GameState.removeBody(e.getBody());
                e.destroy();
                levels.get(currentLvl).eggs.remove(i);

            }
        }
    }
}
