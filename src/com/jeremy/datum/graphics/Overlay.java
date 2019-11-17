package com.jeremy.datum.graphics;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.jeremy.datum.Main;

public class Overlay {

	public static float alpha;
	public static float alphaTitle;
	public static float alphaStep;
	public static float alphaStepTitle;
	public static boolean drain;
	public static boolean drainTitle;
	public static String title;

	public static void tick() {
		if (Overlay.drain) {
			if (Overlay.alpha > 0.0f) {
				Overlay.alpha = Math.max(0.0f, Overlay.alpha - Overlay.alphaStep);
			}
		} else if (Overlay.alpha < 1.0f) {
			Overlay.alpha = Math.min(1.0f, Overlay.alpha + Overlay.alphaStep);
		}
		if (Overlay.drainTitle) {
			if (Overlay.alphaTitle > 0.0f) {
				Overlay.alphaTitle = Math.max(0.0f, Overlay.alphaTitle - Overlay.alphaStepTitle);
			}
		} else if (Overlay.alphaTitle < 1.0f) {
			Overlay.alphaTitle = Math.min(1.0f, Overlay.alphaTitle + Overlay.alphaStepTitle);
		}
	}

	public static void render(final Graphics2D g) {

		if (alpha > 0) {
			g.setColor(new Color(0f, 0f, 0f, alpha));
			g.fillRect(0, 0, Main.width, Main.height);
		}

		if (title != null) {
			g.setFont(Main.font.deriveFont(32.0f));
			g.setColor(new Color(1.0f, 1.0f, 1.0f, alphaTitle));
			FontMetrics metrics = g.getFontMetrics();

			String[] words = title.split(" ");
			StringBuffer display = new StringBuffer();
			StringBuffer line = new StringBuffer();
			for (String word : words) {
				line.append(' ');
				line.append(word);
				if (metrics.stringWidth(line.toString()) > Main.width) {
					line.setLength(line.length() - word.length());
					line.append('\n');
					display.append(line.toString());
					line.setLength(0);
					line.append(word);
				}
			}
			display.append(line);

			String[] split = display.toString().split("\n");
			int startHeight = Main.height / 2 - split.length * metrics.getHeight() / 2 + metrics.getHeight() / 2;
			for (int i = 0; i < split.length; i++) {
				String text = split[i];
				int stringWidth = metrics.stringWidth(text);
				g.drawString(text, Main.width / 2 - stringWidth / 2, startHeight + i * metrics.getHeight());
			}
		}
	}

	public static void flood() {
		Overlay.drain = false;
		Overlay.alpha = 1.0f;
	}

	public static void flood(final float delay) {
		Overlay.alphaStep = 1.0f / (delay * Main.TPS);
		Overlay.drain = false;
	}

	public static void drain(final float delay) {
		Overlay.alphaStep = 1.0f / (delay * Main.TPS);
		Overlay.drain = true;
	}

	public static void titleFlood(final String title, final float delay) {
		Overlay.title = title;
		Overlay.alphaStepTitle = 1.0f / (delay * Main.TPS);
		Overlay.drainTitle = false;
	}

	public static void titleDrain(final float delay) {
		Overlay.alphaStepTitle = 1.0f / (delay * Main.TPS);
		Overlay.drainTitle = true;
	}
}
