package com.curaxu.game.entity.components;

import com.curaxu.game.Game;
import com.curaxu.game.Settings;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.AABBBox;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.AbstractSprite;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;

public class AABBBoxComponent extends Component {
	private AABBBox box;

	private int uncollideColour = 0x77ff00ff;
	private int collideColour = 0x7700ffff;
	private SpriteListComponent spriteListComponent;
	private boolean hasCollided = false;

	public AABBBoxComponent(Entity entity, Vector position, int width, int height) {
		super(entity, "SpriteList");
		spriteListComponent = (SpriteListComponent) entity.getComponent("SpriteList");
		box = new AABBBox(position, width, height);
	}

	public AABBBoxComponent(Entity entity, Vector position, AbstractSprite sprite) {
		super(entity, "SpriteList");
		spriteListComponent = (SpriteListComponent) entity.getComponent("SpriteList");
		box = new AABBBox(position, sprite);
	}

	public AABBBoxComponent(Entity entity, Vector position) {
		super(entity, "SpriteList");
		spriteListComponent = (SpriteListComponent) entity.getComponent("SpriteList");
		box = new AABBBox(position, spriteListComponent.getCurrentSprite());
	}

	public void tick(double delta) {
		box.setPosition(entity.getWorldPos());
	}

	public void render(Screen screen) {
		if (Settings.debugMode) {
			screen.renderRect(box.getPosition().add(Game.getInstance().getScreen().getOffset()), (int) box.getWidth(), (int) box.getHeight(), hasCollided ? collideColour : uncollideColour);
		}
	}

	public boolean hasCollided() {
		return hasCollided;
	}

	public void hasCollided(boolean hasCollided) {
		this.hasCollided = hasCollided;
	}

	public String getName() {
		return "AABBBox";
	}

	public String toString() {
		return "AABBBoxComponent{" +
				", uncollideColour=" + uncollideColour +
				", collideColour=" + collideColour +
				'}';
	}

	public AABBBox getBox() {
		return box;
	}
}