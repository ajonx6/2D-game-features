package com.curaxu.game.entity.components;

import com.curaxu.game.Game;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;

import java.util.List;

public abstract class CollisionResolveComponent extends Component {
	private String tag;
	private AABBBoxComponent aabb;

	public CollisionResolveComponent(Entity entity, String tag) {
		super(entity, "AABBBox");
		this.tag = tag;
		this.aabb = (AABBBoxComponent) entity.getComponent("AABBBox");
	}

	public abstract void onCollision();

	public void tick(double delta) {
		List<Entity> collisions = Game.getInstance().level.boxEntityCollisionAll(tag, aabb);
		if (!collisions.isEmpty()) onCollision();
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