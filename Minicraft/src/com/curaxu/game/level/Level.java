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
        for (int i = 0; i < tileSpriteIndices.length; i++) {
            tileSpriteIndices[i] = -1;
        }

        generateLevelWithAutomata();
        generateSpriteIndices();
        generateExtras();
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
                tileSpriteIndices[x+y*levelWidth] = 15 - spriteOffset;
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
                Tile t = Tile.getTileByID(getTileID(x, y));
                if (t == Tile.GRASS) {
                    if (random.nextDouble() > 0.95) {
                        if (random.nextDouble() > 0.3) {
                            Entity tree = new Entity(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE), "tree");
                            tree.addComponent(new SpriteList(tree, new Sprite(SpriteSheets.black_white_sprites.getGraySprite(3, 0, 1, 1), 0xFF704629, 0xFF438759, 0xFF54A86E, 0)));
                            tree.addComponent(new AABBBox(tree, tree.getWorldPos(), Game.TILE_SIZE, Game.TILE_SIZE));
                            entities.add(tree);
                        } else {
                            Entity candle = new Entity(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE), "candle");
                            SpriteList list = new SpriteList(candle, SpriteSheets.colored_sprites.getSprite(0, 0, 1, 1));
                            candle.addComponent(list);
                            candle.addComponent(new AABBBox(candle, candle.getWorldPos(), list, 0));
                            entities.add(candle);
                        }
                    } else if (random.nextDouble() > 0.9) {
                        Entity magic = new Entity(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE), "magic");
                        SpriteList list = new SpriteList(magic, SpriteSheets.colored_sprites.getSprite(1, 0, 1, 1));
                        magic.addComponent(list);
                        magic.addComponent(new AABBBox(magic, magic.getWorldPos(), list, 0));
                        entities.add(magic);
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

    public void render(Screen screen) {
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                if (!tileInBounds(x, y) || tileSpriteIndices[x + y * levelWidth] == -1 || getTileID(x, y) == 0) continue;
                Tile t = Tile.getTileByID(getTileID(x, y));
                screen.renderSprite(Game.getInstance().getScreen().getOffset().add(new Vector(x * Game.TILE_SIZE, y * Game.TILE_SIZE)), t.getSprites()[tileSpriteIndices[x + y * levelWidth]]);
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
