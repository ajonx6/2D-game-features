package com.curaxu.game;

import com.curaxu.game.entity.Collisions;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.*;
import com.curaxu.game.graphics.AnimatedSprite;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheet;
import com.curaxu.game.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Generator {
	public static final Random RANDOM = new Random();

	public static List<Vector> generatePoints(double radius, Vector startCoord, Vector dimensions) {
		return generatePoints(radius, startCoord, dimensions, 30);
	}

	public static List<Vector> generatePoints(double radius, Vector startCoord, Vector dimensions, int numSamplesBeforeReject) {
		double cellSize = radius / Math.sqrt(2.0);

		int[][] grid = new int[(int) Math.ceil(dimensions.getX() / cellSize)][(int) Math.ceil(dimensions.getY() / cellSize)];
		List<Vector> points = new ArrayList<>();
		List<Vector> spawnPoints = new ArrayList<>();

		spawnPoints.add(dimensions.div(2.0));
		while (!spawnPoints.isEmpty()) {
			int spawnIndex = RANDOM.nextInt(spawnPoints.size());
			Vector spawnCentre = spawnPoints.get(spawnIndex);
			boolean wasAccepted = false;

			for (int i = 0; i < numSamplesBeforeReject; i++) {
				double angle = RANDOM.nextDouble() * Math.PI * 2.0;
				Vector direction = new Vector(Math.sin(angle), Math.cos(angle));
				Vector candidate = spawnCentre.add(direction.mul(RANDOM.nextDouble(radius, radius * 2)));
				if (isValid(candidate, dimensions, cellSize, radius, points, grid)) {
					wasAccepted = true;
					points.add(candidate);
					spawnPoints.add(candidate);
					grid[(int) (candidate.getX() / cellSize)][(int) (candidate.getY() / cellSize)] = points.size();
					break;
				}
			}

			if (!wasAccepted) spawnPoints.remove(spawnIndex);
		}

		List<Vector> toRet = new ArrayList<>();
		for (Vector point : points) {
			toRet.add(point.add(startCoord));
		}
		return toRet;
	}

	public static boolean isValid(Vector candidate, Vector dimensions, double cellSize, double radius, List<Vector> points, int[][] grid) {
		if (candidate.getX() >= 0 && candidate.getX() < dimensions.getX() && candidate.getY() >= 0 && candidate.getY() < dimensions.getY()) {
			int cellX = (int) (candidate.getX() / cellSize);
			int cellY = (int) (candidate.getY() / cellSize);
			int searchStartX = (int) Math.max(0, cellX - 2.0);
			int searchStartY = (int) Math.max(0, cellY - 2.0);
			int searchEndX = (int) Math.min(cellX + 2.0, grid.length - 1);
			int searchEndY = (int) Math.min(cellY + 2.0, grid[0].length - 1);

			for (int x = searchStartX; x <= searchEndX; x++) {
				for (int y = searchStartY; y <= searchEndY; y++) {
					int pointIndex = grid[x][y] - 1;
					if (pointIndex != -1) {
						double sqdist = candidate.sub(points.get(pointIndex)).lengthSquared();
						if (sqdist < radius * radius) return false;
					}
				}
			}

			return true;
		}
		return false;
	}

	public static Entity generatePlayer(double x, double y) {
		Entity player = new Entity(x, y, "Player", "player");
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
		// player.addComponent(new CollisionResolveComponent(player, "tree", true) {
		// 	public void onCollision(List<Entity> collided, double delta) {
		// 		AABBBoxComponent playerBox = (AABBBoxComponent) player.getComponent("AABBBox");
		// 		playerBox.hasCollided(true);
		// 		MoveComponent moveComponent = (MoveComponent) player.getComponent("Move");
		//
		// 		for (Entity e : collided) {
		// 			AABBBoxComponent otherBox = (AABBBoxComponent) e.getComponent("AABBBox");
		// 			otherBox.hasCollided(true);
		// 			if (playerBox.getBox().getOtherCorner().getX() < )
		// 			if (aabbBoxComponent.getBox().getAbsolutePosition().getX() < )
		// }
		// }
		//
		// public void noCollisions(double delta) {
		// 	((AABBBoxComponent) player.getComponent("AABBBox")).hasCollided(false);
		// }
		// });
		player.addComponent(new MoveComponent(player, 150).setPlayerControls(true).canSwim(80));
		player.addComponent(new AABBBoxComponent(player, new Vector(6, 31), 21, 17));// new Vector(0, 0)));
		player.addComponent(new CameraComponent(player, true));

		player.verifyComponents();
		return player;
	}

	public static Entity generateNPC(double x, double y) {
		Entity king = new Entity(x, y, "King", "npc");
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
		king.addComponent(new AABBBoxComponent(king, new Vector(0, 0)));
		king.addComponent(new CameraComponent(king, false));
		king.addComponent(new HealthComponent(king, 3));
		king.addComponent(new LootComponent(king).addLoot(Item.SHINY, 3, 12, 0.5));

		king.verifyComponents();
		return king;
	}

	public static Entity generateSheep(double x, double y) {
		Entity sheep = new Entity(x, y, "Sally", "sheep");
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
		sheep.addComponent(new AABBBoxComponent(sheep, new Vector(0, 0)));
		sheep.addComponent(new CameraComponent(sheep, false));
		sheep.addComponent(new HealthComponent(sheep, 3));
		sheep.addComponent(new LootComponent(sheep).addLoot(Item.SHEEP_EYE, 1, 2, 0.3));

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

	public static Entity generateTree(double x, double y) {
		Entity tree = new Entity(x, y, "Tree", "tree");
		tree.addComponent(new SpriteListComponent(tree, "static",
				new Pair<>("static", new Sprite("entities/large_tree"))
		));
		tree.addComponent(new AABBBoxComponent(tree, new Vector(45, 89), 29, 62));

		tree.verifyComponents();
		return tree;
	}
}