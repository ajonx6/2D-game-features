package com.curaxu.game.entity.components;

import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootComponent extends Component {
	public static final Random RANDOM = new Random();

	private List<LootDrop> lootDropList = new ArrayList<>();

	public LootComponent(Entity entity) {
		super(entity);
	}

	public LootComponent addLoot(Item item, int min, int max, double prob) {
		lootDropList.add(new LootDrop(item, min, max, prob));
		return this;
	}

	public void tick(double delta) {}

	public void render(Screen screen) {}

	public void generateLoot() {
		List<Item> loot = new ArrayList<>();
		for (LootDrop drop : lootDropList) {
			for (int i = 0; i < drop.minAmount; i++) {
				loot.add(drop.item);
			}
			for (int i = drop.minAmount; i < drop.maxAmount; i++) {
				if (RANDOM.nextDouble() < drop.probability) loot.add(drop.item);
			}
		}

		for (Item item : loot) {
			Item.createItemEntity(item, entity.getCenterWorldPos().getX() - 32 + RANDOM.nextInt(64), entity.getCenterWorldPos().getY() - 32 + RANDOM.nextInt(64));
		}
	}

	public String getName() {
		return "Loot";
	}

	private static class LootDrop {
		private Item item;
		private int minAmount;
		private int maxAmount;
		private double probability;

		public LootDrop(Item item, int minAmount, int maxAmount, double probability) {
			this.item = item;
			this.minAmount = minAmount;
			this.maxAmount = maxAmount;
			this.probability = probability;
		}
	}
}
