package com.curaxu.game;

import com.curaxu.game.audio.SoundData;
import com.curaxu.game.audio.SoundGroup;
import com.curaxu.game.audio.SoundManager;
import com.curaxu.game.audio.tracks.CompoundData;
import com.curaxu.game.audio.tracks.EndingTrack;
import com.curaxu.game.audio.tracks.LoopTrack;
import com.curaxu.game.audio.tracks.RandomTrack;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.events.cutscenes.CameraMovementEvent;
import com.curaxu.game.events.cutscenes.Cutscene;
import com.curaxu.game.events.cutscenes.WaitEvent;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.inventory.Hotbar;
import com.curaxu.game.inventory.Storage;
import com.curaxu.game.items.Item;
import com.curaxu.game.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	public static final int SCALE = 2;
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int PIXEL_WIDTH = SCREEN_WIDTH / SCALE;
	public static final int PIXEL_HEIGHT = SCREEN_HEIGHT / SCALE;
	public static final String TITLE = "Minicraft";
	public static final double FPS = 300.0;
	public static final int TILE_SIZE = 32;

	private static Game instance;

	public BufferedImage image = new BufferedImage(PIXEL_WIDTH, PIXEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	public JFrame frame;
	public boolean running;

	public Screen screen;
	public Level level;
	public Entity player;
	public Random random = new Random();
	public CompoundData sound;

	public Storage inventory;
	public boolean showInventory = false;
	public Hotbar hotbar;

	public Cutscene group;

	private Game() {
		screen = new Screen(PIXEL_WIDTH, PIXEL_HEIGHT);
		level = new Level(128, 72);
		level.initLevel();

		inventory = new Storage(6, 3);
		inventory.setCellItem(0, 0, Item.KUNAI, 1);
		inventory.setCellItem(1, 0, Item.EMPTY_VIAL, 14);
		inventory.setCellItem(2, 0, Item.SHINY, 4);
		hotbar = new Hotbar(inventory);

		player = Generator.generatePlayer(96, 96);
		level.addEntity(player);

		group = new Cutscene().addEvent(new CameraMovementEvent(new Vector(0, 0), new Vector(100, 100), 2.0)).addEvent(new WaitEvent(0.5)).addEvent(new CameraMovementEvent(new Vector(100, 100), new Vector(0, 0), 1.0));

		sound = new CompoundData(
				new LoopTrack("stone", "sounds/sfx/stone"),
				new RandomTrack(new SoundGroup(new SoundData(SoundData.PLAY, 1, false)).addSound("ding", "sounds/sfx/ding").addSound("sound", "sounds/sfx/sound"), 2.0, 4.0),
				new EndingTrack("lake", "sounds/music/lake", false)
		);

		for (int i = 0; i < 20; i++) {
			if (random.nextBoolean())
				level.addEntity(Generator.generateNPC(random.nextInt(Game.PIXEL_WIDTH), random.nextInt(Game.PIXEL_HEIGHT)));
			else
				level.addEntity(Generator.generateSheep(random.nextInt(Game.PIXEL_WIDTH), random.nextInt(Game.PIXEL_HEIGHT)));
		}

		// ParticleBlueprint pn1 = new ParticleBlueprint().setSize(5).setColors(0x22444444).setSpeed(8).setDirection(0.0, 360.0).setDeceleration(0).setLife(2).lifeDeterminesAlpha();
		// ParticleBlueprint pn2 = new ParticleBlueprint().setSize(2).setColors(0xFFFF00FF, 0xFFFFFF00, 0xFF72DDE5).setSpeed(2.5).setDirection(0.0, 360.0).setDeceleration(0.1).setLife(2).lifeDeterminesAlpha();
		// ps1 = new PointParticleSystem(new Vector(100, 100), 50, pn1);
		// ps2 = new CircleParticleSystem(new Vector(50, 50), 710, 20, pn2);

		addKeyListener(new KeyInput());
		MouseInput mi = new MouseInput();
		addMouseListener(mi);
		addMouseMotionListener(mi);
		addMouseWheelListener(mi);
	}

	public static Game getInstance() {
		if (instance == null) instance = new Game();
		return instance;
	}

	public void run() {
		running = true;
		requestFocus();

		int frames = 0, ticks = 0;
		long frameCounter = 0;
		double frameTime = 1.0 / FPS;
		long lastTime = Time.getTime();
		double unprocessedTime = 0;

		// SoundManager.request(sound);

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

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
		if (KeyInput.isDown(KeyEvent.VK_ESCAPE)) System.exit(0);
		if (KeyInput.wasPressed(KeyEvent.VK_ENTER)) Settings.debugMode = !Settings.debugMode;

		for (int numKey = KeyEvent.VK_1; numKey <= KeyEvent.VK_6; numKey++) {
			if (KeyInput.wasPressed(numKey)) hotbar.setSelected(numKey - KeyEvent.VK_1);
		}

		if (KeyInput.wasPressed(KeyEvent.VK_SPACE)) {
			// showInventory = !showInventory;
			group.play();
		}

		// if (MouseInput.wasPressed(MouseEvent.BUTTON3)) {
		// 	((CameraComponent) player.getComponent("Camera")).deactivate();
		// 	// CameraComponent.activeCamera.deactivate();
		// 	List<Entity> e = level.getEntities("npc");
		// 	Entity picked = e.get(random.nextInt(e.size()));
		// 	((CameraComponent) picked.getComponent("Camera")).activate();
		// }

		// if (KeyInput.wasPressed(KeyEvent.VK_L)) {
		// 	sound.complete();
		// }
		// if (KeyInput.wasPressed(KeyEvent.VK_N)) {
		// 	sound.cancel();
		// }

		if (MouseInput.wasPressed(MouseEvent.BUTTON1)) {
			if (inventory.getCell(hotbar.getSelected(), 0).getItem() != null)
				inventory.getCell(hotbar.getSelected(), 0).getItem().leftClick(player, level);
		}
		if (MouseInput.wasPressed(MouseEvent.BUTTON3)) {
			if (inventory.getCell(hotbar.getSelected(), 0).getItem() != null)
				inventory.getCell(hotbar.getSelected(), 0).getItem().rightClick(player, level);
		}

		if (KeyInput.wasPressed(KeyEvent.VK_E)) {
			inventory.getCell(hotbar.getSelected(), 0).getItem().isEnchanted(!inventory.getCell(hotbar.getSelected(), 0).getItem().isEnchanted());
		}
		if (KeyInput.wasPressed(KeyEvent.VK_I)) {
			inventory.getCell(hotbar.getSelected(), 0).getItem().isSparkle(!inventory.getCell(hotbar.getSelected(), 0).getItem().isSparkle());
		}

		SoundManager.tick(delta);
		KeyInput.tick();
		MouseInput.tick();

		group.tick(delta);
		level.tick(delta);
		level.removeEntities();
		inventory.tick(delta);
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
		hotbar.render(screen);
		if (showInventory) inventory.render(screen);

		int[] ps = screen.getPixels();
		System.arraycopy(ps, 0, pixels, 0, pixels.length);
		g.drawImage(image, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		g.setColor(Color.WHITE);
		g.dispose();
		bs.show();
	}

	public Screen getScreen() {
		return screen;
	}

	public Level getLevel() {
		return level;
	}

	public Random getRandom() {
		return random;
	}
}