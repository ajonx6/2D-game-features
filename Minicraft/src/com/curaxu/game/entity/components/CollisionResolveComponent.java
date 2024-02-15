package com.curaxu.game.entity.components;

import com.curaxu.game.Game;
import com.curaxu.game.entity.Collisions;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;

import java.util.HashMap;
import java.util.List;

public abstract class CollisionResolveComponent extends Component {
	private String tag;
	private AABBBoxComponent aabb;
	private boolean dynamic;

	public CollisionResolveComponent(Entity entity, String tag, boolean dynamic) {
		super(entity, "AABBBox");
		this.tag = tag;
		this.aabb = (AABBBoxComponent) entity.getComponent("AABBBox");
		this.dynamic = dynamic;
	}

	public abstract void onCollision(HashMap<Entity, Collisions.CollisionData> data, double delta);

	public abstract void noCollisions(double delta);

	public void tick(double delta) {
		HashMap<Entity, Collisions.CollisionData> collisions = Game.getInstance().level.getCollidedWithEntity(entity, tag, dynamic, delta);
		if (!collisions.isEmpty()) onCollision(collisions, delta);
		else noCollisions(delta);
	}

	public void render(Screen screen) {}

	public String getTag() {
		return tag;
	}

	public AABBBoxComponent getAABBBox() {
		return aabb;
	}

	public String getName() {
		return "CollisionResolver";
	}
}