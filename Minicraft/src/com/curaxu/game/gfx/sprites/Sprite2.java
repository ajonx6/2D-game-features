package com.curaxu.game.gfx.sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite2 {
	public BufferedImage image;
	public int width, height;
	public int[] pixels;
	public String path;

	public Sprite2(String path) {
		try {
			this.path = path;
			this.image = ImageIO.read(new File("res/" + path + ".png"));
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.pixels = new int[width * height];
			this.pixels = image.getRGB(0, 0, width, height, null, 0, width);
		} catch (IOException ioe) {
			System.err.println("Cannot find image: " + path + ".png");
			ioe.printStackTrace();
			System.exit(1);
		}
	}
	public Sprite2(int width, int height, int[] pix) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		System.arraycopy(pix, 0, this.pixels, 0, pix.length);
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, width, height, pixels, 0, width);
	}
}