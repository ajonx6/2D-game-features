package com.curaxu.game.entity.components;

import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.LayeredSprite;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.VisualEffect;
import com.curaxu.game.items.Item;

public class HealthComponent extends Component {
	public static final double HEALTH_FLASH_TIME = 0.4;

	private int maxHealth;
	private int currentHealth;
	private SpriteListComponent spriteListComponent;

	private double healthFlashTimer = 0;
	private boolean healthFlashActive = false;
	
	public HealthComponent(Entity entity, int maxHealth) {
		super(entity, "SpriteList");
		this.spriteListComponent = (SpriteListComponent) entity.getComponent("SpriteList");
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
	}

	public boolean damage(int damage) {
		currentHealth -= damage;
		healthFlashTimer = HEALTH_FLASH_TIME;
		healthFlashActive = true;
		spriteListComponent.addToSprites("damage", VisualEffect.DAMAGE, 130, true);
		if (currentHealth <= 0) {
			currentHealth = 0;
			return true;
		}
		return false;
	}

	public void tick(double delta) {
		if (healthFlashActive) {
			healthFlashTimer -= delta;
			if (healthFlashTimer <= 0) {
				healthFlashActive = false;
				spriteListComponent.removeFromSprites("damage");
			}
		}
	}

	public void render(Screen screen) {}

	public String getName() {
		return "Health";
	}
}