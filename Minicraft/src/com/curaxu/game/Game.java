package com.curaxu.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.curaxu.game.entity.*;
import com.curaxu.game.graphics.SpriteSheets;
import com.curaxu.game.level.Level;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int SCALE = 3;
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int PIXEL_WIDTH = SCREEN_WIDTH / SCALE;
	public static final int PIXEL_HEIGHT = SCREEN_HEIGHT / SCALE;
	public static final String TITLE = "Minicraft";
	public static final double FPS = 60.0;
	public static final int TILE_SIZE = 16;

	private static Game instance;

	public BufferedImage image = new BufferedImage(PIXEL_WIDTH, PIXEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	public JFrame frame;
	public boolean running;

	private Screen screen;
	private Level level;
	public Player player;

	private Game() {
		screen = new Screen(PIXEL_WIDTH, PIXEL_HEIGHT);
		level = new Level(128, 72);
		player = new Player();
		player.addComponent(new SpriteList(player, new Sprite(SpriteSheets.tile_sheet.getSprite(1), 0xFFEAB2E6, 0XFFBED9F7, 0XFFF44B6A, 0), new Sprite(SpriteSheets.tile_sheet.getSprite(2), 0xFFEAB2E6, 0XFFBED9F7, 0XFFF44B6A, 0XFF93D2F9)));
		player.addComponent(new AABBBox(player, player.getWorldX(), player.getWorldY(), Game.TILE_SIZE, Game.TILE_SIZE));
		player.addComponent(new Move(player, 2));
		player.addComponent(new Swim(player));

		addKeyListener(new KeyInput());
	}

	public static Game getInstance() {
		if (instance == null) instance = new Game();
		return instance;
	}

	public void stop() {
		if (!running) return;
		running = false;
	}

	public void run() {
		running = true;
		requestFocus();

		int frames = 0, ticks = 0;
		long frameCounter = 0;
		double frameTime = 1.0 / FPS;
		long lastTime = Time.getTime();
		double unprocessedTime = 0;

		// SoundManager.request("pallet_town_bg_music");

		while (running) {
			boolean render = false;

			long startTime = Time.getTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;

			unprocessedTime += passedTime / (double) Time.SECOND;
			frameCounter += passedTime;

			while (unprocessedTime > frameTime) {
				render = true;
				unprocessedTime -= frameTime;
				Time.setDelta(frameTime);
				tick();
				ticks++;
				if (frameCounter >= Time.SECOND) {
					frame.setTitle(TITLE + " | FPS: " + frames + ", UPS: " + ticks);
					frames = 0;
					ticks = 0;
					frameCounter = 0;
				}
			}
			if (render) {
				render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
	}

	public void tick() {
		double delta = Time.getFrameTimeInSeconds();

		if (KeyInput.isDown(KeyEvent.VK_W)) {
			player.move(0, -1.0);
		}
		if (KeyInput.isDown(KeyEvent.VK_S)) {
			player.move(0, 1.0);
		}
		if (KeyInput.isDown(KeyEvent.VK_A)) {
			player.move(-1.0, 0);
		}
		if (KeyInput.isDown(KeyEvent.VK_D)) {
			player.move(1.0, 0);
		}

		KeyInput.tick(delta);
		level.tick();
		player.tick();
		// if (Fade.state != Fade.INACTIVE) Fade.tick(delta);
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		screen.clear();

		level.render(screen);
		player.render(screen);

		// if (Fade.state != Fade.INACTIVE) Fade.render(screen);
		int[] ps = screen.getPixels();
		System.arraycopy(ps, 0, pixels, 0, pixels.length);

		g.drawImage(image, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		g.dispose();
		bs.show();
	}

	public Screen getScreen() {
		return screen;
	}

	public Level getLevel() {
		return level;
	}
}