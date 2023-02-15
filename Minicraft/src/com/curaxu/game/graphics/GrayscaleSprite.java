package com.curaxu.game.graphics;

import com.curaxu.game.Game;

public class GrayscaleSprite {
    private int width, height;
    private int[] pixels;

    public GrayscaleSprite(int[] pixels, int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[Game.TILE_SIZE * Game.TILE_SIZE];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public Sprite coloured(int[] colors) {
        return new Sprite(this, colors[0], colors[1], colors[2], colors[3]);
    }
}
