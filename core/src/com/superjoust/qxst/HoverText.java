package com.superjoust.qxst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Dago on 5/3/2017.
 */



    /**
     * Created by Chris Cavazos on 5/28/2016.
     */
    public class HoverText {
        public static ArrayList<HoverText> texts = new ArrayList<>();
        private Color color;
        private final String text;
        private boolean active=false;
        private final float x;
        private final float y;
        private float px=0;
        private float py=0;
        private int ymod;
        private float dtHov;
        private float dtFlash;
        protected float alpha=1;
        private final boolean flash;
        private boolean cycle;
        private float time;
        BitmapFont font;

        public HoverText(String s, float t, Color c, float x1, float y1, boolean flash){

            active=true;
            time=t;
            text=s;
            color=c;
            x=x1;
            y=y1;
            this.flash=flash;
            if(flash){
                dtFlash=0;
                cycle=true;
            }
            texts.add(this);
        }
        public void updateDT() {
            dtHov += Gdx.graphics.getDeltaTime();
            if (flash) dtFlash += Gdx.graphics.getDeltaTime();
            //delete inactive hoverText
            boolean[] index;
            if (!texts.isEmpty()) {
                index = new boolean[texts.size()];
                texts.stream().filter(h -> !h.isActive()).forEach(h -> index[texts.indexOf(h)] = true);
                for (int i = texts.size() - 1; i >= 0; i--) {
                    try {
                        if (index[i]) {
                            texts.remove(i);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                while (HoverText.texts.size() > 10) HoverText.texts.remove(0);

                if (active) {
                    float time = 1.2f;
                    if (dtHov < time) {
                      //  Game.setFontSize(3);
                        CharSequence cs = text;
                        GlyphLayout gl = new GlyphLayout();
                        gl.setText(Game.getFont(), cs);
                        alpha= (float) -(Math.pow(Math.E,5*((dtHov/time)-1f)+1));
                        if (dtFlash > .1) {
                            if (cycle) {
                                font.setColor(Color.WHITE);
                            } else font.setColor(color);
                            cycle = !cycle;
                            dtFlash = 0;
                        }
                       /* font.setColor(color);
                        font.getColor().a=alpha;*/
                        px = (int) (x - (gl.width / 2));
                        py = y + ymod;

                        ymod++;
                    } else {
                        dtHov = 0;
                        ymod = 0;
                        active = false;
                    }

                }
            }
        }
        public void draw(SpriteBatch sb) {
            if (active) {
                if (dtHov < time) {
                   /* font.setColor(color);*/
                    Game.getFont().draw(sb, text, px,py);

                }
            }
//        font.getColor().a=1;


        }
        public boolean isActive() {
            return active;
        }

        public float getTime() {
            return time;
        }
    }
