package com.curaxu.game;

import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.*;
import com.curaxu.game.graphics.AnimatedSprite;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheet;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import static com.curaxu.game.entity.components.Input.Type.*;

public class Generator {
	public static final Random RANDOM = new Random();
	
	public static Entity generatePlayer() {
		Entity player = new Entity(0, 0, "player");
		player.addComponent(new SpriteListComponent(player, "idle_right",
				new Pair<>("idle_left", new Sprite("player_idle_left")),
				new Pair<>("idle_right", new Sprite("player_idle_right")),
				new Pair<>("swim_left", new Sprite("player_swim_left")),
				new Pair<>("swim_right", new Sprite("player_swim_right")),
				new Pair<>("walk_left", new AnimatedSprite(new SpriteSheet("player_walk_left", 18, 36), 0.25, 0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_right", new AnimatedSprite(new SpriteSheet("player_walk_right", 18, 36), 0.25, 0.25, 0.25, 0.25, 0.25))
		));
		player.addComponent(new AABBBoxComponent(player, player.getScreenPos(), Game.TILE_SIZE, Game.TILE_SIZE));
		player.addComponent(new MoveComponent(player, 150).canSwim(80));
		player.addComponent(new CameraComponent(player, true));

		// player.addComponent(new CollisionResolveComponent(player, "tree") {
		// 	public void onCollision() {
		// List<Entity> collided = level.getSurroundingTileCollisions(this.entity.getStanding(), this.getTag(), this.getAABBBox());
		// if (collided.size() > 0) this.getAABBBox().collided();
		// for (Entity e : collided) {
		// 	((AABBBox) e.getComponent("AABBBox")).collided();
		// }
		// }
		// });

		InputListenerComponent playerListener = new InputListenerComponent(player);
		playerListener.addInput(new Input(player, KeyEvent.VK_W, true, ON_PRESSED) {
			public void action() {
				((MoveComponent) this.getEntity().getComponent("Move")).setDy(-1);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_S, true, ON_PRESSED) {
			public void action() {
				((MoveComponent) this.getEntity().getComponent("Move")).setDy(1);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_A, true, ON_PRESSED) {
			public void action() {
				((MoveComponent) this.getEntity().getComponent("Move")).setDx(-1);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_D, true, ON_PRESSED) {
			public void action() {
				((MoveComponent) this.getEntity().getComponent("Move")).setDx(1);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_W, true, ON_UP) {
			public void action() {
				((MoveComponent) this.getEntity().getComponent("Move")).setDy(0);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_S, true, ON_UP) {
			public void action() {
				((MoveComponent) this.getEntity().getComponent("Move")).setDy(0);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_A, true, ON_UP) {
			public void action() {
				((MoveComponent) this.getEntity().getComponent("Move")).setDx(0);
			}
		});
		playerListener.addInput(new Input(player, KeyEvent.VK_D, true, ON_UP) {
			public void action() {
				((MoveComponent) this.getEntity().getComponent("Move")).setDx(0);
			}
		});
		playerListener.addInput(new Input(player, MouseEvent.BUTTON1, false, ON_DOWN) {
			public void action() {
				// Check inv

				// Check entities
				// List<Entity> clicked = level.boxEntityCollisionAll("sheep", MouseInput.mx / Game.SCALE, MouseInput.my / Game.SCALE);
				// if (!clicked.isEmpty()) {
				// 	if (inventory.getAmountOf(Item.EMPTY_VIAL) == 0) return;
				// 	boolean successful = inventory.addItemToStorage(Item.BLOOD_VIAL);
				// 	if (successful) inventory.remove(Item.EMPTY_VIAL);
				// }
			}
		});
		player.addComponent(playerListener);
		return player;
	}
	
	public static Entity generateNPC() {
		int[] shirtcols = new int[]{ 0XFF456D9E, 0xFFA02945, 0XFF597238, 0XFFE4A55A };

		// int c = shirtcols[random.nextInt(shirtcols.length)];
		Entity npc = new Entity(RANDOM.nextInt(Game.PIXEL_WIDTH), RANDOM.nextInt(Game.PIXEL_HEIGHT), "npc");
		// npc.addComponent(new SpriteListComponent(npc,
		// 		SpriteSheets.black_white_sprites.getSprite(4, 0).coloured(0xFF331D1A, c, 0XFFFFD8EF, 0),
		// 		SpriteSheets.black_white_sprites.getSprite(5, 0).coloured(0xFF331D1A, c, 0XFFFFD8EF, 0XFF93D2F9)));
		// npc.addComponent(new AABBBoxComponent(npc, npc.getWorldPos(), Game.TILE_SIZE, Game.TILE_SIZE));
		// npc.addComponent(new CanMoveComponent(npc, 120));
		// npc.addComponent(new CanSwimComponent(npc, 60));
		// npc.addComponent(new CameraComponent(npc, false));
		// npc.addComponent(new RandomWalkComponent(npc));
		
		// test.addComponent(new MoveTowards(test, player));

		return npc;
	}
	
	public static Entity generateSheep() {
		Entity sheep = new Entity(RANDOM.nextInt(Game.PIXEL_WIDTH), RANDOM.nextInt(Game.PIXEL_HEIGHT), "sheep");
		// sheep.addComponent(new SpriteListComponent(sheep, new SpriteSheet("sheep", 32, 32)));
		// sheep.addComponent(new AABBBoxComponent(sheep, sheep.getWorldPos(), Game.TILE_SIZE, Game.TILE_SIZE));
		// sheep.addComponent(new CanMoveComponent(sheep, 80));
		// sheep.addComponent(new RandomWalkComponent(sheep));
		// sheep.addComponent(new Component(sheep, "SpriteList", "CanMove") {
		// 	public void tick(double delta) {
		// 		SpriteListComponent slc = (SpriteListComponent) entity.getComponent("SpriteList");
		// 		CanMoveComponent cmc = (CanMoveComponent) entity.getComponent("CanMove");
		// 		if (cmc.getDirection().getX() < 0) slc.setSprite(0);
		// 		else if (cmc.getDirection().getX() > 0) slc.setIndex(1);
		// 	}
		//
		// 	public void render(Screen screen) {}
		//
		// 	public String getName() {
		// 		return "ChangeSpriteBasedOnDir";
		// 	}
		// });
		return sheep;
	}
}