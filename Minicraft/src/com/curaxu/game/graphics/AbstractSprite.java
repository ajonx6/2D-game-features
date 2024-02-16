package com.curaxu.game.graphics;

import com.curaxu.game.Vector;

public interface AbstractSprite {
	void tick(double delta);

	int getWidth();

	int getHeight();

	int[] getPixels();

	void reset();

	AbstractSprite copy();
}