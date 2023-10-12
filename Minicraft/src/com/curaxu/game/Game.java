package com.curaxu.game;

import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.*;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheet;
import com.curaxu.game.graphics.SpriteSheets;
import com.curaxu.game.inventory.Storage;
import com.curaxu.game.items.Item;
import com.curaxu.game.level.Level;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;
import java.util.List;

import static com.curaxu.game.entity.components.Input.Type.*;

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
	public Storage storage;
	public Entity currEntity;

	private Game() {
		screen = new Screen(PIXEL_WIDTH, PIXEL_HEIGHT);
		level = new Level(128, 72);

		storage = new Storage(9, 4);
		storage.setCellItem(0, 0, Item.EMPTY_VIAL, 5);

		player = createPlayer();
		currEntity = player;
		for (int i = 0; i < 20; i++) {
			if (random.nextBoolean()) createNPC();
			else createSheep();
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

	public Entity createPlayer() {
		player = new Entity(0, 0, "player");
		player.addComponent(new SpriteListComponent(player,
				SpriteSheets.black_white_sprites.getSprite(4, 0).coloured(0xFF331D1A, 0XFF456D9E, 0XFFFFD8EF, 0),
				SpriteSheets.black_white_sprites.getSprite(5, 0).coloured(0XFF331D1A, 0XFF456D9E, 0XFFFFD8EF, 0XFF93D2F9)));
		player.addComponent(new AABBBoxComponent(player, player.getScreenPos(), Game.TILE_SIZE, Game.TILE_SIZE));
		player.addComponent(new CanMoveComponent(player, 150));
		player.addComponent(new CanSwimComponent(player, 80));
		player.addComponent(new CameraComponent(player, true));
		// player.addComponent(new PlayerControlComponent(player));

		player.addComponent(new CollisionResolveComponent(player, "tree") {
			public void onCollision() {
				// List<Entity> collided = level.getSurroundingTileCollisions(this.entity.getStanding(), this.getTag(), this.getAABBBox());
				// if (collided.size() > 0) this.getAABBBox().collided();
				// for (Entity e : collided) {
				// 	((AABBBox) e.getComponent("AABBBox")).collided();
				// }
			}
		});

		InputListenerComponent playerListener = new InputListenerComponent(player);
		playerListener.addInput(new Input(player, KeyEvent.VK_W, true, ON_PRESSED) {
			public void action() {
				((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDy(-1);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_S, true, ON_PRESSED) {
			public void action() {
				((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDy(1);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_A, true, ON_PRESSED) {
			public void action() {
				((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDx(-1);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_D, true, ON_PRESSED) {
			public void action() {
				((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDx(1);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_W, true, ON_UP) {
			public void action() {
				((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDy(0);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_S, true, ON_UP) {
			public void action() {
				((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDy(0);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_A, true, ON_UP) {
			public void action() {
				((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDx(0);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_D, true, ON_UP) {
			public void action() {
				((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDx(0);
			}
		});
		playerListener.addInput(new Input(player, MouseEvent.BUTTON1, false, ON_DOWN) {
			public void action() {
				// Check inv

				// Check entities
				List<Entity> clicked = level.boxEntityCollisionAll("sheep", MouseInput.mx / Game.SCALE, MouseInput.my / Game.SCALE);
				if (!clicked.isEmpty()) {
					if (storage.getAmountOf(Item.EMPTY_VIAL) == 0) return;
					boolean successful = storage.addItemToStorage(Item.BLOOD_VIAL);
					if (successful) storage.remove(Item.EMPTY_VIAL);
				}
			}
		});

		player.addComponent(playerListener);

		level.addEntity(player);
		return player;
	}

	public void createSheep() {
		Entity sheep = new Entity(random.nextInt(PIXEL_WIDTH), random.nextInt(PIXEL_HEIGHT), "sheep");
		sheep.addComponent(new SpriteListComponent(sheep, new SpriteSheet("sheep", 32, 32)));
		sheep.addComponent(new AABBBoxComponent(sheep, sheep.getWorldPos(), Game.TILE_SIZE, Game.TILE_SIZE));
		sheep.addComponent(new CanMoveComponent(sheep, 80));
		sheep.addComponent(new RandomWalkComponent(sheep));
		sheep.addComponent(new Component(sheep, "SpriteList", "CanMove") {
			public void tick(double delta) {
				SpriteListComponent slc = (SpriteListComponent) entity.getComponent("SpriteList");
				CanMoveComponent cmc = (CanMoveComponent) entity.getComponent("CanMove");
				if (cmc.getDirection().getX() < 0) slc.setIndex(0);
				else if (cmc.getDirection().getX() > 0) slc.setIndex(1);
			}

			public void render(Screen screen) {}

			public String getName() {
				return "ChangeSpriteBasedOnDir";
			}
		});

		level.addEntity(sheep);
	}

	public void createNPC() {
		int[] shirtcols = new int[]{ 0XFF456D9E, 0xFFA02945, 0XFF597238, 0XFFE4A55A };

		int c = shirtcols[random.nextInt(shirtcols.length)];
		Entity npc = new Entity(random.nextInt(PIXEL_WIDTH), random.nextInt(PIXEL_HEIGHT), "npc");
		npc.addComponent(new SpriteListComponent(npc,
				SpriteSheets.black_white_sprites.getSprite(4, 0).coloured(0xFF331D1A, c, 0XFFFFD8EF, 0),
				SpriteSheets.black_white_sprites.getSprite(5, 0).coloured(0xFF331D1A, c, 0XFFFFD8EF, 0XFF93D2F9)));
		npc.addComponent(new AABBBoxComponent(npc, npc.getWorldPos(), Game.TILE_SIZE, Game.TILE_SIZE));
		npc.addComponent(new CanMoveComponent(npc, 120));
		npc.addComponent(new CanSwimComponent(npc, 60));
		npc.addComponent(new CameraComponent(npc, false));
		npc.addComponent(new RandomWalkComponent(npc));
		// test.addComponent(new MoveTowards(test, player));

		// InputListenerComponent playerListener = new InputListenerComponent(player);
		// playerListener.addInput(new Input(test, KeyEvent.VK_W, true, ON_PRESSED, false) {
		// 	public void action() {
		// 		((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDy(-1);
		// 	}
		// });
		// playerListener.addInput(new Input(test, KeyEvent.VK_S, true, ON_PRESSED, false) {
		// 	public void action() {
		// 		((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDy(1);
		// 	}
		// });
		// playerListener.addInput(new Input(test, KeyEvent.VK_A, true, ON_PRESSED, false) {
		// 	public void action() {
		// 		((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDx(-1);
		// 	}
		// });
		// playerListener.addInput(new Input(test, KeyEvent.VK_D, true, ON_PRESSED, false) {
		// 	public void action() {
		// 		((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDx(1);
		// 	}
		// });
		// playerListener.addInput(new Input(test, KeyEvent.VK_W, true, ON_UP, false) {
		// 	public void action() {
		// 		((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDy(0);
		// 	}
		// });
		// playerListener.addInput(new Input(test, KeyEvent.VK_S, true, ON_UP, false) {
		// 	public void action() {
		// 		((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDy(0);
		// 	}
		// });
		// playerListener.addInput(new Input(test, KeyEvent.VK_A, true, ON_UP, false) {
		// 	public void action() {
		// 		((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDx(0);
		// 	}
		// });
		// playerListener.addInput(new Input(test, KeyEvent.VK_D, true, ON_UP, false) {
		// 	public void action() {
		// 		((CanMoveComponent) this.getEntity().getComponent("CanMove")).setDx(0);
		// 	}
		// });
		// test.addComponent(playerListener);
		level.addEntity(npc);
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
		if (KeyInput.isDown(KeyEvent.VK_ESCAPE)) System.exit(0);

		// if (MouseInput.wasPressed(MouseEvent.BUTTON3)) {
		// 	CameraComponent.activeCamera.deactivate();
		// 	List<Entity> e = level.getEntities("test");
		// 	Entity picked = e.get(random.nextInt(e.size()));
		// 	((CameraComponent) picked.getComponent("Camera")).activate();
		// 	((InputListenerComponent) currEntity.getComponent("InputListener")).deactivateAll();
		// 	currEntity = picked;
		// 	((InputListenerComponent) currEntity.getComponent("InputListener")).activateAll();
		// }

		KeyInput.tick();
		MouseInput.tick();
		level.tick(delta);
		level.removeEntities();
		storage.tick(delta);
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
		storage.render(screen);

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