package com.jeremy.datum.gui.page;

import java.awt.*;

import com.jeremy.datum.gui.page.pages.*;

public abstract class Page {
	public static Page page;
	public static Page menuPage;
	public static Page settingsPage;
	public static Page helpPage;

	static {
		Page.menuPage = new MenuPage();
		Page.settingsPage = new SettingsPage();
		Page.helpPage = new HelpPage();
	}

	public abstract void tick();

	public abstract void render(final Graphics2D p0);
}
