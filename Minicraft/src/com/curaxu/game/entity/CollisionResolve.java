package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.level.Level;

public abstract class CollisionResolve extends Component {
    private String tag;
    private AABBBox aabb;

    public CollisionResolve(Entity entity, String tag) {
        super(entity, "AABBBox");
        this.tag = tag;
        this.aabb = (AABBBox) entity.getComponent("AABBBox");
    }

    public abstract void onCollision();

    public void tick(double delta) {
        onCollision();
    }

    public void render(Screen screen) {

    }

    public String getTag() {
        return tag;
    }

    public AABBBox getAABBBox() {
        return aabb;
    }

    public String getName() {
        return "CollisionResolver";
    }
}