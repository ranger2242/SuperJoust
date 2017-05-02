package com.superjoust.qxst;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.superjoust.qxst.commands.Command;

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
    protected PolygonShape shape;
    protected long score=0;
    protected int width = 30;

    Body body =null;
    BodyDef playerDef=new BodyDef();
    FixtureDef fixtureDef = new FixtureDef();

    Queue<Command> commands = new LinkedList<>();

    public Player(){
       // onStart();

    }

    public void onStart(){
        playerDef.type=BodyDef.BodyType.DynamicBody;
        playerDef.position.set(getPosition());
        shape=new PolygonShape();

        shape.setAsBox(10,10);
        fixtureDef.shape= shape;
        body = GameState.getWorld().createBody(playerDef);
        body.createFixture(fixtureDef);

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
    }
    public void move(Vector2 vector2){
        body.applyForceToCenter(vector2,true);
        capVelocity(6);
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

    void collisionHandler(){
        /*
        ArrayList<Line> surfaces = new ArrayList<>();
        for(Platform p: GameState.builder.levels.get(level-1).platforms){
            surfaces.addAll(Line.asLines(p.getDimm()));
        }
        ArrayList<Line> playerLines=Line.asLines();
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

        }
        */
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

        //collisionHandler();
        wrapPlayer();
        capVelocity(6);
        commands.clear();
    }
   public void addAcceleration(Vector2 acc){
        accel.add(acc);
   }

    public PolygonShape getShape() {
        return shape;
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
    public void addVelocity(Vector2 v){
        velocity.add(v);
    }

}
