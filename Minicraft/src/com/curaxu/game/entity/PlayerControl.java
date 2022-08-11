package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.KeyInput;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.level.Tile;

import java.awt.event.KeyEvent;

public class PlayerControl extends Component {
    private CanMove move;
    private Vector velocity = new Vector();

    public PlayerControl(Entity entity) {
        super(entity, "SpriteList", "CanMove");
        this.move = (CanMove) entity.getComponent("CanMove");
    }

    public void move(double delta) {
        double movespeed = move.getCurrentSpeed();
        velocity = velocity.mul(movespeed);
        // Tile next = Game.getInstance().getLevel().getTileAtWorldPos(entity.worldPos.add(velocity));
        // if (next != null && next.getOntop() != null) {
        //     AABBBox a = (AABBBox) next.getOntop().getComponent("AABBBox");
        //     AABBBox b = (AABBBox) entity.getComponent("AABBBox");
        //     if (a != null && b != null && Collisions.collisionWithBox(a, b)) return;
        // }
        entity.worldPos = entity.worldPos.add(velocity.mul(delta));
    }

    public void tick(double delta) {
        velocity.setX(0);
        velocity.setY(0);
        if (KeyInput.isDown(KeyEvent.VK_W)) {
            velocity.setY(-1);
        } else if (KeyInput.isDown(KeyEvent.VK_S)) {
            velocity.setY(1);
        }
        if (KeyInput.isDown(KeyEvent.VK_A)) {
            velocity.setX(-1);
        }
        if (KeyInput.isDown(KeyEvent.VK_D)) {
            velocity.setX(1);
        }

        if (!velocity.isZero()) move(delta);
    }

    public void render(Screen screen) {}

    public String getName() {
        return "PlayerControl";
    }
}
