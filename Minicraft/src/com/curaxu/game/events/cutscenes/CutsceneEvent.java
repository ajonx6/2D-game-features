package com.curaxu.game.events.cutscenes;

public abstract class CutsceneEvent {
	public abstract void start();

	public abstract boolean tick(double delta);
}