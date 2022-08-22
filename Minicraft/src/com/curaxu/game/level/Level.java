package com.curaxu.game.level;

import com.curaxu.game.Vector;
import com.curaxu.game.entity.*;
import com.curaxu.game.graphics.SpriteSheets;
import com.curaxu.game.Game;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;

import java.util.*;

public class Level {
    private int levelWidth, levelHeight;
    private int[] tileIDs;
    private int[] tileSpriteIndices;
    private Random random = new Random();

    private List<Entity> entities = new ArrayList<>();

    public Level(int levelWidth, int levelHeight) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.tileIDs = new int[levelWidth * levelHeight];
        this.tileSpriteIndices = new int[levelWidth * levelHeight];

        generateLevelWithAutomata();
        generateSpriteIndices();
        generateExtras();
    }
    public void generateSpriteIndices() {
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                int id = getTileID(x, y);
                boolean u = getTileID(x, y - 1) == id;
                boolean d = getTileID(x, y + 1) == id;
                boolean l = getTileID(x - 1, y) == id;
                boolean r = getTileID(x + 1, y) == id;

                if (u && d && l && r) tileSpriteIndices[x + y * levelWidth] = 5;
                if (u && d && l && !r) tileSpriteIndices[x + y * levelWidth] = 6;
                if (u && d && !l && r) tileSpriteIndices[x + y * levelWidth] = 4;
                if (u && d && !l && !r) tileSpriteIndices[x + y * levelWidth] = 13;
                if (u && !d && l && r) tileSpriteIndices[x + y * levelWidth] = 9;
                if (u && !d && l && !r) tileSpriteIndices[x + y * levelWidth] = 10;
                if (u && !d && !l && r) tileSpriteIndices[x + y * levelWidth] = 8;
                if (u && !d && !l && !r) tileSpriteIndices[x + y * levelWidth] = 3;
                if (!u && d && l && r) tileSpriteIndices[x + y * levelWidth] = 1;
                if (!u && d && l && !r) tileSpriteIndices[x + y * levelWidth] = 2;
                if (!u && d && !l && r) tileSpriteIndices[x + y * levelWidth] = 0;
                if (!u && d && !l && !r) tileSpriteIndices[x + y * levelWidth] = 7;
                if (!u && !d && l && r) tileSpriteIndices[x + y * levelWidth] = 14;
                if (!u && !d && l && !r) tileSpriteIndices[x + y * levelWidth] = 11;
                if (!u && !d && !l && r) tileSpriteIndices[x + y * levelWidth] = 15;
                if (!u && !d && !l && !r) tileSpriteIndices[x + y * levelWidth] = 12;
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

    public void generateExtras() {
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                int tileID = getTileID(x, y);
                if (tileID == Tile.GRASS.getId()) {
                    if (random.nextDouble() > 0.95) {
                        if (random.nextDouble() > 0.3) {
                            entities.add(EntityGenerator.createTree(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE)));
                        } else {
                            entities.add(EntityGenerator.createCandle(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE)));
                        }
                    }
                }
            }
        }
    }

    public int getTileID(int x, int y) {
        int i = x + y * levelWidth;
        if (i < 0 || i >= tileIDs.length) return 0;
        return tileIDs[i];
    }

    public int getTileIDAtWorldPos(Vector position) {
        return getTileID((int) Math.floor(position.getX() / Game.TILE_SIZE), (int) Math.floor(position.getY() / Game.TILE_SIZE));
    }

    public List<Entity> boxEntityCollisionAll(String tag, AABBBox a) {
        List<Entity> cs = new ArrayList<>();
        for (Entity e : entities) {
            if (!e.getTag().equals(tag)) continue;
            AABBBox b = (AABBBox) e.getComponent("AABBBox");
            if (b != null && Collisions.collisionWithBox(a, b)) cs.add(e);
        }
        return cs;
    }

    public Entity boxEntityCollisionFirst(String tag, AABBBox a) {
        for (Entity e : entities) {
            if (!e.getTag().equals(tag)) continue;
            AABBBox b = (AABBBox) e.getComponent("AABBBox");
            if (b != null && Collisions.collisionWithBox(a, b)) return e;
        }
        return null;
    }

    public void tick(double delta) {
        for (Entity e : entities) {
            e.tick(delta);
        }

        entities.sort((e1, e2) -> {
            if (e2.getWorldPos().getY() > e1.getWorldPos().getY()) return -1;
            if (e1.getWorldPos().getY() > e2.getWorldPos().getY()) return 1;
            return 0;
        });
    }

    public boolean tileInBounds(Vector worldpos) {
        double up = worldpos.getY();
        double down = worldpos.getY() + Game.TILE_SIZE;
        double left = worldpos.getX();
        double right = worldpos.getX() + Game.TILE_SIZE;

        return up < Game.PIXEL_HEIGHT && down >= 0 && left < Game.PIXEL_WIDTH && right >= 0;
    }

    public void render(Screen screen) {
         for (int y = 0; y < levelHeight; y++) {
             for (int x = 0; x < levelWidth; x++) {
                 Vector worldpos = Game.getInstance().getScreen().getOffset().add(x * Game.TILE_SIZE, y * Game.TILE_SIZE);
                 if (!tileInBounds(worldpos)) continue;
                 Tile t = Tile.getTile(tileIDs[x + y * levelWidth]);
                 Sprite sprite = t.getSpriteAtIndex(tileSpriteIndices[x + y * levelWidth]);
                 screen.renderSprite(worldpos, sprite);
             }
         }

        for (Entity e : entities) {
            e.render(screen);
        }
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getEntities(String name) {
        List<Entity> res = new ArrayList<>(entities);
        res.removeIf(e -> !e.getTag().equals(name));
        return res;
    }


    public int getWidth() {
        return levelWidth;
    }

    public int getHeight() {
        return levelHeight;
    }
}
