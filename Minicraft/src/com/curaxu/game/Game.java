package com.curaxu.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.curaxu.game.entity.*;
import com.curaxu.game.graphics.SpriteSheets;
import com.curaxu.game.level.Level;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.particle.Particle;
import com.curaxu.game.particle.ParticleBlueprint;
import com.curaxu.game.particle.ParticleSystem;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

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

	private Screen screen;
	private Level level;
	private Entity player;
	private Random random = new Random();
	private ParticleSystem ps1;
	private ParticleSystem ps2;
	private ParticleSystem ps3;

	private Game() {
		screen = new Screen(PIXEL_WIDTH, PIXEL_HEIGHT);
		level = new Level(128, 72);

		createPlayer();

		int[] shirtcols = new int[] {0XFF456D9E, 0xFFA02945, 0XFF597238, 0XFFE4A55A};
		for (int i = 0; i < 20; i++) {
			int c = shirtcols[random.nextInt(shirtcols.length)];
			Entity test = new Entity(random.nextInt(PIXEL_WIDTH), random.nextInt(PIXEL_HEIGHT), "test");
			test.addComponent(new SpriteList(test, new Sprite(
				SpriteSheets.black_white_sprites.getGraySprite(4, 0, 1, 1),
				0xFF331D1A, c, 0XFFFFD8EF, 0), new Sprite(
				SpriteSheets.black_white_sprites.getGraySprite(5, 0, 1, 1),
				0xFF331D1A, c, 0XFFFFD8EF, 0XFF93D2F9)));
			test.addComponent(new AABBBox(test, test.getWorldPos(), Game.TILE_SIZE, Game.TILE_SIZE));
			test.addComponent(new CanMove(test, 120));
			test.addComponent(new CanSwim(test, 60));
			test.addComponent(new Camera(test, false));
			test.addComponent(new RandomWalk(test));
			// test.addComponent(new MoveTowards(test, player));
			level.addEntity(test);
		}

		ParticleBlueprint pn1 = new ParticleBlueprint().setSize(2).setColors(0xFF444444).setSpeed(8).setDirection(0.0, 360.0).setDeceleration(0).setLife(2);
		ParticleBlueprint pn2 = new ParticleBlueprint().setSize(2).setColors(0xFFFF00FF).setSpeed(8).setDirection(0.0, 360.0).setDeceleration(0.1).setLife(2);
		ParticleBlueprint pn3 = new ParticleBlueprint().setSize(2).setColors(0xFF72DDE5).setSpeed(8).setDirection(0.0, 360.0).setDeceleration(0.3).setLife(2);

		ps1 = new ParticleSystem(new Vector(100, 100), 600, new Particle(null, 2, 0xFF444444, 8, null, 0, 2, false));
		ps2 = new ParticleSystem(new Vector(50, 50), 70, new Particle(null, 2, 0xFFFF00FF, 8, null, 0.1, 2, false));
		ps3 = new ParticleSystem(new Vector(50, 50), 50, new Particle(null, 2, 0xFF72DDE5, 8, null, 0.3, 2, false));

		addKeyListener(new KeyInput());
		addMouseListener(new MouseInput());
	}

	public void createPlayer() {
		player = new Entity(0, 0, "player");
		player.addComponent(new SpriteList(player, new Sprite(
			SpriteSheets.black_white_sprites.getGraySprite(4, 0, 1, 1),
			0xFF331D1A, 0XFF456D9E, 0XFFFFD8EF, 0), new Sprite(
			SpriteSheets.black_white_sprites.getGraySprite(5, 0, 1, 1),
			0XFF331D1A, 0XFF456D9E, 0XFFFFD8EF, 0XFF93D2F9)));
		player.addComponent(new AABBBox(player, player.getWorldPos(), Game.TILE_SIZE, Game.TILE_SIZE));
		player.addComponent(new CanMove(player, 150));
		player.addComponent(new CanSwim(player, 80));
		player.addComponent(new PlayerControl(player));
		player.addComponent(new Camera(player, true));

		// player.addComponent(new CollisionResolve(player, "tree") {
		// 	public void onCollision() {
		// 		List<Entity> collided = level.getSurroundingTileCollisions(this.entity.getStanding(), this.getTag(), this.getAABBBox());
		// 		if (collided.size() > 0) this.getAABBBox().collided();
		// 		for (Entity e : collided) {
		// 			((AABBBox) e.getComponent("AABBBox")).collided();
		// 		}
		// 	}
		// });

		// InputListener playerListener = new InputListener(player);
		// playerListener.addInput(new Input(player, KeyEvent.VK_W, true, ON_PRESSED) {
		// 	public void action() {
		// 		((CanMove) this.getEntity().getComponent("CanMove")).setDy(-1);
		// 	}
		// });
		// playerListener.addInput(new Input(player, KeyEvent.VK_S, true, ON_PRESSED) {
		// 	public void action() {
		// 		((CanMove) this.getEntity().getComponent("CanMove")).setDy(1);
		// 	}
		// });
		// playerListener.addInput(new Input(player, KeyEvent.VK_A, true, ON_PRESSED) {
		// 	public void action() {
		// 		((CanMove) this.getEntity().getComponent("CanMove")).setDx(-1);
		// 	}
		// });
		// playerListener.addInput(new Input(player, KeyEvent.VK_D, true, ON_PRESSED) {
		// 	public void action() {
		// 		((CanMove) this.getEntity().getComponent("CanMove")).setDx(1);
		// 	}
		// });
		// playerListener.addInput(new Input(player, KeyEvent.VK_W, true, ON_UP) {
		// 	public void action() {
		// 		((CanMove) this.getEntity().getComponent("CanMove")).setDy(0);
		// 	}
		// });
		// playerListener.addInput(new Input(player, KeyEvent.VK_S, true, ON_UP) {
		// 	public void action() {
		// 		((CanMove) this.getEntity().getComponent("CanMove")).setDy(0);
		// 	}
		// });
		// playerListener.addInput(new Input(player, KeyEvent.VK_A, true, ON_UP) {
		// 	public void action() {
		// 		((CanMove) this.getEntity().getComponent("CanMove")).setDx(0);
		// 	}
		// });
		// playerListener.addInput(new Input(player, KeyEvent.VK_D, true, ON_UP) {
		// 	public void action() {
		// 		((CanMove) this.getEntity().getComponent("CanMove")).setDx(0);
		// 	}
		// });
		// player.addComponent(playerListener);

		level.addEntity(player);
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

		// SoundManager.request("lake");

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

		// if (MouseInput.wasPressed(MouseEvent.BUTTON1)) {
		// 	Camera.activeCamera.deactivate();
		// 	List<Entity> e = level.getEntities("test");
		// 	((Camera) e.get(random.nextInt(e.size())).getComponent("Camera")).activate();
		// }

		KeyInput.tick();
		MouseInput.tick();
		level.tick(delta);

		ps1.tick(delta);
		ps2.tick(delta);
		ps3.tick(delta);
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
		ps1.render(screen);
		ps2.render(screen);
		ps3.render(screen);
		player.render(screen);

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

	public Random getRandom() {
		return random;
	}
}