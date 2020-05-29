package com.jeremy.datum.world;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeremy.datum.Main;
import com.jeremy.datum.entity.Entity;
import com.jeremy.datum.graphics.Camera;
import com.jeremy.datum.graphics.Overlay;
import com.jeremy.datum.gui.page.pages.SettingsPage;
import com.jeremy.datum.state.states.GameState;

import jdk.nashorn.api.scripting.URLReader;

public class World {
	public static long timestamp;
	public static int tileSize;
	public static Tile[][] map;
	public static int width;
	public static int height;
	public static ArrayList<Entity> entities;

	static {
		World.tileSize = 40;
		World.entities = new ArrayList<Entity>();
	}

	public static void generateMap(final int mapWidth, final int mapHeight) {
		System.out.println("Generating Map");
		World.timestamp = System.currentTimeMillis() / 1000L;
		try {
			final String settings = "+" + mapWidth / 2 + "+" + mapHeight / 2 + "+2+2";
			final URL url = new URL("http://www.delorie.com/game-room/mazes/genmaze.cgi?html+text" + settings);
			final URLConnection connection = url.openConnection();
			final InputStream stream = connection.getInputStream();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = reader.readLine();
			final Tile[][] tempMap = new Tile[256][256];
			int y = 0;
			boolean read = false;
			while (line != null) {
				if (read && line.endsWith("</pre>")) {
					read = false;
				}
				if (read) {
					line = line.replaceAll("[^ ]|^.?", "#");
					line = line.replaceAll("  $", "%#");
					if (line.length() > World.width) {
						World.width = line.length();
					}
					for (int x = 0; x < line.length(); ++x) {
						tempMap[y][x] = new Tile(line.charAt(x), x * World.tileSize, y * World.tileSize);
						if (tempMap[y][x] == null) {
							System.err.println("Null tile at: " + x + ", " + y);
							System.err.println("Symbol: " + line.charAt(x) + "|");
						}
					}
					++y;
				}
				if (line.endsWith("<pre>")) {
					read = true;
				}
				line = reader.readLine();
			}
			reader.close();
			World.height = y;
			World.map = tempMap;
		} catch (IOException e) {
			System.out.println("Failed to connect to maze generator.");
			World.width = mapWidth;
			World.height = mapHeight;
			World.map = new Tile[256][256];
			for (int i = 0; i < mapHeight; i++) {
				for (int j = 0; j < mapWidth; j++) {
					TileType type = i == 0 || i == mapHeight - 1 || j == 0 || j == mapWidth - 1 ? TileType.WALL : TileType.AIR;

					if (type == TileType.AIR) {
						if (i != 1 || j != 1) {
							if (Math.random() < 0.25) {
								type = TileType.WALL;
							}
						}
					}

					World.map[i][j] = new Tile(type, j * World.tileSize, i * World.tileSize);
				}
			}
			World.map[mapHeight - 2][mapWidth - 2] = new Tile(TileType.WIN, (mapWidth - 2) * tileSize, (mapHeight - 2) * tileSize);
		}
		System.out.println("Map Successfully Generated");
	}

	public static void tick() {
		for (int y = 0; y < World.height; ++y) {
			for (int x = 0; x < World.width; ++x) {
				final boolean checkX = x * World.tileSize - Camera.x < Main.width && x * World.tileSize > -1;
				final boolean checkY = y * World.tileSize - Camera.y < Main.height && y * World.tileSize > -1;
				if (checkX && checkY && World.map[y][x] != null) {
					World.map[y][x].tick();
				}
			}
		}
		for (final Entity entity : World.entities) {
			entity.tick();
		}
	}

	public static void render(final Graphics2D g) {
		for (int y = 0; y < World.height; ++y) {
			for (int x = 0; x < World.width; ++x) {
				final boolean checkX = x * World.tileSize - Camera.x < Main.width && x * World.tileSize > -1;
				final boolean checkY = y * World.tileSize - Camera.y < Main.height && y * World.tileSize > -1;
				if (checkX && checkY) {
					World.map[y][x].render(g);
				}
			}
		}
		for (final Entity entity : World.entities) {
			entity.render(g);
		}
	}

	public static void bindTileEvents() {
		TileType.WIN.setEnterEvent(() -> {
			new Thread(() -> {
				try {
					Overlay.flood(0.25f);
					Thread.sleep(250);
					GameState.player.toSpawn();
					Overlay.titleFlood("Congratulations!\nYou beat the maze!", 0.25f);
					Thread.sleep(1500L);
					Overlay.titleDrain(0.25f);
					Thread.sleep(200L);
					long currentTimestamp = System.currentTimeMillis() / 1000L;
					Overlay.titleFlood("You took " + World.formatSeconds(currentTimestamp - World.timestamp), 0.25f);
					Thread.sleep(1500L);
					Overlay.titleDrain(0.25f);
					Thread.sleep(200L);
					String quote = getQuote();
					boolean gotQuote = quote != null;
					Overlay.titleFlood(gotQuote ? quote.toString() : "Making you a new maze!", 0.25f);
					World.generateMap(SettingsPage.getMapSize(), SettingsPage.getMapSize());
					Thread.sleep(gotQuote ? 5000L : 1000L);
					Overlay.titleDrain(0.25f);
					Overlay.drain(0.25f);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		});
	}

	private static String getQuote() {
		try {
			BufferedReader reader = new BufferedReader(new URLReader(new URL("https://quota.glitch.me/random")));
			String data = reader.readLine();
			Matcher matcher = Pattern.compile("(?:(?<=\"quoteText\":\")|(?<=\"quoteAuthor\":\"))[^\"]*").matcher(data);
			String quote = null;
			if (matcher.find()) quote = matcher.group();
			if (matcher.find()) quote = String.valueOf(quote) + "\n~ " + matcher.group();
			reader.close();
			return quote;
		} catch (IOException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public static void loadQuotes(StringBuffer quote) {
		//		try {
		//			quote.setLength(0);
		//			URL url = new URL("https://quota.glitch.me/random");
		//			final URLConnection connection = url.openConnection();
		//			final InputStream stream = connection.getInputStream();
		//			JSONParser parser = new JSONParser();
		//			JSONObject json = (JSONObject) parser.parse(new InputStreamReader(stream));
		//			quote.append(json.get("quoteText"));
		//			quote.append("\n ~");
		//			quote.append(json.get("quoteAuthor"));
		//		} catch (IOException | ParseException ioException) {
		//			System.out.println("Failed to fetch quote. " + ioException.getMessage());
		//		}
	}

	public static String formatSeconds(long timeInSeconds) {
		long hours = timeInSeconds / 3600;
		long secondsLeft = timeInSeconds - hours * 3600;
		long minutes = secondsLeft / 60;
		long seconds = secondsLeft - minutes * 60;

		String formattedTime = "";
		if (hours > 0) {
			if (hours < 10) formattedTime += "0";
			formattedTime += hours + " hours, ";
		}

		if (minutes > 0) {
			if (minutes < 10) formattedTime += "0";
			formattedTime += minutes + " minutes, ";
		}

		if (seconds < 10) formattedTime += "0";
		formattedTime += seconds + " seconds";

		return formattedTime;
	}

	public static Tile getTile(final int x, final int y) {
		return World.map[y][x];
	}

}
