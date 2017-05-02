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
        capVelocity(5);
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
    }
    void capVelocity(int max){
        if(velocity.x>max)
            velocity.x=max;
        if(velocity.x<-max)
            velocity.x=-max;
        if(velocity.y>max)
            velocity.y=max;
        if(accel.x>.5)accel.x=.5f;
        if(accel.x<-.5)accel.x=-.5f;

    }
    public void update(float dt){
        for(Command c:commands){
            c.execute();
        }
        System.out.println(position.toString()+" _ "+velocity.toString()+" _ "+accel.toString());
        move();
        wrapPlayer();
        capVelocity(5);
        commands.clear();
    }
   public void addAcceleration(Vector2 acc){
        accel.add(acc);
   }

    public Triangle getShape() {
        return shape;
    }
}
