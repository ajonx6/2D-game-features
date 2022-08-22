package com.curaxu.game.entity;

import com.curaxu.game.level.Tile;
import com.curaxu.game.graphics.Screen;

public class CanSwim extends Component {
    private SpriteList sprites;
    private CanMove canMove;
    private double swimSpeed;

    public CanSwim(Entity entity, double swimSpeed) {
        super(entity, "SpriteList", "CanMove");
        this.sprites = (SpriteList) entity.getComponent("SpriteList");
        this.canMove = (CanMove) entity.getComponent("CanMove");
        this.swimSpeed = swimSpeed;
    }

    public void tick(double delta) {
        if (entity.tileIDunderneath == Tile.WATER.getId()) {
            sprites.setIndex(1);
            canMove.setMoveSpeed(swimSpeed);
        } else {
            sprites.setIndex(0);
            canMove.setMoveSpeed(canMove.getDefaultSpeed());
        }
    }

    public void render(Screen screen) {}

    public String getName() {
        return "CanSwim";
    }
}
