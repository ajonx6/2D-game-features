package com.curaxu.game;

import com.curaxu.game.entity.Collisions;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.*;
import com.curaxu.game.graphics.*;

import java.util.HashMap;
import java.util.Random;

public class Generator {
	public static final Random RANDOM = new Random();

	// ! TODO CONTINUE THE SPRITE FIXING AND BLUEPRINTS AND COLLISIONS ETC

	public static Entity generatePlayer(int tileX, int tileY) {
		Entity player = new Entity(tileX, tileY, "Player", "player");
		SpriteSheet playerSprites = new SpriteSheet("entities/player/player", 33, 48);
		SpriteSheet playerSwimSprites = new SpriteSheet("entities/player/player_swim", 33, 40);
		player.addComponent(new SpriteListComponent(player, "idle_down",
				new Pair<>("idle_down", playerSprites.getSprite(1)),
				new Pair<>("idle_left", playerSprites.getSprite(4)),
				new Pair<>("idle_right", playerSprites.getSprite(7)),
				new Pair<>("idle_up", playerSprites.getSprite(10)),
				new Pair<>("walk_down", new AnimatedSprite(playerSprites.getSprite(0), playerSprites.getSprite(1), playerSprites.getSprite(2), playerSprites.getSprite(1)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_left", new AnimatedSprite(playerSprites.getSprite(3), playerSprites.getSprite(4), playerSprites.getSprite(5), playerSprites.getSprite(4)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_right", new AnimatedSprite(playerSprites.getSprite(6), playerSprites.getSprite(7), playerSprites.getSprite(8), playerSprites.getSprite(7)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_up", new AnimatedSprite(playerSprites.getSprite(9), playerSprites.getSprite(10), playerSprites.getSprite(11), playerSprites.getSprite(10)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("swim_down", playerSwimSprites.getSprite(0)),
				new Pair<>("swim_left", playerSwimSprites.getSprite(1)),
				new Pair<>("swim_right", playerSwimSprites.getSprite(2)),
				new Pair<>("swim_up", playerSwimSprites.getSprite(3))
		));

		// player.addComponent(new CollisionResolveComponent(player, "npc", true) {
		// 	public void onCollision(HashMap<Entity, Collisions.CollisionData> collided, double delta) {
		// 		MoveComponent comp = (MoveComponent) player.getComponent("Move");
		// 		AABBBoxComponent playerBox = (AABBBoxComponent) player.getComponent("AABBBox");
		// 		playerBox.hasCollided(true);
		// 		for (Entity e : collided.keySet()) {
		// 			Collisions.CollisionData data = collided.get(e);
		// 			AABBBoxComponent other = (AABBBoxComponent) e.getComponent("AABBBox");
		// 			if (data == null || data.contactNormal == null) continue;
		//
		// 			if (data.contactNormal.equals(new Vector(0, -1))) {
		// 				comp.setMoveDir(0, false);
		// 				player.worldPos.setY(other.getBox().getPosition().getY() - playerBox.getBox().getHeight() - 1);
		// 			} else if (data.contactNormal.equals(new Vector(0, 1))) {
		// 				comp.setMoveDir(1, false);
		// 				player.worldPos.setY(other.getBox().getPosition().getY() + other.getBox().getHeight() + 1);
		// 			} else if (data.contactNormal.equals(new Vector(-1, 0))) {
		// 				comp.setMoveDir(2, false);
		// 				player.worldPos.setX(other.getBox().getPosition().getX() - playerBox.getBox().getWidth() - 1);
		// 			} else if (data.contactNormal.equals(new Vector(1, 0))) {
		// 				comp.setMoveDir(3, false);
		// 				player.worldPos.setX(other.getBox().getPosition().getX() + other.getBox().getWidth() + 1);
		// 			}
		// 		}
		// 	}
		//
		// 	public void noCollisions(double delta) {
		// 		((AABBBoxComponent) player.getComponent("AABBBox")).hasCollided(false);
		// 	}
		// });
		player.addComponent(new MoveComponent(player, 150).setPlayerControls(true).canSwim(80));
		player.addComponent(new AABBBoxComponent(player, player.getScreenPos()));
		player.addComponent(new CameraComponent(player, true));

		player.verifyComponents();
		return player;
	}

	public static Entity generateNPC() {
		Entity king = new Entity(RANDOM.nextInt(Game.PIXEL_WIDTH), RANDOM.nextInt(Game.PIXEL_HEIGHT), "King", "npc");
		SpriteSheet kingSprites = new SpriteSheet("entities/king/king", 37, 47);
		SpriteSheet kingSwimSprites = new SpriteSheet("entities/king/king_swim", 37, 40);
		king.addComponent(new SpriteListComponent(king, "idle_down",
				new Pair<>("idle_down", kingSprites.getSprite(1)),
				new Pair<>("idle_left", kingSprites.getSprite(4)),
				new Pair<>("idle_right", kingSprites.getSprite(7)),
				new Pair<>("idle_up", kingSprites.getSprite(10)),
				new Pair<>("walk_down", new AnimatedSprite(kingSprites.getSprite(0), kingSprites.getSprite(1), kingSprites.getSprite(2), kingSprites.getSprite(1)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_left", new AnimatedSprite(kingSprites.getSprite(3), kingSprites.getSprite(4), kingSprites.getSprite(5), kingSprites.getSprite(4)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_right", new AnimatedSprite(kingSprites.getSprite(6), kingSprites.getSprite(7), kingSprites.getSprite(8), kingSprites.getSprite(7)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_up", new AnimatedSprite(kingSprites.getSprite(9), kingSprites.getSprite(10), kingSprites.getSprite(11), kingSprites.getSprite(10)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("swim_down", kingSwimSprites.getSprite(0)),
				new Pair<>("swim_left", kingSwimSprites.getSprite(1)),
				new Pair<>("swim_right", kingSwimSprites.getSprite(2)),
				new Pair<>("swim_up", kingSwimSprites.getSprite(3))
		));
		king.addComponent(new MoveComponent(king, 60).canSwim(25));
		king.addComponent(new RandomWalkComponent(king));
		king.addComponent(new AABBBoxComponent(king, king.getScreenPos()));
		king.addComponent(new CameraComponent(king, false));

		king.verifyComponents();
		return king;
	}

	public static Entity generateSheep() {
		Entity sheep = new Entity(RANDOM.nextInt(Game.PIXEL_WIDTH), RANDOM.nextInt(Game.PIXEL_HEIGHT), "Sally", "sheep");
		SpriteSheet sheepSprites = new SpriteSheet("entities/sheep/sheep", 27, 27);
		SpriteSheet sheepSwimSprites = new SpriteSheet("entities/sheep/sheep_swim", 27, 23);
		sheep.addComponent(new SpriteListComponent(sheep, "idle_down",
				new Pair<>("idle_down", sheepSprites.getSprite(1)),
				new Pair<>("idle_left", sheepSprites.getSprite(4)),
				new Pair<>("idle_right", sheepSprites.getSprite(7)),
				new Pair<>("idle_up", sheepSprites.getSprite(10)),
				new Pair<>("walk_down", new AnimatedSprite(sheepSprites.getSprite(0), sheepSprites.getSprite(1), sheepSprites.getSprite(2), sheepSprites.getSprite(1)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_left", new AnimatedSprite(sheepSprites.getSprite(3), sheepSprites.getSprite(4), sheepSprites.getSprite(5), sheepSprites.getSprite(4)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_right", new AnimatedSprite(sheepSprites.getSprite(6), sheepSprites.getSprite(7), sheepSprites.getSprite(8), sheepSprites.getSprite(7)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("walk_up", new AnimatedSprite(sheepSprites.getSprite(9), sheepSprites.getSprite(10), sheepSprites.getSprite(11), sheepSprites.getSprite(10)).setTimes(0.25, 0.25, 0.25, 0.25)),
				new Pair<>("swim_down", sheepSwimSprites.getSprite(0)),
				new Pair<>("swim_left", sheepSwimSprites.getSprite(1)),
				new Pair<>("swim_right", sheepSwimSprites.getSprite(2)),
				new Pair<>("swim_up", sheepSwimSprites.getSprite(3))
		));
		sheep.addComponent(new MoveComponent(sheep, 50).canSwim(20));
		sheep.addComponent(new RandomWalkComponent(sheep));
		sheep.addComponent(new AABBBoxComponent(sheep, sheep.getScreenPos()));
		sheep.addComponent(new CameraComponent(sheep, false));
		sheep.addComponent(new HealthComponent(sheep, 3));

		sheep.verifyComponents();
		return sheep;
	}

	public static Entity generateBloodSplatter(double x, double y) {
		Entity bloodGroup = new Entity(x, y, "Blood Group", "blood").onFloor();
		bloodGroup.verifyComponents();

		int numBloodSplatters = 4 + RANDOM.nextInt(10);
		for (int i = 0; i < numBloodSplatters; i++) {
			Entity bloodSplatter = new Entity(-10 + RANDOM.nextInt(20), -10 + RANDOM.nextInt(20), "Blood " + i, "blood").onFloor();
			bloodSplatter.addComponent(new SpriteListComponent(bloodSplatter, "default",
					new Pair<>("default", Sprite.createCircle(2 + RANDOM.nextInt(6), 0xFF880808))));
			bloodSplatter.verifyComponents();
			bloodGroup.addChild(bloodSplatter);
		}

		return bloodGroup;
	}
}