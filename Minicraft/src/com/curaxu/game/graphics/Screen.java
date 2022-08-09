package com.curaxu.game.graphics;

import com.curaxu.game.Game;

public class Screen {
    public static int xOffset = 0, yOffset = 0;

    private int width, height;
    private int[] pixels;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }

    public void setPixel(int i, int col) {
        double alpha = (double) ((col >> 24) & 0xff) / 255.0;
        if (alpha == 1.0) {
            pixels[i] = col;
        } else if (alpha > 0.0) {
            int oldColour = pixels[i];
            int newRed = ((oldColour >> 16) & 0xff) - (int) ((((oldColour >> 16) & 0xff) - ((col >> 16) & 0xff)) * alpha);
            int newGreen = ((oldColour >> 8) & 0xff) - (int) ((((oldColour >> 8) & 0xff) - ((col >> 8) & 0xff)) * alpha);
            int newBlue = (oldColour & 0xff) - (int) (((oldColour & 0xff) - (col & 0xff)) * alpha);
            pixels[i] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
        }
    }

    public void renderGraySprite(int x, int y, GrayscaleSprite sprite) {
        int[] pixels = sprite.getPixels();
        for (int yy = 0; yy < Game.TILE_SIZE; yy++) {
            int yp = y + yy + yOffset;
            for (int xx = 0; xx < Game.TILE_SIZE; xx++) {
                int xp = x + xx + xOffset;
                if (inBounds(xp, yp, false)) setPixel(xp + yp * width, pixels[xx + yy * Game.TILE_SIZE]);
            }
        }
    }

    public void renderSprite(int x, int y, Sprite sprite) {
        int[] pixels = sprite.getPixels();
        for (int yy = 0; yy < Game.TILE_SIZE; yy++) {
            int yp = y + yy + yOffset;
            for (int xx = 0; xx < Game.TILE_SIZE; xx++) {
                int xp = x + xx + xOffset;
                if (inBounds(xp, yp, false)) {
                    setPixel(xp + yp * width, pixels[xx + yy * Game.TILE_SIZE]);
                }
            }
        }
    }

    public boolean inBounds(int xp, int yp, boolean useOffsets) {
        return xp >= 0 && yp >= 0 && xp < width && yp < height;
    }

    public void clear() {
        clear(0);
    }

    public void clear(int colour) {
        for (int i = 0; i < pixels.length; i++) {
            setPixel(i, colour | 0xff000000);
        }
    }

    // public void up(double d) { yOffset+=d; }
    //
    // public void down(double d) { yOffset-=d; }
    //
    // public void left(double d) { xOffset+=d; }
    //
    // public void right(double d) { xOffset-=d; }

    // public int getXOffset() { return xOffset; }
    //
    // public int getYOffset() { return yOffset; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }
}
