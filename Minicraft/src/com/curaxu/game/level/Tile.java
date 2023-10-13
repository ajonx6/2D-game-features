package com.curaxu.game.level;

import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheets;

public enum Tile {    
    GRASS(1, 0, 1, 0XFF337A45, 0XFF6DB767, 0xFF2B5636, 0),
    WATER(2, 4, 1, 0XFF3784B5, 0XFF6996B2, 0xFF54788E, 0);

    public static final int NUM_TILE_TYPES = 16;
    
    private int id;
    private int x, y;
    private Sprite[] sprites;

    Tile(int id, int x, int y, int c1, int c2, int c3, int c4) {
        this.id = id;
        this.x = x;
        this.y = y;
        
        this.sprites = new Sprite[NUM_TILE_TYPES];
        int index = 0;
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 1, y + 1).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 2, y + 1).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 0, y + 1).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 1, y + 3).coloured(c1, c2, c3, c4);

        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 1, y + 2).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 2, y + 2).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 0, y + 2).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 3, y + 0).coloured(c1, c2, c3, c4);

        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 1, y + 0).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 2, y + 0).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 0, y + 0).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 3, y + 1).coloured(c1, c2, c3, c4);

        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 2, y + 3).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 3, y + 2).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 3, y + 3).coloured(c1, c2, c3, c4);
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 0, y + 3).coloured(c1, c2, c3, c4);
    }

    public static Tile getTileByID(int tileID) {
        for (Tile t : values()) {
            if (t.getId() == tileID) return t;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public Sprite[] getSprites() {
        return sprites;
    }
}