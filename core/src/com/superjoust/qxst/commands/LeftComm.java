package com.superjoust.qxst.commands;


import com.superjoust.qxst.Game;
import com.superjoust.qxst.Vector2;

import static com.superjoust.qxst.Game.player1;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class LeftComm extends Command {
    public LeftComm(){

    }

    @Override
    public void execute() {
        Vector2 left = new Vector2(-3,0);

        player1.move(Game.SPIXEL(left));
    }
}
