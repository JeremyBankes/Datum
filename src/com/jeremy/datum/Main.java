package com.jeremy.datum;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

import com.jeremy.datum.graphics.Overlay;
import com.jeremy.datum.graphics.window.Window;
import com.jeremy.datum.gui.page.pages.SettingsPage;
import com.jeremy.datum.input.KeyInput;
import com.jeremy.datum.input.MouseInput;
import com.jeremy.datum.state.State;
import com.jeremy.datum.tools.JAssets;
import com.jeremy.datum.tools.JAudio;

public class Main implements Runnable {

	public static final String NAME = "Datum";
	public static final Color COLOR = new Color(255, 255, 255);
	public static Thread thread;
	public static boolean running;
	public static Window window;
	public static int width;
	public static int height;
	public static int FPS;
	public static int TPS;
	public static Font font;

	public static void main(final String[] args) {
		new Main();
	}

	public static void start() {
		if (!Main.running) {
			Main.running = true;
			Main.thread.start();
		}
		System.out.println("Datum has successfully initiated.");
	}

	public static void stop() {
		System.out.println("Datum has successfully terminated.");
		System.exit(0);
	}

	private BufferStrategy bufferStrategy;

	private Graphics2D g;

	private int tps;

	private int fps;

	public Main() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/Pixel-Noir.ttf")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Main.width = 1080;
		Main.height = Main.width * 2 / 3;
		Main.TPS = 60;
		Main.FPS = 6000;
		Main.font = new Font("Pixel-Noir Regular", 0, 16);
		Main.window = new Window("Datum", Main.width, Main.height);
		Main.window.getCanvas().addMouseListener(new MouseInput());
		Main.window.getCanvas().addMouseMotionListener(new MouseInput());
		Main.window.getCanvas().addMouseWheelListener(new MouseInput());
		Main.window.getCanvas().addKeyListener(new KeyInput());

		Main.thread = new Thread(this);
		start();
	}

	public Font getFont() {
		return Main.font;
	}

	public int getFps() {
		return fps;
	}

	public int getFPS() {
		return Main.FPS;
	}

	public int getHeight() {
		return Main.height;
	}

	public int getTps() {
		return tps;
	}

	public int getTPS() {
		return Main.TPS;
	}

	public int getWidth() {
		return Main.width;
	}

	public Window getWindow() {
		return Main.window;
	}

	private void init() {
		JAssets.loadTextures();
		Overlay.flood();
		State.menuState.enter();
		JAudio.playTheme();
	}

	private void render() {
		bufferStrategy = Main.window.getCanvas().getBufferStrategy();
		if (bufferStrategy == null) {
			Main.window.getCanvas().createBufferStrategy(3);
			return;
		}
		(g = (Graphics2D) bufferStrategy.getDrawGraphics()).setColor(Main.COLOR);
		g.fillRect(0, 0, Main.width, Main.height);
		if (State.state != null) {
			State.state.render(g);
		}
		Overlay.render(g);
		bufferStrategy.show();
		g.dispose();
	}

	@Override
	public void run() {
		init();
		final long secondAsNano = 1000000000L;
		final long startTime = System.nanoTime();
		final long frameAsNano = secondAsNano / Math.max(1, Main.FPS);
		final long tickAsNano = secondAsNano / Math.max(1, Main.TPS);
		long secondsPassed = 0L;
		long framesPassed = 0L;
		long ticksPassed = 0L;
		while (Main.running) {
			final long currentTime = System.nanoTime();
			if (currentTime - startTime > tickAsNano * ticksPassed) {
				++ticksPassed;
				++tps;
				tick();
			}
			if (currentTime - startTime > frameAsNano * framesPassed) {
				++framesPassed;
				++fps;
				render();
			}
			if (currentTime - startTime > secondAsNano * secondsPassed) {
				++secondsPassed;
				System.out.println("TPS: " + tps + " || FPS: " + fps);
				tps = 0;
				fps = 0;
			}
		}
		Main.window.dispose();
	}

	private void tick() {
		Overlay.tick();
		if (State.state != null) {
			State.state.tick();
			MouseInput.tick();
			JAudio.setVolume(-40.0f + SettingsPage.volumeSlider.getValue() / 100.0f * 40.0f);
		}
		if (KeyInput.isPressed(17)) {
			Overlay.titleFlood("Hello\nWorld!", 0.25f);
		} else if (KeyInput.isPressed(18)) {
			Overlay.titleDrain(0.25f);
		}
	}
}
