package com.curaxu.game.graphics;

import java.util.ArrayList;
import java.util.List;

import com.curaxu.game.Vector;
import com.curaxu.game.graphics.SpriteSheet;
import com.curaxu.game.util.Timer;

public class AnimatedSprite implements AbstractSprite {
	public Sprite[] sprites;
	public List<Timer> timers = new ArrayList<>();
	public Sprite sprite;
	public int currentIndex = 0;
	public int length;

	public boolean valid = true;
	public boolean remove = false;
	public int loops = -1;
	public int loopsDone = 0;

	public AnimatedSprite(SpriteSheet sheet) {
		this.sprites = new Sprite[sheet.getSprites().length];
		System.arraycopy(sheet.getSprites(), 0, this.sprites, 0, sheet.getSprites().length);
		this.sprite = this.sprites[currentIndex];
		this.length = this.sprites.length;
	}

	public AnimatedSprite(Sprite... sprites) {
		this.sprites = new Sprite[sprites.length];
		System.arraycopy(sprites, 0, this.sprites, 0, sprites.length);
		this.sprite = this.sprites[currentIndex];
		this.length = this.sprites.length;
	}

	public AnimatedSprite setTimes(double... timers) {
		this.timers.clear();
		for (double timer : timers) {
			this.timers.add(new Timer(timer, false));
		}
		return this;
	}

	public AnimatedSprite removeAfterNLoops(int loops) {
		this.loops = loops;
		return this;
	}

	public void reset() {
		for (Timer t : timers) {
			t.setCurrTime(0);
		}
		currentIndex = 0;
		sprite = sprites[currentIndex];
	}

	public void tick(double delta) {
		if (valid) {
			if (timers.get(currentIndex).tick(delta)) {
				timers.get(currentIndex++).setCurrTime(0);
				if (currentIndex >= length) {
					currentIndex = 0;
					if (loops != -1) {
						loopsDone++;
						if (loopsDone >= loops) {
							remove = true;
							valid = false;
							return;
						}
					}
				}
				sprite = sprites[currentIndex];
			}
		}
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

	public AbstractSprite copy() {
		double[] ds = new double[timers.size()];
		int di = 0;
		for (Timer t : timers) ds[di++] = t.getTimer();

		AnimatedSprite s = new AnimatedSprite(sprites).setTimes(ds);
		s.loops = loops;
		return s;
	}
}