package com.curaxu.game.graphics;

import com.curaxu.game.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {
    private int width, height;
    private int[] pixels;

    public SpriteSheet(String path) {
        try {
            BufferedImage image = ImageIO.read(new File("res/" + path + ".png"));
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.pixels = image.getRGB(0, 0, width, height, null, 0, width);
        } catch (IOException ioe) {
            System.err.println("Cannot find image: " + path + ".png");
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    public GrayscaleSprite getSprite(int i, int w, int h) {
        return getSprite(i % (width / Game.TILE_SIZE), i / (width / Game.TILE_SIZE), w, h);
    }

    public GrayscaleSprite getSprite(int x, int y, int w, int h) {
        int[] pixels = new int[w * Game.TILE_SIZE * h * Game.TILE_SIZE];

        for (int yy = 0; yy < h * Game.TILE_SIZE; yy++) {
            int yp = y * Game.TILE_SIZE + yy;
            for (int xx = 0; xx < w * Game.TILE_SIZE; xx++) {
                int xp = x * Game.TILE_SIZE + xx;
                pixels[xx + yy * w * Game.TILE_SIZE] = this.pixels[xp + yp * width];
            }
        }

        return new GrayscaleSprite(pixels, w * Game.TILE_SIZE, h * Game.TILE_SIZE);
    }
}
