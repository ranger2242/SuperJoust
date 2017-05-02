package com.superjoust.qxst;

import com.superjoust.qxst.commands.Command;
import com.superjoust.qxst.shapes.Line;
import com.superjoust.qxst.shapes.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Dago on 5/1/2017.
 */
public class Player {
    protected int lives=0;
    protected int level;
    protected Vector2 position=new Vector2(0,0);
    protected Vector2 velocity=new Vector2(0,0);
    protected Vector2 accel=new Vector2(0,0);
    protected Rectangle shape=new Rectangle();
    protected long score=0;
    protected int width = 30;

    Queue<Command> commands = new LinkedList<>();
    public Player(){
        onStart();
       // shape=new Rectangle(position.x,position.y,width,width,0);
    }
    public void onStart(){
        changePos(new Vector2(300,300));

        lives = 5;
        score = 0;
        level =1;


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
    void onLevelStart(){

    }
    void collisionHandler(){
        ArrayList<Line> surfaces = new ArrayList<>();
        for(Platform p: GameState.builder.levels.get(level-1).platforms){
            surfaces.addAll(Line.asLines(p.getDimm()));
        }
        ArrayList<Line> playerLines=Line.asLines(shape);
        boolean flip=false;
        int ind=-1;
        for(Line l: playerLines){
            for(Line l2: surfaces) {
               if(Line.intersectsLine(l,l2)){
                   flip=true;
                   ind=surfaces.indexOf(l2);
               }
            }
        }
        if(flip){
            Line vel = new Line(new Vector2(0,0),new Vector2(velocity.x,velocity.y));
            float m1= surfaces.get(ind).getAngle();
            float m2= vel.getAngle();
            float ang= (float) Math.toRadians( 180-vel.getAngle());

            float mag= (float) EMath.pathag(vel.a,vel.b);
            if(dtFlipVelocity>.04) {
                velocity.set((float) (mag * Math.cos(ang)), (float) (mag * Math.sin(ang)));
                dtFlipVelocity=0;
            }
            //velocity.set(velocity.x*-1,velocity.y*-1);
        }
    }
    float thetaMin(float m1, float m2){
        float a= (float) Math.atan((m1-m2)/(1+m1*m2));
        float b= (float) Math.atan(-(m1-m2)/(1+m1*m2));
        if(a>b)
            return a;
        else return b;
    }
    float dtFlipVelocity=0;
    public void update(float dt){
        dtFlipVelocity+=dt;
        for(Command c:commands){
            c.execute();
        }
       // System.out.println(position.toString()+" _ "+velocity.toString()+" _ "+accel.toString());
        move();
        collisionHandler();
        wrapPlayer();
        capVelocity(6);
        commands.clear();
    }
   public void addAcceleration(Vector2 acc){
        accel.add(acc);
   }

    public Rectangle getShape() {
        return shape;
    }

    public void addVelocity(Vector2 v) {
       velocity.add(v);
    }

    public void addPosition(Vector2 v) {
       position.add(v);
    }

    public int getLevel() {
       return level;
    }
    public void addLevel(){
       level++;
    }

    public Vector2 getPosition() {
        return position;
    }
}
