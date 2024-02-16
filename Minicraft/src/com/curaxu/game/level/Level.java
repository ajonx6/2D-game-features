package com.curaxu.game.level;

import com.curaxu.game.Game;
import com.curaxu.game.PerlinNoise2D;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.AABBBox;
import com.curaxu.game.entity.Collisions;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.AABBBoxComponent;
import com.curaxu.game.entity.components.MoveComponent;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.particle.ParticleSystem;
import com.sun.security.jgss.GSSUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Level {
	private int levelWidth, levelHeight;
	private int[] tileIDs;
	private int[] tileSpriteIndices;
	private Random random = new Random();

	private List<Entity> entities = new ArrayList<>();
	private HashMap<String, List<Entity>> entityMap = new HashMap<>();
	private List<Entity> toRemove = new ArrayList<>();
	private List<ParticleSystem> particleSystems = new ArrayList<>();

	public Level(int levelWidth, int levelHeight) {
		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;
		this.tileIDs = new int[levelWidth * levelHeight];
		this.tileSpriteIndices = new int[levelWidth * levelHeight];
		Arrays.fill(tileSpriteIndices, -1);

		generateLevelWithNoise();
		// generateLevelWithAutomata();
		generateSpriteIndices();
		// generateExtras();
	}

	public void generateSpriteIndices() {
		for (int y = 0; y < levelHeight; y++) {
			for (int x = 0; x < levelWidth; x++) {
				int id = getTileID(x, y);

				int spriteOffset = 0;
				spriteOffset += getTileID(x + 1, y) == id ? 1 : 0;
				spriteOffset += getTileID(x - 1, y) == id ? 2 : 0;
				spriteOffset += getTileID(x, y + 1) == id ? 4 : 0;
				spriteOffset += getTileID(x, y - 1) == id ? 8 : 0;
				tileSpriteIndices[x + y * levelWidth] = 15 - spriteOffset;
			}
		}
	}

	public void generateLevelWithAutomata() {
		IslandGeneration generator = new IslandGeneration(levelWidth, levelHeight, 0.35);
		generator.generate();
		boolean[] cells = generator.getCells();

		for (int y = 0; y < levelHeight; y++) {
			for (int x = 0; x < levelWidth; x++) {
				tileIDs[x + y * levelWidth] = cells[x + y * levelWidth] ? Tile.WATER.getId() : Tile.GRASS.getId();
			}
		}
	}

	public void generateLevelWithNoise() {
		int[] pixels = PerlinNoise2D.getNoiseImage(levelWidth, levelHeight, random.nextInt());

		for (int y = 0; y < levelHeight; y++) {
			for (int x = 0; x < levelWidth; x++) {
				tileIDs[x + y * levelWidth] = ((pixels[x + y * levelWidth] >> 16) & 0xff) < 110 ? Tile.WATER.getId() : Tile.GRASS.getId();
			}
		}

		int[] ps = new int[levelWidth * levelHeight];
		for (int i = 0; i < tileIDs.length; i++) {
			ps[i] = tileIDs[i] == Tile.WATER.getId() ? 0x0000ff : 0x00ff00;
		}
		BufferedImage img = new BufferedImage(levelWidth, levelHeight, BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, levelWidth, levelHeight, ps, 0, levelWidth);
		try {
			ImageIO.write(img, "PNG", new File("World.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void generateExtras() {
		// for (int y = 0; y < levelHeight; y++) {// += Integer.MAX_VALUE) {
		// 	for (int x = 0; x < levelWidth; x++) {// += Integer.MAX_VALUE) {
		// 		Tile t = Tile.getTileByID(getTileID(x, y));
		// 		if (t == Tile.GRASS) {
		// 			if (random.nextDouble() > 0.95) {
		// 				if (random.nextDouble() > 0.5) {
		// 					Entity tree = new Entity(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE), "tree");
		// 					tree.addComponent(new SpriteListComponent(tree, SpriteSheets.black_white_sprites.getSprite(3, 0).coloured(0xFF704629, 0xFF438759, 0xFF54A86E, 0)));
		// 					tree.addComponent(new AABBBoxComponent(tree, tree.getWorldPos(), Game.TILE_SIZE, Game.TILE_SIZE));
		// 					tree.addComponent(new LootComponent(tree).addLoot(Item.WOOD, 1, 4, 0.8f));
		// 					entities.add(tree);
		// 				} else {
		// 					Entity candle = new Entity(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE), "candle");
		// 					SpriteListComponent list = new SpriteListComponent(candle, SpriteSheets.colored_sprites.getSprite(0, 0));
		// 					candle.addComponent(list);
		// 					candle.addComponent(new AABBBoxComponent(candle, candle.getWorldPos(), list, 0));// 0x88DD892A
		// 					// candle.addComponent(new LightComponent(candle, new Vector(15, 12), new Light(null, 8, 0xFFDD892A, true), true));
		// 					// candle.addComponent(new LightComponent(candle, new Vector(15, 12), new Light(null, 24, 0x66ff00ff, true), true));
		// 					entities.add(candle);
		// 				}
		// 			} else if (random.nextDouble() > 0.95) {
		// 				Entity magic = new Entity(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE), "magic");
		// 				SpriteListComponent list = new SpriteListComponent(magic, SpriteSheets.colored_sprites.getSprite(1, 0));
		// 				magic.addComponent(list);
		// 				AABBBoxComponent box = new AABBBoxComponent(magic, magic.getWorldPos(), list, 0);
		// 				magic.addComponent(box);
		// 				entities.add(magic);
		//
		// 				ParticleBlueprint pb = new ParticleBlueprint().setSize(1).setColors(0xAA99212D, 0xAA8E1F2A, 0xFFAE1F2A).setSpeed(0).setDirection(0.0, 360.0).setDeceleration(0.1).setLife(2).lifeDeterminesAlpha();
		// 				ParticleSystem ps = new CircleParticleSystem(new Vector(x * Game.TILE_SIZE + box.getOffx() + box.getWidth() / 2, y * Game.TILE_SIZE + box.getOffy() + box.getHeight() / 2), 20, Game.TILE_SIZE / 2, pb);
		// 				particleSystems.add(ps);
		// 			}
		// 		}
		// 	}
		// }
	}

	public int getTileID(int x, int y) {
		int i = x + y * levelWidth;
		if (i < 0 || i >= tileIDs.length) return 0;
		return tileIDs[i];
	}

	public int getTileIDAtWorldPos(Vector position) {
		return getTileID((int) Math.floor(position.getX() / Game.TILE_SIZE), (int) Math.floor(position.getY() / Game.TILE_SIZE));
	}

	// public List<Entity> getSurroundingTileCollisions(Tile t, String tag, AABBBox a) {
	//     List<Entity> cs = new ArrayList<>();
	//     for (int y = -1; y <= 1; y++) {
	//         for (int x = -1; x <= 1; x++) {
	//             Tile s = getTile(t.tx + x, t.ty + y);
	//             if (s != null && s.getOntop() != null) {
	//                 AABBBox b = (AABBBox) s.getOntop().getComponent("AABBBox");
	//                 if (b != null && s.getOntop().getTag().equals(tag) && Collisions.collisionWithBox(a, b)) {
	//                     cs.add(s.getOntop());
	//                 }
	//             }
	//         }
	//     }
	//     return cs;
	// }

	public boolean tileInBounds(int x, int y) {
		double xx = x * Game.TILE_SIZE + Game.getInstance().getScreen().getOffset().getX();
		double yy = y * Game.TILE_SIZE + Game.getInstance().getScreen().getOffset().getY();
		return xx >= -Game.TILE_SIZE && yy >= -Game.TILE_SIZE && xx < Game.PIXEL_WIDTH && yy < Game.PIXEL_HEIGHT;
	}

	public List<Entity> getCollidedWithPoint(int x, int y, String tag) {
		List<Entity> cs = new ArrayList<>();
		for (Entity e : entities) {
			if (!e.getTag().contains(tag)) continue;
			AABBBoxComponent b = (AABBBoxComponent) e.getComponent("AABBBox");
			if (b != null && Collisions.PointVsBox(new Vector(x, y), b.getBox())) cs.add(e);
		}
		return cs;
	}

	public HashMap<Entity, Collisions.CollisionData> getCollidedWithEntity(Entity e, String tag, boolean dynamic, double delta) {
		AABBBoxComponent comp1 = (AABBBoxComponent) e.getComponent("AABBBox");
		if (comp1 == null) return new HashMap<>();
		AABBBox a = comp1.getBox();

		MoveComponent comp2 = (MoveComponent) e.getComponent("Move");
		if (dynamic && comp2 == null) return new HashMap<>();
		Vector velocity = comp2 == null ? new Vector(0, 0) : comp2.getVelocity();

		if (!entityMap.containsKey(tag)) return new HashMap<>();
		HashMap<Entity, Collisions.CollisionData> collisions = new HashMap<>();
		for (Entity other : entityMap.get(tag)) {
			AABBBoxComponent otherBox = (AABBBoxComponent) other.getComponent("AABBBox");
			if (otherBox == null) continue;
			if ((!dynamic || velocity.isZero()) && Collisions.BoxVsBox(a, otherBox.getBox()))
				collisions.put(other, null);
			if (dynamic && !velocity.isZero()) {
				Collisions.CollisionData collisionData = Collisions.DynamicBoxVsBox(a, velocity, otherBox.getBox(), delta);
				if (collisionData != null) collisions.put(other, collisionData);
			}
		}

		// System.out.println(dynamic);
		// System.out.println(collisions);
		// System.exit(0);
		return collisions;
	}

	public List<Entity> getCollidedWithEntity(AABBBoxComponent box, String tag) {
		if (!entityMap.containsKey(tag)) return new ArrayList<>();
		List<Entity> collisions = new ArrayList<>();
		for (Entity item : entityMap.get(tag)) {
			AABBBoxComponent itemBox = (AABBBoxComponent) item.getComponent("AABBBox");
			if (Collisions.BoxVsBox(box.getBox(), itemBox.getBox())) collisions.add(item);
		}
		return collisions;
	}

	public Entity getFirstCollidedWithEntity(Entity e, String tag) {
		return getFirstCollidedWithEntity((AABBBoxComponent) e.getComponent("AABBBox"), tag);
	}

	public Entity getFirstCollidedWithEntity(AABBBoxComponent eBox, String tag) {
		if (!entityMap.containsKey(tag)) return null;
		for (Entity item : entityMap.get(tag)) {
			AABBBoxComponent itemBox = (AABBBoxComponent) item.getComponent("AABBBox");
			if (Collisions.BoxVsBox(eBox.getBox(), itemBox.getBox())) return item;
		}
		return null;
	}

	public void tick(double delta) {
		for (Entity e : entities) {
			e.tick(delta);
		}

		for (ParticleSystem p : particleSystems) {
			p.tick(delta);
		}

		entities.sort(Comparator.comparingDouble(e -> e.getWorldFootPos().getY()));
	}

	public void render(Screen screen) {
		for (int y = 0; y < levelHeight; y++) {
			for (int x = 0; x < levelWidth; x++) {
				if (!tileInBounds(x, y) || tileSpriteIndices[x + y * levelWidth] == -1 || getTileID(x, y) == 0)
					continue;
				Tile t = Tile.getTileByID(getTileID(x, y));
				screen.renderSprite(Game.getInstance().getScreen().getOffset().add(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE)), t.getSprites()[tileSpriteIndices[x + y * levelWidth]]);
			}
		}

		for (Entity e : entities) {
			e.render(screen);
		}

		for (ParticleSystem p : particleSystems) {
			p.render(screen);
		}
	}

	public void addEntity(Entity e) {
		entities.add(e);
		if (!entityMap.containsKey(e.tag)) entityMap.put(e.tag, new ArrayList<>());
		entityMap.get(e.tag).add(e);
	}

	public void prepareRemove(Entity e) {
		toRemove.add(e);
	}

	public void removeEntities() {
		for (Entity e : toRemove) {
			if (entityMap.containsKey(e.tag)) entityMap.get(e.tag).remove(e);
			entities.remove(e);
		}
		toRemove.clear();
	}

	public int getWidth() {
		return levelWidth;
	}

	public int getHeight() {
		return levelHeight;
	}
}
