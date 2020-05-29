package com.jeremy.datum.entity.physics;

import java.awt.Rectangle;

import com.jeremy.datum.entity.entities.Player;
import com.jeremy.datum.world.Tile;
import com.jeremy.datum.world.World;

public class Collision {
	
	public static void xCollision(final Player player) {
		final int tileSize = World.tileSize;
		final Rectangle collisionBox = player.getCollisionBoxX();
		final float xMove = player.getxMove();
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				final int x = j + (int) ((player.getX() + player.getWidth() / 2) / tileSize);
				final int y = i + (int) ((player.getY() + player.getHeight() / 2) / tileSize);
				if (x >= 0 && y >= 0 && World.map.length > y && World.map[y].length > x) {
					final Tile tile = World.map[y][x];
					if (tile != null && tile.isSolid()) {
						final Rectangle tileCollisionBox = new Rectangle(tile.getX(), tile.getY(), tileSize, tileSize);
						if (collisionBox.intersects(tileCollisionBox)) {
							if (xMove < 0.0f) {
								player.setxMove(tile.getX() + tileSize - player.getX());
							} else if (xMove > 0.0f) {
								player.setxMove(tile.getX() - (player.getX() + player.getWidth()));
							}
						}
					}
				}
			}
		}
	}

	public static void yCollision(final Player player) {
		final int tileSize = World.tileSize;
		final Rectangle collisionBox = player.getCollisionBoxY();
		final float yMove = player.getyMove();
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				final int x = j + (int) ((player.getX() + player.getWidth() / 2) / tileSize);
				final int y = i + (int) ((player.getY() + player.getHeight() / 2) / tileSize);
				if (x >= 0 && y >= 0 && World.map.length > y && World.map[y].length > x) {
					final Tile tile = World.map[y][x];
					if (tile != null && tile.isSolid()) {
						final Rectangle tileCollisionBox = new Rectangle(tile.getX(), tile.getY(), tileSize, tileSize);
						if (collisionBox.intersects(tileCollisionBox)) {
							if (yMove < 0.0f) {
								player.setyMove(tile.getY() + tileSize - player.getY());
							} else if (yMove > 0.0f) {
								player.setyMove(tile.getY() - (player.getY() + player.getHeight()));
							}
						}
					}
				}
			}
		}
	}
}
