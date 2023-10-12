package com.curaxu.game.graphics;

import java.util.ArrayList;
import java.util.List;

import com.curaxu.game.graphics.SpriteSheet;
import com.curaxu.game.util.Timer;

public class AnimatedSprite implements AbstractSprite {
	public SpriteSheet sheet;
	public List<Timer> timers = new ArrayList<>();
	public Sprite sprite;
	public int currentIndex = 0;
	public int length;

	public boolean valid = true;
	public boolean remove = false;
	public int loops = -1;
	public int loopsDone = 0;

	public AbstractSprite copy() {
		double[] ds = new double[timers.size()];
		int di = 0;
		for (Timer t : timers) ds[di++] = t.getTimer();
		
		AnimatedSprite s = new AnimatedSprite(sheet, ds);
		s.loops = loops;
		return s;
	}

	public AnimatedSprite(SpriteSheet sheet, double... timers) {
		this.sheet = sheet;
		for (double timer : timers) {
			this.timers.add(new Timer(timer, false));
		}
		this.sprite = sheet.getSprite(currentIndex);
		this.length = sheet.getSprites().length;

		if (this.timers.size() != length) {
			System.err.println("Invalid Animation! Sheet length: " + length + ", number of timers: " + this.timers.size());
			valid = false;
		}
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
		sprite = sheet.getSprite(currentIndex);
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
				sprite = sheet.getSprite(currentIndex);
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
}