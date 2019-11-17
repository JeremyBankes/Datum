package com.jeremy.datum.gui.page.pages;

import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import com.jeremy.datum.GameEvent;
import com.jeremy.datum.Main;
import com.jeremy.datum.graphics.Overlay;
import com.jeremy.datum.gui.components.Menu;
import com.jeremy.datum.gui.page.Page;
import com.jeremy.datum.state.State;
import com.jeremy.datum.tools.JAssets;

public class MenuPage extends Page {
	private Menu menu;
	private String[] texts;
	private GameEvent[] events;

	public MenuPage() {
		this.texts = new String[] { "Play", "Settings", "Help", "Exit" };
		this.events = new GameEvent[] { new GameEvent() {
			@Override
			public void callEvent() {
				System.out.println("Navigating to game state");
				Overlay.flood(0.25f);
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						State.gameState.enter();
					}
				}, 250L);
			}
		}, new GameEvent() {
			@Override
			public void callEvent() {
				System.out.println("Navigating to menu state");
				System.out.println("Navigating to settings page");
				Overlay.flood(0.25f);
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						Page.page = Page.settingsPage;
						SettingsPage.comingFrom = State.menuState;
						Overlay.drain(0.25f);
					}
				}, 250L);
			}
		}, new GameEvent() {
			@Override
			public void callEvent() {
				System.out.println("Navigating to help page");
				Overlay.flood(0.25f);
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						Page.page = Page.helpPage;
						Overlay.drain(0.25f);
					}
				}, 250L);
			}
		}, new GameEvent() {
			@Override
			public void callEvent() {
				Main.stop();
			}
		} };
		this.menu = new Menu(0, Main.height / 3, Main.width, Main.width / 24, this.texts, this.events);
	}

	@Override
	public void tick() {
		this.menu.tick();
	}

	@Override
	public void render(final Graphics2D g) {
		g.drawImage(JAssets.BACKGROUNDS[0], 0, 0, null);
		this.menu.render(g);
	}
}
