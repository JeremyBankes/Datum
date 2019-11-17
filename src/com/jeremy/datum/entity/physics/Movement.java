package com.jeremy.datum.entity.physics;

import com.jeremy.datum.entity.entities.*;
import com.jeremy.datum.input.*;

public class Movement
{
    public static void yMove(final Player player) {
        if (KeyInput.isPressed(87, 38)) {
            player.setyMove(Math.max(-Player.maxSpeed, player.getyMove() - Player.acceleration));
        }
        else if (player.getyMove() < 0.0f) {
            player.setyMove(Math.min(0.0f, player.getyMove() + Player.deceleration));
        }
        if (KeyInput.isPressed(83, 40)) {
            player.setyMove(Math.min(Player.maxSpeed, player.getyMove() + Player.acceleration));
        }
        else if (player.getyMove() > 0.0f) {
            player.setyMove(Math.max(0.0f, player.getyMove() - Player.deceleration));
        }
    }
    
    public static void xMove(final Player player) {
        if (KeyInput.isPressed(68, 39)) {
            player.setxMove(Math.min(Player.maxSpeed, player.getxMove() + Player.acceleration));
        }
        else if (player.getxMove() > 0.0f) {
            player.setxMove(Math.max(0.0f, player.getxMove() - Player.deceleration));
        }
        if (KeyInput.isPressed(65, 37)) {
            player.setxMove(Math.max(-Player.maxSpeed, player.getxMove() - Player.acceleration));
        }
        else if (player.getxMove() < 0.0f) {
            player.setxMove(Math.min(0.0f, player.getxMove() + Player.deceleration));
        }
    }
}
