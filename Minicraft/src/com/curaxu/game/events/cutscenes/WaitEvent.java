package com.curaxu.game.events.cutscenes;

public class WaitEvent extends CutsceneEvent {
	private double duration;
	private double progress = 0.0;

	public WaitEvent(double duration) {
		this.duration = duration;
	}

	public void start() {}

	public boolean tick(double delta) {
		progress += delta / duration;
		return progress >= 1.0;
	}
}