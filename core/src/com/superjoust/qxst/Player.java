package com.superjoust.qxst;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.superjoust.qxst.commands.Command;
import com.superjoust.qxst.commands.FlapComm;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

import static com.superjoust.qxst.Game.*;

/**
 * Created by Dago on 5/1/2017.
 */
public class Player {

    boolean dead=false;
    float dtFlipVelocity=0;
    protected int lives=0;
    protected int level;
    protected Vector2 position=new Vector2(0,0);
    protected Vector2 velocity=new Vector2(0,0);
    protected Vector2 accel=new Vector2(0,0);
    protected PolygonShape shape;
    protected long score=0;
    protected boolean jumping=false;
    protected int width = 30;
    protected float dtRun =0;

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
        com.badlogic.gdx.math.Vector2 v=SWORLD(new Vector2(15,15));
        shape.setAsBox(v.x,v.y);
        fixtureDef.shape= shape;
        fixtureDef.friction=.2f;
        fixtureDef.restitution=0f;
        body = GameState.getWorld().createBody(playerDef);
        body.createFixture(fixtureDef);
        MassData m= new MassData();
        m.mass=3
        ;
        body.setMassData(m);
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
    }
    void wrapPlayer(){
        com.badlogic.gdx.math.Vector2 pos= body.getPosition();
        com.badlogic.gdx.math.Vector2 vel=body.getLinearVelocity();
        if(pos.x<0){
            pos.x=Game.WIDTH/SCL;
        }
        if(pos.x>Game.WIDTH/SCL){
            pos.x=0;
        }
        if(pos.y>Game.HEIGHT/SCL){
            pos.y=Game.HEIGHT/SCL;
            vel.y=0;
        }
        if(pos.y<0) {
            pos.y = 0;
            vel.y=-vel.y;
        }
        body.setLinearVelocity(vel);
        body.setTransform(pos,0);

    }
    void capVelocity(int max){
        com.badlogic.gdx.math.Vector2 vel=body.getLinearVelocity();

        if(vel.x>max) vel.x=max;
        if(vel.x<-max) vel.x=-max;
       // if(vel.y<(float)-(max)*3/4) vel.y=(float) (-max)*3/4;

        body.setLinearVelocity(vel);
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
    public void update(float dt) {
        dtFlipVelocity += dt;
        for (Command c : commands) {
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
    public Body getBody() {
        return body;
    }
    public void drawSR(ShapeRendererExt sr){
        sr.setColor(Color.YELLOW);
        sr.rect((body.getPosition().x),(body.getPosition().y),.1f,.1f);
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
    public void addScore(int sc){
        score+=sc;
        new HoverText(""+sc,1, Color.WHITE,getScrCorrd().x,getScrCorrd().y,false);

    }

    public void drawSB(SpriteBatch sb){
        DecimalFormat df = new DecimalFormat(".##");
        DecimalFormat df1 = new DecimalFormat("0000000000");

        if(isDead()){
            Game.getFont().draw(sb,"DEAD",Game.WIDTH/2/SCL,Game.HEIGHT/2/SCL);
        }
        Game.getFont().draw(sb,"Score: "+df1.format(score),20,80);
        Game.getFont().draw(sb,"Lives: "+lives,20,60);
        Game.getFont().draw(sb,"y_"+df.format(body.getPosition().y),20,40);
        Game.getFont().draw(sb,"x_"+df.format(body.getPosition().x),20,20);
    }
    public Vector2 getScrCorrd(){
        return new Vector2(body.getPosition().x*SCL,HEIGHT-body.getPosition().y*SCL);
    }
    public void setDead(boolean b){
        dead=b;
    }
    public boolean isDead(){
        return dead;
    }
    public void death() {
        if(isDead()) {
            lives--;
            body.setTransform(new Vector2(Game.WIDTH/2/SCL, 0), 0);
            dead=false;
        }
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void addRun(float deltaTime) {
        dtRun+=deltaTime;
    }

    public void clearRun() {
        dtRun=0;
    }

    public float getRun() {
        return dtRun;
    }

    public boolean canJump() {
       if(!jumping)
           return true;
       else return false;
    }

    public void jump() {
        setJumping(true);
        queueComm(new FlapComm());
    }
}

