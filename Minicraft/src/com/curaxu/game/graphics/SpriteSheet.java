package com.curaxu.game.graphics;

import com.curaxu.game.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {
    private GrayscaleSprite[] sprites;
    private int width = 0;

    public SpriteSheet(String path) {
        try {
            BufferedImage image = ImageIO.read(new File("res/" + path + ".png"));
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

            this.sprites = new GrayscaleSprite[pixels.length / (Game.TILE_SIZE * Game.TILE_SIZE)];
            this.width = width / Game.TILE_SIZE;

            for (int y = 0; y < height; y += Game.TILE_SIZE) {
                for (int x = 0; x < width; x += Game.TILE_SIZE) {
                    int[] ps = new int[Game.TILE_SIZE * Game.TILE_SIZE];
                    for (int yy = 0; yy < Game.TILE_SIZE; yy++) {
                        for (int xx = 0; xx < Game.TILE_SIZE; xx++) {
                            ps[xx + yy * Game.TILE_SIZE] = pixels[(x + xx) + (y + yy) * width];
                        }
                    }
                    GrayscaleSprite s = new GrayscaleSprite(ps);
                    sprites[(x / Game.TILE_SIZE) + (y / Game.TILE_SIZE) * (width / Game.TILE_SIZE)] = s;
                }
            }

        } catch (IOException ioe) {
            System.err.println("Cannot find image: " + path + ".png");
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    public GrayscaleSprite getSprite(int i) {
        return sprites[i];
    }

    public GrayscaleSprite getSprite(int x, int y) {
        return sprites[x + y * width];
    }
}
