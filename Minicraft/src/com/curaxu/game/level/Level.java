package com.curaxu.game.level;

import com.curaxu.game.Game;
import com.curaxu.game.graphics.*;
import com.curaxu.game.util.SimplexNoise;

import java.util.Random;

public class Level {
    private Random random = new Random();

    private int levelWidth, levelHeight;
    private Tile[] tiles;

    public Level(int levelWidth, int levelHeight, SpriteSheet sheet) {
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.tiles = new Tile[levelWidth * levelHeight];

        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                double s = SimplexNoise.noise((double) x / 11.0, (double) y / 11.0) / 2.0 + 0.5;
                boolean isWater = s > 0.7;
                Tile tile = isWater ? new WaterTile(x, y, this) : new GrassTile(x, y, this);
                tiles[x + y * levelWidth] = tile;
            }
        }

        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                Tile t = getTile(x, y);
                t.setSprite();
                if (t instanceof GrassTile) {
                    if (random.nextDouble() > 0.95) {
                        ((GrassTile) t).hasTree = true;
                    }
                }
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
        int up = (int) (t.getY() + Screen.yOffset);
        int down = (int) (t.getY() + Game.TILE_SIZE + Screen.yOffset);
        int left = (int) (t.getX() + Screen.xOffset);
        int right = (int) (t.getX() + Game.TILE_SIZE + Screen.xOffset);

        return up < Game.PIXEL_HEIGHT && down >= 0 && left < Game.PIXEL_WIDTH && right >= 0;
    }

    public void render(Screen screen) {
        for (Tile t : tiles) {
            if (tileInBounds(screen, t)) t.render(screen);
        }
    }

    public int getWidth() {
        return levelWidth;
    }

    public int getHeight() {
        return levelHeight;
    }
}
