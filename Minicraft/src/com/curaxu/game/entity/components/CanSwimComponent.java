package com.curaxu.game.entity.components;

import com.curaxu.game.entity.Entity;
import com.curaxu.game.level.Tile;
import com.curaxu.game.graphics.Screen;

public class CanSwimComponent extends Component {
    private SpriteListComponent sprites;
    private CanMoveComponent canMove;
    private double swimSpeed;

    public CanSwimComponent(Entity entity, double swimSpeed) {
        super(entity, "SpriteList", "CanMove");
        this.sprites = (SpriteListComponent) entity.getComponent("SpriteList");
        this.canMove = (CanMoveComponent) entity.getComponent("CanMove");
        this.swimSpeed = swimSpeed;
    }

    public void tick(double delta) {
        if (entity.standing != null && entity.standing.getId() == Tile.WATER.getId()) {
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
