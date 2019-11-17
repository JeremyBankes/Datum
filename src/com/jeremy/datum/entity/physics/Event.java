package com.jeremy.datum.entity.physics;

import com.jeremy.datum.entity.entities.*;
import com.jeremy.datum.world.*;

public class Event
{
    private static boolean executedEnterEvent;
    
    static {
        Event.executedEnterEvent = false;
    }
    
    public static void check(final Player player) {
        final int width = player.getWidth();
        final int height = player.getHeight();
        final int x = (int)player.getX();
        final int y = (int)player.getY();
        final Tile[] tiles = { World.getTile(x / World.tileSize, y / World.tileSize), World.getTile((x + width) / World.tileSize, y / World.tileSize), World.getTile(x / World.tileSize, (y + height) / World.tileSize), World.getTile((x + width) / World.tileSize, (y + height) / World.tileSize) };
        boolean isEnterEvent = false;
        Tile[] array;
        for (int length = (array = tiles).length, i = 0; i < length; ++i) {
            final Tile tile = array[i];
            if (tile.getType().getEnterEvent() != null) {
                isEnterEvent = true;
                if (!Event.executedEnterEvent) {
                    tile.getType().getEnterEvent().callEvent();
                    Event.executedEnterEvent = true;
                }
            }
        }
        if (!isEnterEvent) {
            Event.executedEnterEvent = false;
        }
    }
}
