package com.jeremy.datum.entity.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.jeremy.datum.entity.Entity;
import com.jeremy.datum.entity.physics.Collision;
import com.jeremy.datum.entity.physics.Event;
import com.jeremy.datum.entity.physics.Movement;
import com.jeremy.datum.graphics.Camera;
import com.jeremy.datum.gui.page.pages.SettingsPage;
import com.jeremy.datum.input.KeyInput;
import com.jeremy.datum.world.World;

public class Player extends Entity {

	public static float maxSpeed;
	public static float acceleration;
	public static float deceleration;
	static {
		Player.maxSpeed = 8.0f;
		Player.acceleration = 0.5f;
		Player.deceleration = 0.5f;
	}
	private Rectangle collisionBoxX;
	private Rectangle collisionBoxY;

	private boolean control;

	public Player() {
		width = 30;
		height = 30;
		x = (float) World.tileSize + 5;
		y = (float) World.tileSize + 5;
		collisionBoxX = new Rectangle((int) x, (int) y, width, height);
		collisionBoxY = new Rectangle((int) x, (int) y, width, height);
		control = true;
	}

	public Rectangle getCollisionBoxX() {
		return collisionBoxX;
	}

	public Rectangle getCollisionBoxY() {
		return collisionBoxY;
	}

	public boolean hasControl() {
		return control;
	}

	@Override
	public void render(final Graphics2D g) {
		final int x = (int) (this.x - Camera.x);
		final int y = (int) (this.y - Camera.y);
		g.drawImage(SettingsPage.getSkin(), x, y, width, height, null);
	}

	public void setControl(final boolean control) {
		this.control = control;
	}

	@Override
	public void tick() {
		if (control) {
			Movement.xMove(this);
			Movement.yMove(this);
		}
		collisionBoxX.setRect(x + xMove, y, width, height);
		collisionBoxY.setRect(x, y + yMove, width, height);
		if (!KeyInput.isPressed('G')) {
			Collision.xCollision(this);
			Collision.yCollision(this);
		}

		Event.check(this);
		y += yMove;
		x += xMove;
	}

	public void toSpawn() {
		setX((float) World.tileSize + 5);
		setY((float) World.tileSize + 5);
	}

}
