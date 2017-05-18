package com.superjoust.qxst.commands;


import com.superjoust.qxst.Game;
import com.superjoust.qxst.Vector2;

import static com.superjoust.qxst.Game.player1;

/**
 * Created by Chris Cavazos on 5/1/2017.
 */
public class FlapComm extends Command {
    public FlapComm(){

    }
    //commit
    @Override
    public void execute() {
        Vector2 vec =new Vector2(0,-35f);
        player1.move(Game.SPIXEL(vec));
    }
}
