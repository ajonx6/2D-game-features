package com.curaxu.game.items;

import com.curaxu.game.Game;
import com.curaxu.game.Pair;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.Collisions;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.AABBBoxComponent;
import com.curaxu.game.entity.components.CollisionResolveComponent;
import com.curaxu.game.entity.components.ItemComponent;
import com.curaxu.game.entity.components.SpriteListComponent;
import com.curaxu.game.graphics.*;

import java.util.HashMap;

public class Item {
	public static final Item WOOD = new Item("Wood", new Sprite("items/wood"), 1);
	public static final Item EMPTY_VIAL = new Item("Empty Vial", new Sprite("items/empty_vial"), 1);
	public static final Item BLOOD_VIAL = new Item("Blood Vial", new Sprite("items/blood_vial"), 1);
	public static final Item SHINY = new Item("Shiny", new AnimatedSprite(new SpriteSheet("items/shiny", 32, 32)).setTimes(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1), 1);
	public static final Item SHEEP_EYE = new Item("Sheep Eye", new Sprite("items/sheep_eye"), 1);
	public static final Item KUNAI = new Item("Kunai", new Sprite("vfx/test2"), 1);

	public static int nextId = 0;

	private final int id;
	private final String name;
	private final AbstractSprite sprite;
	private final LayeredSprite currentSprite;
	private final int stackSize;

	private boolean isEnchanted = false;
	private boolean isSparkle = false;

	public Item(String name, AbstractSprite sprite, int stackSize) {
		this.id = nextId++;
		this.name = name;
		this.sprite = sprite.copy();
		this.currentSprite = new LayeredSprite();
		this.currentSprite.addLayer("main", sprite, 255, false);
		this.stackSize = stackSize;
	}

	public Item(int id, String name, AbstractSprite sprite, int stackSize) {
		this.id = id;
		this.name = name;
		this.sprite = sprite.copy();
		this.currentSprite = new LayeredSprite();
		this.currentSprite.addLayer("main", sprite, 255, false);
		this.stackSize = stackSize;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Item item = (Item) o;
		return id == item.getId();
	}

	public void tick(double delta) {
		currentSprite.tick(delta);
	}

	public void render(Screen screen, Vector pos) {
		screen.renderSprite(pos, currentSprite);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public AbstractSprite getSprite() {
		return sprite;
	}

	public int getStackSize() {
		return stackSize;
	}

	public boolean isEnchanted() {
		return isEnchanted;
	}

	public void isEnchanted(boolean enchanted) {
		isEnchanted = enchanted;
		if (enchanted && !currentSprite.containsLayer("enchanted")) {
			currentSprite.addLayer("enchanted", VisualEffect.ENCHANTMENT, 100, true);
		} else if (!enchanted && currentSprite.containsLayer("enchanted")) {
			currentSprite.removeLayer("enchanted");
		}
	}

	public boolean isSparkle() {
		return isSparkle;
	}

	public void isSparkle(boolean sparkle) {
		isSparkle = sparkle;
		if (sparkle && !currentSprite.containsLayer("sparkle")) {
			currentSprite.addLayer("sparkle", VisualEffect.SPARKLE, 255, true);
		} else if (!sparkle && currentSprite.containsLayer("sparkle")) {
			currentSprite.removeLayer("sparkle");
		}
	}

	public static Entity createItemEntity(Item item, int x, int y, Game game) {
		item = item.copy();
		Entity itemEntity = new Entity(x, y, item.getName(), "item");
		//-" + item.getName().toLowerCase().replace(" ", "-")
		itemEntity.addComponent(new SpriteListComponent(itemEntity, "-", new Pair<>("-", item.getSprite())));
		itemEntity.addComponent(new ItemComponent(itemEntity, item));
		itemEntity.addComponent(new AABBBoxComponent(itemEntity, new Vector(), ((SpriteListComponent) itemEntity.getComponent("SpriteList")).getCurrentSprite()));
		itemEntity.addComponent(new CollisionResolveComponent(itemEntity, "player", false) {
			public void onCollision(HashMap<Entity, Collisions.CollisionData> data, double delta) {
				ItemComponent comp = (ItemComponent) entity.getComponent("Item");
				if (comp.isPickedUp()) return;
				Item item = comp.getItem();
				boolean successful = game.inventory.addItemToStorage(item);
				if (successful) {
					comp.isPickedUp(true);
					item.sprite.reset();
					game.level.prepareRemove(itemEntity);
				}
			}

			public void noCollisions(double delta) {
			}
		});
		game.level.addEntity(itemEntity);
		return itemEntity;
	}

	public Item copy() {
		return new Item(id, name, sprite.copy(), stackSize);
	}
}