package com.curaxu.game.level;

import com.curaxu.game.Vector;
import com.curaxu.game.entity.*;
import com.curaxu.game.graphics.SpriteSheets;
import com.curaxu.game.Game;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.util.SimplexNoise;

import java.util.*;

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
                        Entity tree = new Entity(t.getWorldPos(), "tree");
                        tree.addComponent(new SpriteList(tree, new Sprite(SpriteSheets.tile_sheet.getSprite(3, 0, 1, 1), 0xFF704629, 0xFF438759, 0xFF54A86E, 0)));
                        tree.addComponent(new AABBBox(tree, tree.getWorldPos(), Game.TILE_SIZE, Game.TILE_SIZE));
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

    public Tile getTileAtWorldPos(Vector position) {
        return getTile((int) Math.floor(position.getX() / Game.TILE_SIZE), (int) Math.floor(position.getY() / Game.TILE_SIZE));
    }

    public boolean tileInBounds(Screen screen, Tile t) {
        double up = t.getWorldPos().getY() + screen.getOffset().getY();
        double down = t.getWorldPos().getY() + Game.TILE_SIZE + screen.getOffset().getY();
        double left = t.getWorldPos().getX() + screen.getOffset().getX();
        double right = t.getWorldPos().getX() + Game.TILE_SIZE + screen.getOffset().getX();

        return up < Game.PIXEL_HEIGHT && down >= 0 && left < Game.PIXEL_WIDTH && right >= 0;
    }

    public List<Entity> getSurroundingTileCollisions(Tile t, String tag, AABBBox a) {
        List<Entity> cs = new ArrayList<>();
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                Tile s = getTile(t.tx + x, t.ty + y);
                if (s != null && s.getOntop() != null) {
                    AABBBox b = (AABBBox) s.getOntop().getComponent("AABBBox");
                    if (b != null && s.getOntop().getTag().equals(tag) && Collisions.collisionWithBox(a, b)) {
                        cs.add(s.getOntop());
                    }
                }
            }
        }
        return cs;
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
        for (Tile t : tiles) {
            t.tick();
        }

        for (Entity e : entities) {
            e.tick(delta);
        }

        // entities.sort((e1, e2) -> {
        //     if (e2.getWorldPos().getY() >= e1.getWorldPos().getY()) return -1;
        //     if (e1.getWorldPos().getY() >= e2.getWorldPos().getY()) return 1;
        //     return 0;
        // });
    }

    public void render(Screen screen) {
        for (Tile t : tiles) {
            if (tileInBounds(screen, t)) t.render(screen);
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

    public int getWidth() {
        return levelWidth;
    }

    public int getHeight() {
        return levelHeight;
    }
}
