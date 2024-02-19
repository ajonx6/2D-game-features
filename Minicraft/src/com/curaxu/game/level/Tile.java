package com.curaxu.game.level;

import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheet;
import com.curaxu.game.graphics.SpriteSheets;

public enum Tile {
	GRASS(1, 0),
	WATER(2, 4),
	SAND(3, 8);

	public static final int NUM_TILE_TYPES = 16;

	private int id;
	private Sprite[] sprites;

	Tile(int id, int x) {
		this.id = id;

		this.sprites = new Sprite[NUM_TILE_TYPES];
		int index = 0;
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 1, 1);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 2, 1);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 0, 1);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 1, 3);

		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 1, 2);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 2, 2);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 0, 2);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 3, 0);

		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 1, 0);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 2, 0);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 0, 0);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 3, 1);

		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 2, 3);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 3, 2);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 3, 3);
		sprites[index++] = SpriteSheets.TILE_SHEET.getSprite(x + 0, 3);
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