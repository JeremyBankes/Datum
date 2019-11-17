package com.jeremy.datum.gui.page.pages;

import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import com.jeremy.datum.GameEvent;
import com.jeremy.datum.Main;
import com.jeremy.datum.graphics.Overlay;
import com.jeremy.datum.gui.components.Menu;
import com.jeremy.datum.gui.page.Page;
import com.jeremy.datum.tools.JAssets;

public class HelpPage extends Page {
	private Menu menu;

	public HelpPage() {
		final GameEvent[] events = { new GameEvent() {
			@Override
			public void callEvent() {
				System.out.println("Navigating to menu page");
				Overlay.flood(0.25f);
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						Page.page = Page.menuPage;
						Overlay.drain(0.25f);
					}
				}, 250L);
			}
		} };
		this.menu = new Menu(0, 600, Main.width, 32, new String[] { "Back" }, events);
	}

	@Override
	public void tick() {
		this.menu.tick();
	}

	@Override
	public void render(final Graphics2D g) {
		g.drawImage(JAssets.BACKGROUNDS[2], 0, 0, null);
		this.menu.render(g);
	}
}
