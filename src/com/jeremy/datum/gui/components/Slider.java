package com.jeremy.datum.gui.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.jeremy.datum.Main;
import com.jeremy.datum.gui.Component;
import com.jeremy.datum.input.MouseInput;
import com.jeremy.datum.tools.JColor;

public class Slider extends Component {

	private String key;
	private Font font;
	private int min;
	private int max;
	private int value;
	private String text;
	private Rectangle bar;
	private int barOffset;
	private int startBarOffset;

	public Slider(final String key, final int x, final int y, final int width, final int height, final int min, final int max, final int startValue) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.min = min;
		this.max = max;
		this.font = Main.font;
		this.value = startValue;
		this.key = key;
		this.text = String.valueOf(key) + ": " + this.value;
		this.bar = new Rectangle();
		this.barOffset = (int) ((width - 10) * ((this.value - min) / (float) (max - min)));
	}

	@Override
	public void tick() {
		this.text = String.valueOf(this.key) + ": " + this.value;
		final int mx = MouseInput.x;
		final int my = MouseInput.y;
		final int sx = MouseInput.lx;
		final int sy = MouseInput.ly;
		if (new Rectangle(this.x, this.y, this.width, this.height).contains(mx, my) && MouseInput.wheel != 0) {
			this.value = Math.max(this.min, Math.min(this.max, this.value - MouseInput.wheel));
			this.barOffset = (int) ((this.width - 10) * ((this.value - this.min) / (float) (this.max - this.min)));
		}
		if (new Rectangle(this.x, this.y, this.width, this.height).contains(sx, sy) && MouseInput.leftPressed) {
			this.barOffset = Math.max(0, Math.min(this.width - 10, this.startBarOffset + (mx - sx)));
			this.value = (int) (this.min + (this.max - this.min) * (this.barOffset / (float) (this.width - 10)));
		} else {
			this.startBarOffset = this.barOffset;
		}
		this.bar.setRect(this.x + this.barOffset, this.y, 10.0, this.height);
	}

	@Override
	public void render(final Graphics2D g) {
		g.setColor(JColor.BLACK_50);
		g.fillRect(this.x, this.y, this.width, this.height);
		g.setColor(JColor.HAPPY_GREEN);
		g.fill(this.bar);
		g.setColor(JColor.DARK_GREEN);
		g.draw(this.bar);
		g.setFont(this.font);
		g.setColor(JColor.WHITE);
		final FontMetrics metrics = g.getFontMetrics(this.font);
		final int textWidth = metrics.stringWidth(this.text);
		final int textHeight = metrics.getHeight();
		g.drawString(this.text, this.x + this.width / 2 - textWidth / 2, this.y + this.height / 2 + textHeight / 3);
	}

	public String getKey() {
		return this.key;
	}

	public int getValue() {
		return this.value;
	}
}
