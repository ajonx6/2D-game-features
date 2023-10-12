package com.curaxu.game.entity.components;

import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.items.Item;

public class ItemComponent extends Component{
	private Item item;
	
	public ItemComponent(Entity entity, Item item) {
		super(entity, "SpriteList");
		this.item = item;
	}

	public void tick(double delta) {}
	
	public void render(Screen screen) {
		screen.renderSprite(entity.screenPos, item.getSprite());
	}

	public Item getItem() {
		return item;
	}

	public String getName() {
		return "Item";
	}
}