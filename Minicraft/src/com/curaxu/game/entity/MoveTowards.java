package com.curaxu.game.entity;

import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;

public class MoveTowards extends Component {
    private CanMove move;
    private Entity target;

    public MoveTowards(Entity entity, Entity target) {
        super(entity, "SpriteList", "CanMove");
        this.move = (CanMove) entity.getComponent("CanMove");
        this.target = target;
    }

    public void move(double delta, Vector toTarget) {
        double movespeed = move.getCurrentSpeed();
        entity.worldPos = entity.worldPos.add(toTarget.mul(movespeed).mul(delta));
    }

    public void tick(double delta) {
        if (target == null) return;
        Vector toTarget = target.getCenterWorldPos().sub(entity.getCenterWorldPos()).normalize();
        move(delta, toTarget);
    }

    public void render(Screen screen) {}

    public String getName() {
        return "PlayerControl";
    }
}
