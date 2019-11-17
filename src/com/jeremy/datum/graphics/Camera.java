package com.jeremy.datum.graphics;

import com.jeremy.datum.*;
import com.jeremy.datum.state.states.*;
import com.jeremy.datum.world.*;

public class Camera
{
    public static int fx;
    public static int fy;
    public static int x;
    public static int y;
    public static int drag;
    
    static {
        Camera.drag = 30;
    }
    
    public static void tick() {
        Camera.fx = (int)GameState.player.getX();
        Camera.fy = (int)GameState.player.getY();
        final int wantX = Camera.fx - Main.width / 2;
        final int wantY = Camera.fy - Main.height / 2;
        if (Camera.x != wantX) {
            final int stepX = (wantX - Camera.x) / Camera.drag;
            Camera.x += stepX;
        }
        if (Camera.y != wantY) {
            final int stepY = (wantY - Camera.y) / Camera.drag;
            Camera.y += stepY;
        }
        final int maxX = World.width * World.tileSize - Main.width;
        final int maxY = World.height * World.tileSize - Main.height;
        Camera.x = Math.min(maxX, Math.max(0, Camera.x));
        Camera.y = Math.min(maxY, Math.max(0, Camera.y));
    }
}
