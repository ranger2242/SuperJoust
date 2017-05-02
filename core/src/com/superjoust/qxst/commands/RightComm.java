package com.superjoust.qxst.commands;

import com.badlogic.gdx.math.Vector2;

import static com.superjoust.qxst.Game.player1;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class RightComm extends Command {
    public RightComm(){

    }

    @Override
    public void execute() {
        player1.addAcceleration(new Vector2(.08f,0));
    }
}
