package com.superjoust.qxst;

import com.badlogic.gdx.math.Vector2;
import com.superjoust.qxst.shapes.Triangle;

/**
 * Created by Dago on 5/1/2017.
 */
public class Player {
    int lives;
    Vector2 position;
    Vector2 velocity;
    Vector2 accel;
    Triangle shape;
    long score;
    public Player(){
        lives = 5;

    }

}
