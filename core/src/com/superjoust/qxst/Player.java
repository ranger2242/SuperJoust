package com.superjoust.qxst;

import com.badlogic.gdx.math.Vector2;
import com.superjoust.qxst.shapes.Triangle;

/**
 * Created by Dago on 5/1/2017.
 */
public class Player {
   protected int lives;
   protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 accel;
    protected Triangle shape;
    protected long score;
    protected int width=30;
    public Player(){


    }
    public void onStart(){
        changePos(new Vector2(300,300));
        lives = 5;
        score = 0;


    }
    public void changePos(Vector2 v){
       position.set(v);
       shape.translate(v);
    }
    public void move(){

    }



}
