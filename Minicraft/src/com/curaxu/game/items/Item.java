package com.curaxu.game.items;

import com.curaxu.game.Game;
import com.curaxu.game.Pair;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.AABBBoxComponent;
import com.curaxu.game.entity.components.CollisionResolveComponent;
import com.curaxu.game.entity.components.ItemComponent;
import com.curaxu.game.entity.components.SpriteListComponent;
import com.curaxu.game.graphics.AbstractSprite;
import com.curaxu.game.graphics.AnimatedSprite;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheet;

public class Item {
    public static final Item WOOD = new Item("Wood", new Sprite("wood"), 1);
    public static final Item EMPTY_VIAL = new Item("Empty Vial", new Sprite("empty_vial"), 1);
    public static final Item BLOOD_VIAL = new Item("Blood Vial", new Sprite("blood_vial"), 1);
    public static final Item SHINY = new Item("Shiny", new AnimatedSprite(new SpriteSheet("shiny", 32, 32), 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1), 1);

    public static int nextId = 0;
    
    private final int id;
    private final String name;
    private final AbstractSprite sprite;
    private final int stackSize;
    
    public Item(String name, AbstractSprite sprite, int stackSize) {
        this.id = nextId++;
        this.name = name;
        this.sprite = sprite.copy();
        this.stackSize = stackSize;
    }

    public Item(int id, String name, AbstractSprite sprite, int stackSize) {
        this.id = id;
        this.name = name;
        this.sprite = sprite.copy();
        this.stackSize = stackSize;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.getId();
    }
    
    public void tick(double delta) {
        sprite.tick(delta);
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
    
    public static Entity createItemEntity(Item item, int x, int y, Game game) {
        item = item.copy();
        Entity itemEntity = new Entity(x, y, "item-" + item.getName().toLowerCase().replace(" ", "-"));
        itemEntity.addComponent(new SpriteListComponent(itemEntity, "-", new Pair<>("-", item.getSprite())));
        itemEntity.addComponent(new ItemComponent(itemEntity, item));
        itemEntity.addComponent(new AABBBoxComponent(itemEntity, null, ((SpriteListComponent) itemEntity.getComponent("SpriteList")), 0));
        itemEntity.addComponent(new CollisionResolveComponent(itemEntity, "player") {
            public void onCollision() {
                Item item = ((ItemComponent) entity.getComponent("Item")).getItem();
                item.sprite.reset();
                boolean successful = game.inventory.addItemToStorage(item);
                if (successful) game.level.prepareRemove(itemEntity);
            }
        });
        game.level.addEntity(itemEntity);
        return itemEntity;
    }
    
    public Item copy() {
        return new Item(id, name, sprite.copy(), stackSize);
    }
}