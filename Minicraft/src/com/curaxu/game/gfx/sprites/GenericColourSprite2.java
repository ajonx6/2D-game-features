package com.curaxu.game.gfx.sprites;

public class GenericColourSprite2 extends Sprite2 {
	public Sprite2 original;

	public int[] originalColours;

	public GenericColourSprite2(String path, int... colours) {
		super(path);

		original = new Sprite2(path);
		originalColours = new int[colours.length / 2];
		for (int i = 0; i < colours.length; i += 2) {
			originalColours[i / 2] = colours[i];
			for (int p = 0; p < pixels.length; p++) {
				if (pixels[p] == colours[i]) pixels[p] = colours[i + 1];
			}
		}
	}

	public void changeColour(int fromIndex, int to) {
		int from = originalColours[fromIndex];
		for (int p = 0; p < pixels.length; p++) {
			if (original.pixels[p] == from) pixels[p] = to;
		}
	}
}