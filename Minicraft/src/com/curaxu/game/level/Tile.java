package com.curaxu.game.level;

import com.curaxu.game.graphics.*;
import com.curaxu.game.Game;
import disused.GrayscaleSprite;

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
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 1 + 1 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 1, y + 1).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 2 + 1 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 2, y + 1).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 0 + 1 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 0, y + 1).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 1 + 3 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 1, y + 3).coloured(c1, c2, c3, c4);

        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 1 + 2 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 1, y + 2).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 2 + 2 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 2, y + 2).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 0 + 2 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 0, y + 2).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 3 + 0 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 3, y + 0).coloured(c1, c2, c3, c4);

        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 1 + 0 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 1, y + 0).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 2 + 0 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 2, y + 0).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 0 + 0 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 0, y + 0).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 3 + 1 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 3, y + 1).coloured(c1, c2, c3, c4);

        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 2 + 3 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 2, y + 3).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 3 + 2 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 3, y + 2).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 3 + 3 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index++] = SpriteSheets.black_white_sprites.getSprite(x + 3, y + 3).coloured(c1, c2, c3, c4);
        // grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 0 + 3 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
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