package com.curaxu.game.events.cutscenes;

import com.curaxu.game.Maths;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.components.CameraComponent;

public class CameraMovementEvent extends CutsceneEvent {
	private Vector offsetStart;
	private Vector offsetEnd;
	private double duration;

	private Vector diff;
	private double progress = 0.0;

	public CameraMovementEvent(Vector offsetStart, Vector offsetEnd, double duration) {
		this.offsetStart = offsetStart;
		this.offsetEnd = offsetEnd;
		this.diff = offsetEnd.sub(offsetStart);
		this.duration = duration;
	}

	public void start() {
		CameraComponent.offset = offsetStart;
	}
	
	public boolean tick(double delta) {
		double stepSize = delta / duration;
		progress += stepSize;
		if (progress > 1.0) progress = 1.0;
		CameraComponent.offset = offsetStart.add(diff.mul(Maths.easeInOutQuad(progress)));// CameraComponent.offset.add(diff.mul(stepSize));
		return progress == 1.0;
	}
}