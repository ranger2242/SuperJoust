package com.superjoust.qxst.commands;

import com.badlogic.gdx.math.Vector2;

import static com.superjoust.qxst.Game.player1;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class LeftComm extends Command {
    public LeftComm(){

    }

    @Override
    public void execute() {
        player1.addAcceleration(new Vector2(-.007f,0));
    }
}
