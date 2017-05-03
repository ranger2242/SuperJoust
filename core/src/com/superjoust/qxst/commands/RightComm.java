package com.superjoust.qxst.commands;


import com.superjoust.qxst.Game;
import com.superjoust.qxst.Vector2;

import static com.superjoust.qxst.Game.player1;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class RightComm extends Command {
    public RightComm(){

    }

    @Override
    public void execute() {
       Vector2 vec = new Vector2(1,0);
        player1.move(Game.SPIXEL(vec));
    }
}
