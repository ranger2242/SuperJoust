package com.superjoust.qxst;

import com.badlogic.gdx.math.Vector2;
import com.superjoust.qxst.commands.Command;
import com.superjoust.qxst.shapes.Triangle;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Dago on 5/1/2017.
 */
public class Player {
    protected int lives=0;
    protected int numLevel;
    protected Vector2 position=new Vector2(0,0);
    protected Vector2 velocity=new Vector2(0,0);
    protected Vector2 accel=new Vector2(0,0);
    protected Triangle shape=new Triangle();
    protected long score=0;
    protected int width = 30;
    Queue<Command> commands = new LinkedList<>();
    public Player(){
        onStart();
        shape=new Triangle(position,width);
    }
    public void onStart(){
        changePos(new Vector2(300,300));
        lives = 5;
        score = 0;
        numLevel =1;


    }
    public void queueComm(Command c){
        commands.add(c);
    }
    public void changePos(Vector2 v){
       position.set(v);
       shape.translate(v);
    }
    public void move(){
        velocity.add(accel);
        velocity.add(0,-Game.GRAVITY);
        capVelocity(6);
        position.add(velocity);
        shape.translate(position);
    }
    void wrapPlayer(){
        if(position.x<0){
            position.x=Game.WIDTH;
        }
        if(position.x>Game.WIDTH){
            position.x=0;
        }
        if(position.y<0)position.y=1;
        if(position.y>Game.HEIGHT) {
            position.y = Game.HEIGHT - 1;
            velocity.y=-velocity.y;
        }

    }
    void capVelocity(int max){
        if(velocity.x>max) velocity.x=max;
        if(velocity.x<-max) velocity.x=-max;
        if(velocity.y<(float)-(max)*3/4) velocity.y=(float) (-max)*3/4;
        if(accel.x>.2)accel.x=.2f;
        if(accel.x<-.2)accel.x=-.2f;
        if((int)position.y==1){
            velocity.y=0;
        }
        float wind=.05f;
        accel.set(accel.x*wind,accel.y*wind);
    }
    public void update(float dt){
        for(Command c:commands){
            c.execute();
        }
       // System.out.println(position.toString()+" _ "+velocity.toString()+" _ "+accel.toString());
        move();
        wrapPlayer();
        capVelocity(6);
        commands.clear();
    }
   public void addAcceleration(Vector2 acc){
        accel.add(acc);
   }

    public Triangle getShape() {
        return shape;
    }

    public void addVelocity(Vector2 v) {
       velocity.add(v);
    }

    public void addPosition(Vector2 v) {
       position.add(v);
    }

    public int getLevel() {
       return numLevel;
    }
    public void addLevel(){
       numLevel++;
    }
}
