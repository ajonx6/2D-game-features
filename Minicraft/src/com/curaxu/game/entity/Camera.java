package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;

public class Camera extends Component {
    public static Camera activeCamera;
    private boolean active;

    public Camera(Entity entity, boolean active) {
        super(entity);
        this.active = active;
        if (active && activeCamera == null)  activeCamera = this;
    }

    public void tick(double delta) {
        if (active) Game.getInstance().getScreen().setOffset(new Vector(Game.PIXEL_WIDTH / 2.0, Game.PIXEL_HEIGHT / 2.0).sub(entity.getCenterWorldPos()));
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
