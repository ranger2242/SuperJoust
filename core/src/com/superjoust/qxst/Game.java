package com.superjoust.qxst;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends ApplicationAdapter {
    public static Player player1 = new Player();
    public static Enemy testEnemy = new Enemy();
    SpriteBatch batch;
    ShapeRendererExt sr;
    public static int SCL = 50;
    GameStateManager gsm = new GameStateManager();

    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    public static float GRAVITY = .085f;

    public static void out(String s) {
        System.out.println(s);
    }

    @Override
    public void create() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        sr = new ShapeRendererExt();
        gsm.push(new GameState(gsm));
        //player1.onStart();

    }

    public static Vector2 SPIXEL(Vector2 v) {
        Vector2 x = new Vector2();
        x.x = SCL * v.x;
        x.y = SCL * v.y;
        return x;
    }

    public static Vector2 SWORLD(Vector2 v) {
        Vector2 x = new Vector2();
        x.x = v.x / SCL;
        x.y = v.y / SCL;
        return x;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch, sr);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static ArrayList<Level> initFile() {

        try {
            FileReader file = new FileReader("data\\levels.txt");
            BufferedReader bf = new BufferedReader(file);
            String s;
            int count = 0;
            ArrayList<Level> levels = new ArrayList<>();
            for(int x =0; x< 50; x++){
                levels.add(new Level());
            }
            while ((s = bf.readLine()) != null) {
                if (!s.equals("")) {
                    String[] ars = s.split(" ");
                    Float[] parsed = new Float[ars.length];
                    for (int i = 0; i < parsed.length; i++) {
                        parsed[i] = Float.parseFloat(ars[i]);
                    }
                    Platform p = new Platform(parsed[0], parsed[1], parsed[2], parsed[3], parsed[4]);
                    if (levels.get(count).equals(null)) {
                        levels.set(count, new Level());
                    } else levels.get(count).platforms.add(p);

                }else count++;
            }
            return levels;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
