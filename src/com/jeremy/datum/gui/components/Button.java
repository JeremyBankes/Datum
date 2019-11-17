package com.jeremy.datum.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.jeremy.datum.GameEvent;
import com.jeremy.datum.Main;
import com.jeremy.datum.gui.Component;
import com.jeremy.datum.input.MouseInput;
import com.jeremy.datum.tools.JColor;

public class Button extends Component {
	private GameEvent event;
	private Font font;
	private String text;
	private boolean hovering;
	private boolean clicking;
	private Color colorNormal;
	private Color colorHover;
	private Color colorClick;
	private Color color;

	public Button(final int x, final int y, final String text, final int size, final Font font) {
		this.hovering = false;
		this.clicking = false;
		this.colorNormal = JColor.WHITE;
		this.colorHover = JColor.DARK_GREEN;
		this.colorClick = JColor.HAPPY_GREEN;
		this.color = this.colorNormal;
		this.x = x;
		this.y = y;
		this.text = text;
		this.font = font.deriveFont((float) size);
		final FontMetrics metrics = Main.window.getFontMetrics(this.font);
		this.width = metrics.stringWidth(text);
		this.height = metrics.getHeight();
	}

	@Override
	public void tick() {
		final int mx = MouseInput.x;
		final int my = MouseInput.y;
		final int sx = MouseInput.lx;
		final int sy = MouseInput.ly;
		if (this.x < mx && this.x + this.width > mx && this.y < my && this.y + this.height > my) {
			this.hovering = true;
		} else if (this.hovering) {
			this.hovering = false;
		}
		if (this.x < sx && this.x + this.width > sx && this.y < sy && this.y + this.height > sy && MouseInput.leftPressed) {
			this.clicking = true;
		} else if (this.clicking) {
			this.clicking = false;
			if (this.event != null) {
				this.event.callEvent();
			}
		}
		if (this.clicking) {
			this.toColor(this.color, this.colorClick, 8);
		} else if (this.hovering) {
			this.toColor(this.color, this.colorHover, 8);
		} else {
			this.toColor(this.color, this.colorNormal, 8);
		}
	}

	@Override
	public void render(final Graphics2D g) {
		g.setFont(this.font);
		g.setColor(this.color);
		g.drawString(this.text, this.x, this.y + this.height);
	}

	private void toColor(final Color currentColor, final Color toColor, final int step) {
		int cRed = currentColor.getRed();
		int cGreen = currentColor.getGreen();
		int cBlue = currentColor.getBlue();
		final int toRed = toColor.getRed();
		final int toGreen = toColor.getGreen();
		final int toBlue = toColor.getBlue();

		final int redStep = (toRed - cRed != 0) ? ((toRed - cRed) / step) : 0;
		final int greenStep = (toGreen - cGreen != 0) ? ((toGreen - cGreen) / step) : 0;
		final int blueStep = (toBlue - cBlue != 0) ? ((toBlue - cBlue) / step) : 0;

		cRed = Math.min(255, Math.max(0, redStep + cRed));
		cGreen = Math.min(255, Math.max(0, greenStep + cGreen));
		cBlue = Math.min(255, Math.max(0, blueStep + cBlue));
		this.color = new Color(cRed, cGreen, cBlue);
	}

	public GameEvent getEvent() {
		return this.event;
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

	@Override
	public int getHeight() {
		return this.height;
	}

	public Font getFont() {
		return this.font;
	}

	public String getText() {
		return this.text;
	}

	public boolean isHovering() {
		return this.hovering;
	}

	public boolean isClicking() {
		return this.clicking;
	}

	public Color getColorNormal() {
		return this.colorNormal;
	}

	public Color getColorHover() {
		return this.colorHover;
	}

	public Color getColorClick() {
		return this.colorClick;
	}

	public Color getColor() {
		return this.color;
	}

	public void setEvent(final GameEvent event) {
		this.event = event;
	}
}
