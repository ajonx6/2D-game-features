package com.curaxu.game.entity.components;

import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;

public class MoveTowardsComponent extends Component {
    private MoveComponent move;
    private Entity target;

    public MoveTowardsComponent(Entity entity, Entity target) {
        super(entity, "SpriteList", "Move");
        this.move = (MoveComponent) entity.getComponent("Move");
        this.target = target;
    }

    public void move(double delta, Vector toTarget) {
        double movespeed = move.getCurrentMoveSpeed();
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
