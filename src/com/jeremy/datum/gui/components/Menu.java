package com.jeremy.datum.gui.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.jeremy.datum.GameEvent;
import com.jeremy.datum.Main;
import com.jeremy.datum.gui.Component;

public class Menu extends Component {
	private Font font;
	private String[] texts;
	private GameEvent[] events;
	private Component[] components;

	public Menu(final int x, final int y, final int width, final int size, final String[] texts, final GameEvent[] events) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.texts = texts;
		this.events = events;
		this.components = new Component[texts.length];
		this.font = Main.font.deriveFont((float) size);
		final FontMetrics metrics = Main.window.getFontMetrics(this.font);
		final int bHeight = metrics.getHeight();
		for (int i = 0; i < this.components.length; ++i) {
			final int bWidth = metrics.stringWidth(texts[i]);
			this.components[i] = new Button(x + width / 2 - bWidth / 2, y + bHeight * i, texts[i], size, this.font);
			((Button) this.components[i]).setEvent(events[i]);
		}
	}

	public Menu(final int x, int y, final int width, final Component[] components) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.components = components;
		for (int i = 0; i < components.length; ++i) {
			final Component component = components[i];
			component.setX(x + width / 2 - component.getWidth() / 2);
			component.setY(y);
			y += component.getHeight();
		}
	}

	@Override
	public void tick() {
		Component[] components;
		for (int length = (components = this.components).length, i = 0; i < length; ++i) {
			final Component button = components[i];
			button.tick();
		}
	}

	@Override
	public void render(final Graphics2D g) {
		Component[] components;
		for (int length = (components = this.components).length, i = 0; i < length; ++i) {
			final Component button = components[i];
			button.render(g);
		}
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	public Font getFont() {
		return this.font;
	}

	public String[] getTexts() {
		return this.texts;
	}

	public GameEvent[] getEvents() {
		return this.events;
	}

	public Component[] getButtons() {
		return this.components;
	}
}
