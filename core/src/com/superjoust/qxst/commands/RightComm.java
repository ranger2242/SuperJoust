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
        float f= (float) (2*Math.exp(player1.getRun())-1.5f);
        if(f>1.5)
            f=1.5f;
       Vector2 vec = new Vector2(f,0);
        player1.move(Game.SPIXEL(vec));
    }
}
