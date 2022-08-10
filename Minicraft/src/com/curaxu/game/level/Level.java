package com.curaxu.game.level;

import com.curaxu.game.graphics.SpriteSheets;
import com.curaxu.game.Game;
import com.curaxu.game.entity.AABBBox;
import com.curaxu.game.entity.Collisions;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.SpriteList;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.util.SimplexNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
    private int levelWidth, levelHeight;
    private Tile[] tiles;
    private Random random = new Random();

    private List<Entity> entities = new ArrayList<>();

    public Level(int levelWidth, int levelHeight) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.tiles = new Tile[levelWidth * levelHeight];

        generateLevelWithAutomata();
        generateTileSprites();
        generateTrees();
    }

    public void generateLevelWithSimplex() {
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                double s = SimplexNoise.noise((double) x / 11.0, (double) y / 11.0) / 2.0 + 0.5;
                boolean isWater = s > 0.7;
                Tile tile = isWater ? new WaterTile(x, y, this) : new GrassTile(x, y, this);
                tiles[x + y * levelWidth] = tile;
            }
        }
    }

    public void generateLevelWithAutomata() {
        IslandGeneration generator = new IslandGeneration(levelWidth, levelHeight, 0.35);
        generator.generate();
        boolean[] cells = generator.getCells();

        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                Tile tile = cells[x + y * levelWidth] ? new WaterTile(x, y, this) : new GrassTile(x, y, this);
                tiles[x + y * levelWidth] = tile;
            }
        }
    }

    public void generateTrees() {
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                Tile t = getTile(x, y);
                t.setSprite();
                if (t instanceof GrassTile) {
                    if (random.nextDouble() > 0.95) {
                        Entity tree = new Entity(t.worldX, t.worldY, "tree");
                        tree.addComponent(new SpriteList(tree, new Sprite(SpriteSheets.tile_sheet.getSprite(3, 0), 0xFF704629, 0xFF438759, 0xFF54A86E, 0)));
                        tree.addComponent(new AABBBox(tree, tree.getWorldX(), tree.getWorldY(), Game.TILE_SIZE, Game.TILE_SIZE));
                        t.ontop = tree;
                        entities.add(tree);
                    }
                }
            }
        }
    }

    public void generateTileSprites() {
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                Tile t = getTile(x, y);
                t.setSprite();
            }
        }
    }

    public Tile getTile(int x, int y) {
        int i = x + y * levelWidth;
        if (i < 0 || i >= tiles.length) return null;
        return tiles[i];
    }

    public Tile getTileAtWorldPos(int x, int y) {
        return getTile(x / Game.TILE_SIZE, y / Game.TILE_SIZE);
    }

    public boolean tileInBounds(Screen screen, Tile t) {
        int up = t.getWorldY() + screen.yOffset;
        int down = t.getWorldY() + Game.TILE_SIZE + screen.yOffset;
        int left = t.getWorldX() + screen.xOffset;
        int right = t.getWorldX() + Game.TILE_SIZE + screen.xOffset;

        return up < Game.PIXEL_HEIGHT && down >= 0 && left < Game.PIXEL_WIDTH && right >= 0;
    }

    public List<Entity> boxEntityCollisionAll(String tag, AABBBox a) {
        List<Entity> cs = new ArrayList<>();
        for (Entity e : entities) {
            if (!e.getTag().equals(tag)) continue;
            AABBBox b = (AABBBox) e.getComponent("aabb");
            if (b != null && Collisions.collisionWithBox(a, b)) cs.add(e);
        }
        return cs;
    }

    public Entity boxEntityCollisionFirst(String tag, AABBBox a) {
        for (Entity e : entities) {
            if (!e.getTag().equals(tag)) continue;
            AABBBox b = (AABBBox) e.getComponent("aabb");
            if (b != null && Collisions.collisionWithBox(a, b)) return e;
        }
        return null;
    }

    public void tick() {
        for (Tile t : tiles) {
            t.tick();
        }

        for (Entity e : entities) {
            e.tick();
        }
    }

    public void render(Screen screen) {
        for (Tile t : tiles) {
            if (tileInBounds(screen, t)) t.render(screen);
        }

        for (Entity e : entities) {
            e.render(screen);
        }
    }

    public int getWidth() {
        return levelWidth;
    }

    public int getHeight() {
        return levelHeight;
    }
}
