package com.curaxu.game.graphics;

public class SpriteSheet {
	private Sprite sprite;
	private int width, height;
	private int spriteWidth, spriteHeight;
	private int numSpritesX, numSpritesY;
	private int[] pixels;
	private Sprite[] sprites;

	public SpriteSheet(String path, int spriteWidth, int spriteHeight) {
		this.sprite = new Sprite(path);
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.numSpritesX = width / spriteWidth;
		this.numSpritesY = height / spriteHeight;
		this.pixels = new int[width * height];
		System.arraycopy(sprite.getPixels(), 0, this.pixels, 0, sprite.getPixels().length);
		
		process();
	}

	public void process() {
		sprites = new Sprite[numSpritesX * numSpritesY];
		
		for (int sy = 0; sy < numSpritesY; sy++) {
			for (int sx = 0; sx < numSpritesX; sx++) {
				int[] spritePixels = new int[spriteWidth * spriteHeight];
				for (int y = 0; y < spriteHeight; y++) {
					int yp = y + sy * spriteHeight;
					for (int x = 0; x < spriteWidth; x++) {
						int xp = x + sx * spriteWidth;
						spritePixels[x + y * spriteWidth] = pixels[xp + yp * width];
					}
				}
				sprites[sx + sy * numSpritesX] = new Sprite(spriteWidth, spriteHeight, spritePixels);
			}
		}
	}

	// public GrayscaleSprite getGraySprite(int i, int w, int h) {
	// 	return getGraySprite(i % (width / Game.TILE_SIZE), i / (width / Game.TILE_SIZE), w, h);
	// }
	//
	// public GrayscaleSprite getGraySprite(int x, int y, int w, int h) {
	// 	int[] pixels = new int[w * Game.TILE_SIZE * h * Game.TILE_SIZE];
	//
	// 	for (int yy = 0; yy < h * Game.TILE_SIZE; yy++) {
	// 		int yp = y * Game.TILE_SIZE + yy;
	// 		for (int xx = 0; xx < w * Game.TILE_SIZE; xx++) {
	// 			int xp = x * Game.TILE_SIZE + xx;
	// 			pixels[xx + yy * w * Game.TILE_SIZE] = this.pixels[xp + yp * width];
	// 		}
	// 	}
	//
	// 	return new GrayscaleSprite(pixels, w * Game.TILE_SIZE, h * Game.TILE_SIZE);
	// }
	//
	// public GrayscaleSprite getGraySpriteCustomWidthHeight(int i, int w, int h) {
	// 	return getGraySpriteCustomWidthHeight(i % (width / w), i / (width / w), w, h);
	// }
	//
	// public GrayscaleSprite getGraySpriteCustomWidthHeight(int x, int y, int w, int h) {
	// 	int[] pixels = new int[w * h];
	//
	// 	for (int yy = 0; yy < h; yy++) {
	// 		int yp = y * h + yy;
	// 		for (int xx = 0; xx < w; xx++) {
	// 			int xp = x * w + xx;
	// 			pixels[xx + yy * w] = this.pixels[xp + yp * width];
	// 		}
	// 	}
	//
	// 	return new GrayscaleSprite(pixels, w, h);
	// }

	public Sprite getSprite(int i) {
		return sprites[i];
	}
	
	public Sprite getSprite(int x, int y) {
		return sprites[x + y * numSpritesX];
	}

	// public Sprite getSprite(int x, int y, int w, int h) {
	// 	int[] pixels = new int[w * Game.TILE_SIZE * h * Game.TILE_SIZE];
	//
	// 	for (int yy = 0; yy < h * Game.TILE_SIZE; yy++) {
	// 		int yp = y * Game.TILE_SIZE + yy;
	// 		for (int xx = 0; xx < w * Game.TILE_SIZE; xx++) {
	// 			int xp = x * Game.TILE_SIZE + xx;
	// 			pixels[xx + yy * w * Game.TILE_SIZE] = this.pixels[xp + yp * width];
	// 		}
	// 	}
	//
	// 	return new Sprite(w * Game.TILE_SIZE, h * Game.TILE_SIZE, pixels);
	// }

	public Sprite getSprite() {
		return sprite;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public int getNumSpritesX() {
		return numSpritesX;
	}

	public int getNumSpritesY() {
		return numSpritesY;
	}

	public Sprite[] getSprites() {
		return sprites;
	}
}
