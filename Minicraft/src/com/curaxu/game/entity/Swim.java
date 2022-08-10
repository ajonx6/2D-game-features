package com.curaxu.game.entity;

import com.curaxu.game.level.Tile;
import com.curaxu.game.graphics.Screen;

public class Swim extends Component {
    private SpriteList sprites;
    private Move canMove;

    public Swim(Entity entity) {
        super(entity, "SpriteList", "CanMove");
        this.sprites = (SpriteList) entity.getComponent("SpriteList");
        this.canMove = (Move) entity.getComponent("CanMove");
    }

    public void tick() {
        if (entity.standing != null && entity.standing.getID() == Tile.WATER_ID) {
            sprites.setIndex(1);
            canMove.setMoveSpeed(1);
        } else {
            sprites.setIndex(0);
            canMove.setMoveSpeed(2);
        }
    }

    public void render(Screen screen) {}

    public String getName() {
        return "CanSwim";
    }
}
