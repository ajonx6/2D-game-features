package com.curaxu.game.entity.components;

import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;

public class CanMoveComponent extends Component {
    private double moveSpeed;
    private double currentMoveSpeed;
    private Vector direction = new Vector();

    public CanMoveComponent(Entity entity, double moveSpeed) {
        super(entity);
        this.moveSpeed = moveSpeed;
        this.currentMoveSpeed = moveSpeed;
    }

    public void move(double delta) {
        entity.worldPos = entity.worldPos.add(direction.mul(currentMoveSpeed).mul(delta));
    }

    public void tick(double delta) {
        move(delta);
    }

    public void render(Screen screen) {}

    public double getDefaultSpeed() {
        return moveSpeed;
    }

    public double getCurrentSpeed() {
        return currentMoveSpeed;
    }

    public void setMoveSpeed(double currentMoveSpeed) {
        this.currentMoveSpeed = currentMoveSpeed;
    }

    public void setDx(double dx) {
        this.direction = new Vector(dx, direction.getY());
    }

    public void setDy(double dy) {
        this.direction = new Vector(direction.getX(), dy);
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector v) {
        this.direction = new Vector(v);
    }

    public String getName() {
        return "CanMove";
    }
}
