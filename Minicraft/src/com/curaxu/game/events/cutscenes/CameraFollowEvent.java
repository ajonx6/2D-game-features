package com.curaxu.game.events.cutscenes;

import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.CameraComponent;

public class CameraFollowEvent extends CutsceneEvent {
	private Entity toFollow, currentlyOn;
	private double followTime;
	private Vector oldOffset;

	private double followTimer = 0;

	public CameraFollowEvent(Entity toFollow, double followTime) {
		this.toFollow = toFollow;
		this.followTime = followTime;
	}

	public void start() {
		currentlyOn = CameraComponent.activeCamera.getEntity();
		((CameraComponent) currentlyOn.getComponent("Camera")).deactivate();
		((CameraComponent) toFollow.getComponent("Camera")).activate();

		oldOffset = CameraComponent.offset;
		CameraComponent.offset = new Vector();
	}

	public boolean tick(double delta) {
		followTimer += delta;
		if (followTimer >= followTime) {
			((CameraComponent) toFollow.getComponent("Camera")).deactivate();
			((CameraComponent) currentlyOn.getComponent("Camera")).activate();
			CameraComponent.offset = oldOffset;
			return true;
		}
		return false;
	}
}
