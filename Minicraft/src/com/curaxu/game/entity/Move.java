package com.curaxu.game.entity;

import com.curaxu.game.graphics.Screen;

public class Move extends Component {
    private int moveSpeed;

    public Move(Entity entity, int moveSpeed) {
        super(entity);
        this.moveSpeed = moveSpeed;
    }

    public void tick() {}

    public void render(Screen screen) {}

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public String getName() {
        return "CanMove";
    }
}
