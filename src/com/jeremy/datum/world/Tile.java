package com.jeremy.datum.world;

import java.awt.Graphics2D;

import com.jeremy.datum.graphics.Camera;

public class Tile {

	public static final int EVENT_ENTER = 0;
	public static final int EVENT_LEAVE = 1;
	public static final int EVENT_USE = 2;
	private int x;
	private int y;
	private TileType type;

	public Tile(final TileType type, final int x, final int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public Tile(final char symbol, final int x, final int y) {
		this.type = this.recognizeSymbol(symbol);
		this.x = x;
		this.y = y;
	}

	public void tick() {

	}

	public void render(final Graphics2D g) {
		g.drawImage(type.getTexture(), this.x - Camera.x, this.y - Camera.y, World.tileSize + 1, World.tileSize + 1, null);
	}

	private TileType recognizeSymbol(final char symbol) {
		TileType[] values;
		for (int length = (values = TileType.values()).length, i = 0; i < length; ++i) {
			final TileType type = values[i];
			if (type.getSymbol() == symbol) {
				return type;
			}
		}
		return TileType.AIR;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean isSolid() {
		return this.type.isSolid();
	}

	public TileType getType() {
		return this.type;
	}

}
