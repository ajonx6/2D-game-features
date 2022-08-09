package com.curaxu.game.graphics;

import com.curaxu.game.Game;

public class GrayscaleSprite {
    private int[] pixels;

    public GrayscaleSprite(int[] pixels) {
        this.pixels = new int[Game.TILE_SIZE * Game.TILE_SIZE];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
    }

    public int[] getPixels() {
        return pixels;
    }
}
