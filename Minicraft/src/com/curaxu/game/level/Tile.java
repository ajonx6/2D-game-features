package com.curaxu.game.level;

import com.curaxu.game.graphics.SpriteSheets;
import com.curaxu.game.graphics.Sprite;

import java.util.HashMap;

public class Tile {
    public static final int NUM_SPRITES_PER_TILE = 16;
    public static final Tile GRASS = new Tile(1, 0, 0XFF337A45, 0XFF6DB767, 0xFF2B5636, 0);
    public static final Tile WATER = new Tile(2, 1, 0XFF3784B5, 0XFF6996B2, 0xFF54788E, 0);

    private static HashMap<Integer, Tile> TILES;

    private int id;
    private int[] colors;
    private Sprite[] sprites = new Sprite[NUM_SPRITES_PER_TILE];

    Tile(int id, int y, int... colors) {
        this.id = id;
        this.colors = colors;
        for (int x = 0; x < NUM_SPRITES_PER_TILE; x++) {
            sprites[x] = new Sprite(SpriteSheets.tiles_sheet.getSprite(x, y, 1, 1), colors);
        }
        if (TILES == null) TILES = new HashMap<Integer, Tile>();
        TILES.put(id, this);
    }

    public static Tile getTile(int id) {
        return TILES.get(id);
    }

    public int getId() {
        return id;
    }

    public int[] getColors() {
        return colors;
    }

    public Sprite getSpriteAtIndex(int i) {
        return sprites[i];
    }
}