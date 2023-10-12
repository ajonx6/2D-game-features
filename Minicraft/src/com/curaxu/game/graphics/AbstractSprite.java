package com.curaxu.game.graphics;

public interface AbstractSprite {
	void tick(double delta);

	int getWidth();

	int getHeight();

	int[] getPixels();

	AbstractSprite copy();
	
	void reset();
}