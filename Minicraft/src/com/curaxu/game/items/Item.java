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
import com.curaxu.game.inventory.StorageCell;
import com.curaxu.game.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Item {
	public static final Item WOOD = new Item("Wood", new Sprite("items/wood"), 32);
	public static final Item EMPTY_VIAL = new Item("Empty Vial", new Sprite("items/empty_vial"), 16).addAction(new ItemAction() {
		public void onLeftClick(Entity user, Level level) {
			Game.getInstance().inventory.remove(Game.getInstance().hotbar.getSelected(), 0, 1);
			Game.getInstance().inventory.addItemToStorage(BLOOD_VIAL);
		}

		public void onRightClick(Entity user, Level level) {}
	});
	public static final Item BLOOD_VIAL = new Item("Blood Vial", new Sprite("items/blood_vial"), 16);
	public static final Item SHINY = new Item("Shiny", new AnimatedSprite(new SpriteSheet("items/shiny", 32, 32)).setTimes(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1), 32);
	public static final Item SHEEP_EYE = new Item("Sheep Eye", new Sprite("items/sheep_eye"), 16);
	public static final Item KUNAI = new Item("Kunai", new Sprite("items/kunai"), 1).addAction(new SwingAction(50, 30.0, 5));
	public static final Item VOODOO = new Item("Voodoo", new Sprite("items/voodoo"), 1);

	public static int nextId = 0;

	private final int id;
	private final String name;
	private final LayeredSprite sprite;
	private final int stackSize;
	private List<ItemAction> actions = new ArrayList<>();

	private boolean isEnchanted = false;
	private boolean isSparkle = false;
	private boolean isFire = false;

	public Item(String name, AbstractSprite sprite, int stackSize) {
		this.id = nextId++;
		this.name = name;
		this.sprite = new LayeredSprite(sprite);
		this.stackSize = stackSize;
	}

	public Item(int id, String name, AbstractSprite sprite, int stackSize) {
		this.id = id;
		this.name = name;
		this.sprite = new LayeredSprite(sprite);
		this.stackSize = stackSize;
	}

	public Item addAction(ItemAction action) {
		actions.add(action);
		return this;
	}

	public void leftClick(Entity user, Level level) {
		for (ItemAction action : actions) action.onLeftClick(user, level);
	}

	public void rightClick(Entity user, Level level) {
		for (ItemAction action : actions) action.onRightClick(user, level);
	}

	public static Entity createItemEntity(Item item, double x, double y) {
		item = item.copy();
		Entity itemEntity = new Entity(x, y, item.getName(), "item");
		//-" + item.getName().toLowerCase().replace(" ", "-")
		itemEntity.addComponent(new SpriteListComponent(itemEntity, "-", new Pair<>("-", item.getSprite())));
		itemEntity.addComponent(new ItemComponent(itemEntity, item));
		itemEntity.addComponent(new AABBBoxComponent(itemEntity, new Vector(), ((SpriteListComponent) itemEntity.getComponent("SpriteList")).getCurrentSprite()));
		// itemEntity.addComponent(new CollisionResolveComponent(itemEntity, "player", false) {
		// 	public void onCollision(HashMap<Entity, Collisions.CollisionData> data, double delta) {
		// 		ItemComponent comp = (ItemComponent) entity.getComponent("Item");
		// 		if (comp.isPickedUp()) return;
		// 		Item item = comp.getItem();
		// 		boolean successful = Game.getInstance().inventory.addItemToStorage(item);
		// 		if (successful) {
		// 			comp.isPickedUp(true);
		// 			item.sprite.reset();
		// 			Game.getInstance().level.prepareRemove(itemEntity);
		// 		}
		// 	}
		//
		// 	public void noCollisions(double delta) {
		// 	}
		// });
		Game.getInstance().level.addEntity(itemEntity);
		return itemEntity;
	}

	public void tick(double delta) {
		sprite.tick(delta);
	}

	public void render(Screen screen, Vector pos) {
		screen.render(Screen.UI_LAYER, pos, sprite);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LayeredSprite getSprite() {
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
		if (enchanted && !sprite.containsLayer("enchanted")) {
			sprite.addLayer("enchanted", VisualEffect.ENCHANTMENT, 100, true);
		} else if (!enchanted && sprite.containsLayer("enchanted")) {
			sprite.removeLayer("enchanted");
		}
	}

	public boolean isSparkle() {
		return isSparkle;
	}

	public void isSparkle(boolean sparkle) {
		isSparkle = sparkle;
		if (sparkle && !sprite.containsLayer("sparkle")) {
			sprite.addLayer("sparkle", VisualEffect.SPARKLE, 255, true);
		} else if (!sparkle && sprite.containsLayer("sparkle")) {
			sprite.removeLayer("sparkle");
		}
	}

	public boolean isFire() {
		return isFire;
	}

	public void isFire(boolean fire) {
		isFire = fire;
		if (isFire && !sprite.containsLayer("fire")) {
			sprite.addLayer("fire", VisualEffect.FIRE, 80, true);
		} else if (!isFire && sprite.containsLayer("fire")) {
			sprite.removeLayer("fire");
		}
	}

	public Item copy() {
		return new Item(id, name, sprite.copy(), stackSize);
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Item item = (Item) o;
		return id == item.getId();
	}
}