package com.curaxu.game.level;

import com.curaxu.game.graphics.*;
import com.curaxu.game.Game;

public enum Tile {
    GRASS(1, 8, 0XFF337A45, 0XFF6DB767, 0xFF2B5636, 0),
    WATER(2, 12, 0XFF3784B5, 0XFF6996B2, 0xFF54788E, 0);

    private int id;
    private int spritesheetX, spritesheetY, spritesheetI;
    private GrayscaleSprite[] grayscaleSprites;
    private Sprite[] sprites;

    private int[] colors;

    Tile(int id, int spritesheetI, int c1, int c2, int c3, int c4) {
        this.id = id;
        this.spritesheetX = spritesheetI % SpriteSheets.black_white_sprites.getWidth();
        this.spritesheetY = spritesheetI / SpriteSheets.black_white_sprites.getWidth();
        this.spritesheetI = spritesheetI;

        this.grayscaleSprites = new GrayscaleSprite[16];
        this.sprites = new Sprite[16];
        int index = 0;
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 1 + 1 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 2 + 1 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 0 + 1 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 1 + 3 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);

        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 1 + 2 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 2 + 2 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 0 + 2 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 3 + 0 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);

        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 1 + 0 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 2 + 0 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 0 + 0 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 3 + 1 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);

        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 2 + 3 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 3 + 2 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 3 + 3 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index++], c1, c2, c3, c4);
        grayscaleSprites[index] = SpriteSheets.black_white_sprites.getGraySprite(spritesheetI + 0 + 3 * SpriteSheets.black_white_sprites.getWidth() / Game.TILE_SIZE, 1, 1);//1 1
        sprites[index] = new Sprite(grayscaleSprites[index], c1, c2, c3, c4);

        this.colors = new int[]{c1, c2, c3, c4};
    }

    public static int[] getSheetPosFromID(int id) {
        return new int[] { values()[id].getSpritesheetX(), values()[id].getSpritesheetY()};
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

    public int getSpritesheetX() {
        return spritesheetX;
    }

    public int getSpritesheetY() {
        return spritesheetY;
    }

    public int getSpritesheetI() {
        return spritesheetI;
    }

    public int[] getColors() {
        return colors;
    }

    public GrayscaleSprite[] getGrayscaleSprites() {
        return grayscaleSprites;
    }

    public Sprite[] getSprites() {
        return sprites;
    }
}