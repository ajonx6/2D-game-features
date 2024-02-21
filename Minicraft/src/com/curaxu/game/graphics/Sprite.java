package com.curaxu.game.graphics;

import com.curaxu.game.Pair;
import com.curaxu.game.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Sprite implements AbstractSprite {
	public static final int COLOR0 = 0xFF000000;
	public static final int COLOR1 = 0xFF444444;
	public static final int COLOR2 = 0xFF888888;
	public static final int COLOR3 = 0xFFCCCCCC;
	public static final int COLOR4 = 0xFFFFFFFF;

	private int width, height;
	private int[] pixels;

	public Sprite(String path) {
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

	public Sprite(int width, int height, int[] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
	}

	public Sprite(int colour, int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		Arrays.fill(pixels, colour);
	}

	public Sprite coloured(int c1, int c2, int c3, int c4) {
		int[] pxs = new int[pixels.length];

		for (int i = 0; i < pxs.length; i++) {
			if (pixels[i] == COLOR0) pxs[i] = 0x0;
			else if (pixels[i] == COLOR1) pxs[i] = c1;
			else if (pixels[i] == COLOR2) pxs[i] = c2;
			else if (pixels[i] == COLOR3) pxs[i] = c3;
			else if (pixels[i] == COLOR4) pxs[i] = c4;
		}

		return new Sprite(width, height, pxs);
	}

	public Sprite colour(HashMap<Integer, Integer> colours) {
		int[] newPixels = new int[pixels.length];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (colours.containsKey(pixels[x + y * width])) {
					newPixels[x + y * width] = colours.get(pixels[x + y * width]);
				} else {
					newPixels[x + y * width] = pixels[x + y * width];
				}
			}
		}
		return new Sprite(width, height, newPixels);
	}

	public Sprite cut(int x, int y, int w, int h) {
		int[] newPixels = new int[w * h];
		for (int yy = 0; yy < h; yy++) {
			int yp = yy + y;
			for (int xx = 0; xx < w; xx++) {
				int xp = xx + x;
				newPixels[xx + yy * w] = pixels[xp + yp * width];
			}
		}
		return new Sprite(w, h, newPixels);
	}

	public static Sprite createCircle(int radius, int colour) {
		int[] pixels = new int[radius * 2 * radius * 2];
		for (int y = -radius; y < radius; y++) {
			for (int x = -radius; x < radius; x++) {
				if (Math.sqrt(x * x + y * y) <= radius) pixels[(x + radius) + (y + radius) * radius * 2] = colour;
			}
		}
		return new Sprite(radius * 2, radius * 2, pixels);
	}

	public void tick(double delta) {}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void reset() {}

	public AbstractSprite copy() {
		return new Sprite(width, height, pixels);
	}
}
