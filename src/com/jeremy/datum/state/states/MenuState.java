package com.jeremy.datum.state.states;

import java.awt.*;

import com.jeremy.datum.graphics.*;
import com.jeremy.datum.gui.page.*;
import com.jeremy.datum.state.*;

public class MenuState extends State
{
    public MenuState() {
        Page.page = Page.menuPage;
    }
    
    @Override
    public void tick() {
        Page.page.tick();
    }
    
    @Override
    public void render(final Graphics2D g) {
        Page.page.render(g);
    }
    
    @Override
    public void enter() {
        State.state = this;
        Overlay.drain(1.0f);
    }
}
