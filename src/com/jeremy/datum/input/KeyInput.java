package com.jeremy.datum.input;

import java.awt.event.*;

public class KeyInput extends KeyAdapter {
	
	public static final int W = 87;
	public static final int A = 65;
	public static final int S = 83;
	public static final int D = 68;
	public static final int UP = 38;
	public static final int DOWN = 40;
	public static final int LEFT = 37;
	public static final int RIGHT = 39;
	public static final int SPACE = 32;
	public static final int SHIFT = 16;
	public static final int CTRL = 17;
	public static final int ALT = 18;
	public static final int DELTE = 127;
	public static final int ESCAPE = 27;
	public static boolean[] keys;

	static {
		KeyInput.keys = new boolean[512];
	}

	@Override
	public void keyPressed(final KeyEvent event) {
		KeyInput.keys[event.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(final KeyEvent event) {
		KeyInput.keys[event.getKeyCode()] = false;
	}

	public static boolean isPressed(final int keyCode) {
		return KeyInput.keys[keyCode];
	}

	public static boolean isPressed(final int... keyCodes) {
		for (final int keyCode : keyCodes) {
			if (KeyInput.keys[keyCode]) {
				return true;
			}
		}
		return false;
	}
}
