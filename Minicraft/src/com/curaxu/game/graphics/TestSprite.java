package com.curaxu.game.graphics;

import java.util.Random;

public class TestSprite {
    public static final int[] GRAYSCALE_COLORS = {0x000000, 0x444444, 0x888888, 0xCCCCCC, 0xFFFFFF};
    public static final Random RANDOM = new Random();

    private GrayscaleSprite gsprite;
    private Sprite sprite;

    public TestSprite() {
        int[] grayscale = generateGrayscale();

        gsprite = new GrayscaleSprite(grayscale);
        sprite = new Sprite(gsprite, 0xFFFF00FF, 0XFFFFFF00, 0XFF00FFFF, 0XFFFF0000);
    }

    public int[] generateGrayscale() {
        int[] pixels = new int[256];
        for (int i = 0; i < pixels.length; i++) {
            int r = RANDOM.nextInt(GRAYSCALE_COLORS.length);
            int c = GRAYSCALE_COLORS[r];
            pixels[i] = 0xff << 24 | c << 16 | c << 8 | c;
        }
        return pixels;
    }

    public GrayscaleSprite getGraySprite() {
        return gsprite;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
