package com.jeremy.datum.tools;

import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;

public class JImage {
	public static BufferedImage grab(final String path) {
		try {
			return ImageIO.read(JImage.class.getResourceAsStream("/" + path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage grab(final String path, final String toLocation) {
		try {
			return ImageIO.read(JFile.grab(path, toLocation));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage resize(BufferedImage image, final int width, final int height) {
		if (image.getWidth() != width && image.getHeight() != height) {
			final int type = (image.getTransparency() == 1) ? 1 : 2;
			BufferedImage newImage = image;
			final BufferedImage temp = new BufferedImage(width, height, type);
			final Graphics2D g = temp.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			g.drawImage(newImage, 0, 0, width, height, null);
			g.dispose();
			newImage = (image = temp);
		}
		return image;
	}
}
