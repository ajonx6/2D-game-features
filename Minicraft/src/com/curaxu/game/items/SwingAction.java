package com.curaxu.game.items;

import com.curaxu.game.Generator;
import com.curaxu.game.MouseInput;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.HealthComponent;
import com.curaxu.game.entity.components.MoveComponent;
import com.curaxu.game.entity.components.SpriteListComponent;
import com.curaxu.game.graphics.LayeredSprite;
import com.curaxu.game.graphics.VisualEffect;
import com.curaxu.game.level.Level;

import java.util.List;

public class SwingAction implements ItemAction {
	public static final double BACKWARDS = 15.0;

	public double range;
	public double angleInDeg;
	public double angleInDot;
	public int damage;

	public SwingAction(double range, double angleInDeg, int damage) {
		this.range = range;
		this.angleInDeg = angleInDeg;
		this.angleInDot = Math.cos(Math.toRadians(angleInDeg));
		this.damage = damage;
	}

	public void onLeftClick(Entity user, Level level) {
		Vector userToMouse = MouseInput.getMouseWorldPos().sub(user.getCenterWorldPos());
		Vector focalPoint = user.getCenterWorldPos().sub(userToMouse.normalize().mul(BACKWARDS));

		for (Entity e : level.getEntities("sheep")) {
			Vector focalToEntity = e.getWorldPos().sub(focalPoint);
			if (focalToEntity.length() <= range + BACKWARDS && userToMouse.normalize().dot(focalToEntity.normalize()) >= angleInDot) {
				boolean killed = ((HealthComponent) e.getComponent("Health")).damage(1);
				level.addEntity(Generator.generateBloodSplatter(e.getCenterWorldPos().getX(), e.getCenterWorldPos().getY()));
				if (killed) level.prepareRemove(e);

				// SpriteListComponent spriteListComponent = (SpriteListComponent) e.getComponent("SpriteList");
				// List<LayeredSprite> sprites = spriteListComponent.getSprites();
				// for (LayeredSprite s : sprites) {
				// 	s.addLayer("Damage", VisualEffect.DAMAGE, 128, true);
				// }
			}
		}
	}

	public void onRightClick(Entity user, Level level) {}
}