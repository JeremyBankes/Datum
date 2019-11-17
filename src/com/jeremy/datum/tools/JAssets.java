package com.jeremy.datum.tools;

import java.awt.image.*;

import com.jeremy.datum.*;

public class JAssets {

	public static final int MENU = 0;
	public static final int SETTINGS = 1;
	public static final int HELP = 2;
	public static final BufferedImage[] BACKGROUNDS = new BufferedImage[32];

	public static void loadTextures() {
		JAssets.BACKGROUNDS[0] = JImage.resize(JImage.grab("gui/menu/background.png"), Main.width, Main.height);
		JAssets.BACKGROUNDS[1] = JImage.resize(JImage.grab("gui/menu/background.png"), Main.width, Main.height);
		JAssets.BACKGROUNDS[2] = JImage.resize(JImage.grab("gui/help/background.png"), Main.width, Main.height);
	}

}
