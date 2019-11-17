package com.jeremy.datum.state;

import java.awt.*;

import com.jeremy.datum.state.states.*;

public abstract class State
{
    public static State state;
    public static State menuState;
    public static State gameState;
    
    static {
        State.menuState = new MenuState();
        State.gameState = new GameState();
    }
    
    public abstract void tick();
    
    public abstract void render(final Graphics2D p0);
    
    public abstract void enter();
}
