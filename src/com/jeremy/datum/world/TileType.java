package com.jeremy.datum.world;

import java.awt.image.BufferedImage;

import com.jeremy.datum.GameEvent;
import com.jeremy.datum.tools.JImage;

public enum TileType {
	
	AIR("AIR", 0, ' ', "air.png", false), //
	WALL("WALL", 1, '#', "wall.png", true), //
	WIN("WIN", 2, '%', "win.png", false);

	private final char symbol;
	private final BufferedImage texture;
	private final boolean solid;
	private GameEvent enterEvent;
	private GameEvent exitEvent;
	private GameEvent useEvent;

	private TileType(final String s, final int n, final char symbol, final String texture, final boolean solid) {
		this.symbol = symbol;
		this.texture = JImage.grab(texture);
		this.solid = solid;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public boolean isSolid() {
		return this.solid;
	}

	public GameEvent getEnterEvent() {
		return this.enterEvent;
	}

	public void setEnterEvent(final GameEvent enterEvent) {
		this.enterEvent = enterEvent;
	}

	public GameEvent getExitEvent() {
		return this.exitEvent;
	}

	public void setExitEvent(final GameEvent exitEvent) {
		this.exitEvent = exitEvent;
	}

	public GameEvent getUseEvent() {
		return this.useEvent;
	}

	public void setUseEvent(final GameEvent useEvent) {
		this.useEvent = useEvent;
	}

}
