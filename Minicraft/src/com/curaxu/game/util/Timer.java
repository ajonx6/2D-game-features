package com.curaxu.game.util;

public class Timer {
	private double timer;
	private double time = 0;
	private boolean loop;

	public Timer(double timer, boolean loop) {
		this.timer = timer;
		this.loop = false;
	}

	public boolean tick(double delta) {		
		if (time >= timer) {
			if (loop) time = 0;
			return true;
		}
		time += delta;
		return false;
	}

	public double percent() {
		return time / timer;
	}

	public void setTimeLength(double timeLength) {
		this.timer = timeLength;
		this.time = 0;
	}
	
	public void setCurrTime(double time) {
		this.time = time;
	}

	public double getTimer() {
		return timer;
	}
}