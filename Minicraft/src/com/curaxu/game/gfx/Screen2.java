package com.curaxu.game.gfx;

public class Screen2 {
	private int width, height;
	private int[] pixels;

	public Screen2(int width, int height) {
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

	public void renderRect(int x, int y, int w, int h, int col) {
		for (int yy = 0; yy < h; yy++) {
			int yp = y + yy;
			for (int xx = 0; xx < w; xx++) {
				int xp = x + xx;
				if (outOfBounds(xp, yp, false)) continue;
				else setPixel(xp + yp * width, col);
			}
		}
	}
	public boolean outOfBounds(int xp, int yp, boolean useOffsets) {
		return xp < 0 || yp < 0 || xp >= width || yp >= height;
	}

	public void clear() {
		clear(0);
	}

	public void clear(int colour) {
		for (int i = 0; i < pixels.length; i++) {
			setPixel(i, colour | 0xff000000);
		}
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
}