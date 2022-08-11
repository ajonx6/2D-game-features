package com.curaxu.game.util;

public class Timer {
	private double timer;
	private double ticks = 0;
	private boolean loop;

	public Timer(double timer, boolean loop) {
		this.timer = timer;
		this.loop = false;
	}

	public boolean tick(double delta) {
		if (ticks >= timer) {
			if (loop) ticks = 0;
			return true;
		}
		ticks += delta;
		return false;
	}

	public double percent() {
		return ticks / timer;
	}

	public void setTime(double time) {
		this.timer = time;
		this.ticks = 0;
	}
}