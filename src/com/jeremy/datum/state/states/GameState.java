package com.jeremy.datum.state.states;

import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import com.jeremy.datum.Main;
import com.jeremy.datum.entity.entities.Player;
import com.jeremy.datum.graphics.Camera;
import com.jeremy.datum.graphics.Overlay;
import com.jeremy.datum.gui.Component;
import com.jeremy.datum.gui.components.Menu;
import com.jeremy.datum.gui.page.Page;
import com.jeremy.datum.gui.page.pages.SettingsPage;
import com.jeremy.datum.input.KeyInput;
import com.jeremy.datum.state.State;
import com.jeremy.datum.world.World;

public class GameState extends State {
	public static Player player;
	private static boolean escapePressed;

	public GameState() {
		GameState.player = new Player();
		World.entities.add(GameState.player);
		World.bindTileEvents();
	}

	@Override
	public void tick() {
		if (World.map != null) {
			Camera.tick();
			World.tick();
		}
		if (KeyInput.isPressed(27)) {
			if (!GameState.escapePressed) {
				Overlay.flood(0.25f);
				Page.page = Page.settingsPage;
				SettingsPage.components = new Component[] { SettingsPage.mapSizeSlider, SettingsPage.volumeSlider, SettingsPage.returnMainButton,
						SettingsPage.newMapButton, SettingsPage.returnButton };
				SettingsPage.menu = new Menu(0, 200, Main.width, SettingsPage.components);
				SettingsPage.comingFrom = State.gameState;
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						State.state = State.menuState;
						Overlay.drain(0.25f);
					}
				}, 250L);
				GameState.escapePressed = true;
			}
		} else {
			GameState.escapePressed = false;
		}
	}

	@Override
	public void render(final Graphics2D g) {
		if (World.map != null) {
			World.render(g);
		}
	}

	@Override
	public void enter() {
		if (World.map == null) {
			World.generateMap(SettingsPage.getMapSize(), SettingsPage.getMapSize());
		}
		State.state = this;
		Overlay.drain(0.25f);
	}
}
