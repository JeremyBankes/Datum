package com.jeremy.datum.input;

import java.awt.event.*;

public class MouseInput extends MouseAdapter {
	public static boolean leftPressed;
	public static boolean rightPressed;
	public static int x;
	public static int y;
	public static int lx;
	public static int ly;
	public static int rx;
	public static int ry;
	public static int wheel;

	@Override
	public void mousePressed(final MouseEvent event) {
		if (event.getButton() == 1) {
			MouseInput.leftPressed = true;
			MouseInput.lx = MouseInput.x;
			MouseInput.ly = MouseInput.y;
		} else if (event.getButton() == 2) {
			MouseInput.rightPressed = true;
			MouseInput.rx = MouseInput.x;
			MouseInput.rx = MouseInput.y;
		}
	}

	@Override
	public void mouseReleased(final MouseEvent event) {
		if (event.getButton() == 1) {
			MouseInput.leftPressed = false;
		} else if (event.getButton() == 2) {
			MouseInput.rightPressed = false;
		}
	}

	@Override
	public void mouseMoved(final MouseEvent event) {
		MouseInput.x = event.getX();
		MouseInput.y = event.getY();
	}

	@Override
	public void mouseDragged(final MouseEvent event) {
		MouseInput.x = event.getX();
		MouseInput.y = event.getY();
	}

	@Override
	public void mouseWheelMoved(final MouseWheelEvent event) {
		MouseInput.wheel = event.getWheelRotation();
	}

	public static void tick() {
		MouseInput.wheel = 0;
	}
}
