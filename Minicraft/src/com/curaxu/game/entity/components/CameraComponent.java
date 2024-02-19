package com.curaxu.game.entity.components;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;

public class CameraComponent extends Component {
	public static CameraComponent activeCamera;
	public static Vector cameraPosition = new Vector();
	public static Vector offset = new Vector();

	private boolean active;

	public CameraComponent(Entity entity, boolean active) {
		super(entity);
		this.active = active;
		if (active && activeCamera == null) {
			activeCamera = this;
			cameraPosition = entity.getCenterWorldPos().add(offset);
		}
	}

	public void tick(double delta) {
		if (active) {
			cameraPosition = entity.getCenterWorldPos().add(offset);
			Game.getInstance().getScreen().setOffset(new Vector(Game.PIXEL_WIDTH / 2.0, Game.PIXEL_HEIGHT / 2.0).sub(cameraPosition));
		}
	}

	public void render(Screen screen) {}

	public boolean isActive() {
		return active;
	}

	public void activate() {
		if (activeCamera != null) return;
		activeCamera = this;
		active = true;
	}

	public void deactivate() {
		if (activeCamera == this) activeCamera = null;
		active = false;
	}

	public String getName() {
		return "Camera";
	}
}
