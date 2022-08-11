package com.curaxu.game.entity;

import com.curaxu.game.graphics.Screen;

public class CanMove extends Component {
    private double moveSpeed;
    private double currentMoveSpeed;

    public CanMove(Entity entity, double moveSpeed) {
        super(entity);
        this.moveSpeed = moveSpeed;
        this.currentMoveSpeed = moveSpeed;
    }

    public void tick(double delta) {}

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

    public String getName() {
        return "CanMove";
    }
}
