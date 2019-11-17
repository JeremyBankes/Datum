package com.jeremy.datum.entity;

import java.awt.*;

public abstract class Entity {
	protected float x;
	protected float y;
	protected float xMove;
	protected float yMove;
	protected int width;
	protected int height;

	public abstract void tick();

	public abstract void render(final Graphics2D p0);

	public float getX() {
		return this.x;
	}

	public void setX(final float x) {
		this.x = x;
	}

	public float getY() {
		return this.y;
	}

	public void setY(final float y) {
		this.y = y;
	}

	public float getxMove() {
		return this.xMove;
	}

	public void setxMove(final float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return this.yMove;
	}

	public void setyMove(final float yMove) {
		this.yMove = yMove;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}
}
