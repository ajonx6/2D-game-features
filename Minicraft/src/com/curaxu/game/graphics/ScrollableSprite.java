package com.curaxu.game.graphics;

import com.curaxu.game.Vector;
import com.curaxu.game.util.Timer;

import java.util.ArrayList;
import java.util.List;

public class ScrollableSprite implements AbstractSprite {
	public Sprite sprite;
	public double scrollSpeedX, scrollSpeedY;

	public double offsetX = 0, offsetY = 0;

	public ScrollableSprite(Sprite sprite, double scrollSpeedX, double scrollSpeedY) {
		this.sprite = sprite;
		this.scrollSpeedX = scrollSpeedX;
		this.scrollSpeedY = scrollSpeedY;
	}

	public void tick(double delta) {
		offsetX += scrollSpeedX * delta;
		offsetY += scrollSpeedY * delta;
		// System.out.println(offsetX + "," + offsetY);
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}

	public int[] getPixels() {
		return sprite.getPixels();
	}

	public void reset() {
		offsetX = 0;
		offsetY = 0;
	}

	public AbstractSprite copy() {
		return new ScrollableSprite(sprite, scrollSpeedX, scrollSpeedY);
	}
}