package com.curaxu.game.entity.components;

import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.items.Item;

import java.util.ArrayList;
import java.util.List;

public class LootComponent extends Component {
    public List<LootDrop> lootDropList = new ArrayList<>();

    public LootComponent(Entity entity) {
        super(entity);
    }

    public LootComponent addLoot(Item item, int min, int max, float prob) {
        lootDropList.add(new LootDrop(item, min, max, prob));
        return this;
    }

    public void tick(double delta) {}

    public void render(Screen screen) {}

    public String getName() {
        return "Loot";
    }

    private static class LootDrop {
        private Item item;
        private int minAmount;
        private int maxAmount;
        private float probability;

        public LootDrop(Item item, int minAmount, int maxAmount, float probability) {
            this.item = item;
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
            this.probability = probability;
        }
    }
}
