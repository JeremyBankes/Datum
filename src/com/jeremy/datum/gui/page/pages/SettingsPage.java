package com.jeremy.datum.gui.page.pages;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import com.jeremy.datum.Main;
import com.jeremy.datum.graphics.Overlay;
import com.jeremy.datum.gui.Component;
import com.jeremy.datum.gui.components.Button;
import com.jeremy.datum.gui.components.Menu;
import com.jeremy.datum.gui.components.Slider;
import com.jeremy.datum.gui.page.Page;
import com.jeremy.datum.state.State;
import com.jeremy.datum.state.states.GameState;
import com.jeremy.datum.tools.JAssets;
import com.jeremy.datum.tools.JColor;
import com.jeremy.datum.tools.JImage;
import com.jeremy.datum.world.World;

public class SettingsPage extends Page {
	public static Component[] components;
	public static State comingFrom;
	public static Menu menu;
	public static Slider mapSizeSlider;
	public static Slider volumeSlider;
	public static Button returnButton;
	public static Button newMapButton;
	public static Button returnMainButton;

	public static Button skin1Button;
	public static Button skin2Button;
	public static Button skin3Button;

	public static int skin = 0;
	public static BufferedImage[] skins;

	public SettingsPage() {
		this.createComponents();
		SettingsPage.components = new Component[] { SettingsPage.mapSizeSlider, SettingsPage.volumeSlider, SettingsPage.newMapButton,
				SettingsPage.returnButton };
		SettingsPage.menu = new Menu(0, 200, Main.width, SettingsPage.components);

		skins = new BufferedImage[] { JImage.grab("skin1.png"), JImage.grab("skin2.png"), JImage.grab("skin3.png") };
	}

	private void createComponents() {
		SettingsPage.mapSizeSlider = new Slider("Map Size", 0, 0, 300, 50, 30, 100, 30);
		SettingsPage.volumeSlider = new Slider("Volume", 0, 0, 300, 50, 0, 100, 50);

		(SettingsPage.returnButton = new Button(0, 0, "Back", 32, Main.font)).setEvent(() -> {
			Overlay.flood(0.25f);
			System.out.println("Navigating to menu page");
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					Page.page = Page.menuPage;
					if (SettingsPage.comingFrom instanceof GameState) {
						State.state = State.gameState;
					}
					Overlay.drain(0.25f);
				}
			}, 250L);
		});

		(SettingsPage.newMapButton = new Button(0, 0, "New World", 32, Main.font)).setEvent(() -> {
			Overlay.flood(0.25f);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					State.state = State.gameState;
					World.generateMap(SettingsPage.getMapSize(), SettingsPage.getMapSize());
					GameState.player.toSpawn();
					Overlay.drain(1.0f);
				}
			}, 250L);
		});

		(SettingsPage.returnMainButton = new Button(0, 0, "Back to Menu", 32, Main.font)).setEvent(() -> {
			Overlay.flood(0.25f);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					State.state = State.menuState;
					SettingsPage.components = new Component[] { SettingsPage.mapSizeSlider, SettingsPage.volumeSlider, SettingsPage.newMapButton,
							SettingsPage.returnButton };
					SettingsPage.menu = new Menu(0, 200, Main.width, SettingsPage.components);
					Page.page = Page.menuPage;
					Overlay.drain(1.0f);
				}
			}, 250L);
		});

		(SettingsPage.skin1Button = new Button(Main.width / 2 - 30, 560, "", 32, Main.font) {

			@Override
			public void tick() {
				if (width != height) {
					width = height;
				}
				super.tick();
			}

			@Override
			public void render(Graphics2D g) {
				if (skin == 0) {
					g.setColor(JColor.BLACK_50);
					g.fillRect(x - 10, y - 10, width + 20, height + 20);
				}
				g.drawImage(skins[0], x, y, width, height, null);
			}
		}).setEvent(() -> {
			skin = 0;
		});

		(SettingsPage.skin2Button = new Button(Main.width / 2 - 30 - 60 - 30, 560, "", 32, Main.font) {

			@Override
			public void tick() {
				if (width != height) {
					width = height;
				}
				super.tick();
			}

			@Override
			public void render(Graphics2D g) {
				if (skin == 1) {
					g.setColor(JColor.BLACK_50);
					g.fillRect(x - 10, y - 10, width + 20, height + 20);
				}
				g.drawImage(skins[1], x, y, width, height, null);
			}
		}).setEvent(() -> {
			skin = 1;
		});

		(SettingsPage.skin3Button = new Button(Main.width / 2 - 30 + 60 + 30, 560, "", 32, Main.font) {

			@Override
			public void tick() {
				if (width != height) {
					width = height;
				}
				super.tick();
			}

			@Override
			public void render(Graphics2D g) {
				if (skin == 2) {
					g.setColor(JColor.BLACK_50);
					g.fillRect(x - 10, y - 10, width + 20, height + 20);
				}
				g.drawImage(skins[2], x, y, width, height, null);
			}
		}).setEvent(() -> {
			skin = 2;
		});
	}

	public static BufferedImage getSkin() {
		return skins[skin];
	}

	@Override
	public void tick() {
		SettingsPage.menu.tick();
		SettingsPage.skin1Button.tick();
		SettingsPage.skin2Button.tick();
		SettingsPage.skin3Button.tick();
	}

	@Override
	public void render(final Graphics2D g) {
		g.drawImage(JAssets.BACKGROUNDS[1], 0, 0, null);
		SettingsPage.menu.render(g);
		SettingsPage.skin1Button.render(g);
		SettingsPage.skin2Button.render(g);
		SettingsPage.skin3Button.render(g);
	}

	public static int getMapSize() {
		return SettingsPage.mapSizeSlider.getValue();
	}
}
