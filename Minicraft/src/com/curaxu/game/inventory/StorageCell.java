package com.curaxu.game.inventory;

import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheet;
import com.curaxu.game.items.Item;

public class StorageCell {
	public static final Sprite cell = new Sprite("inventory_cell");
	public static final SpriteSheet digits = new SpriteSheet("digits", 3, 5);

	public static final int CELL_SIZE = 40;
	public static final int CELL_GAP = 2;
	public static final int DIGIT_COLOR = 0xFFFFFFFF;

	private int x, y;
	private Vector position;
	private Item item;
	private int amt = 0;

	public StorageCell(int x, int y) {
		this.x = x;
		this.y = y;
		initPosition();
	}

	public void tick(double delta) {
		if (item != null) item.tick(delta);
	}

	public void render(Screen screen) {
		render(screen, cell, position);
	}

	public void render(Screen screen, Sprite sprite, Vector position) {
		screen.renderSprite(position, sprite);
		if (item != null) {
			Vector itemPos = position.add(new Vector(CELL_SIZE, CELL_SIZE).div(2)).sub(new Vector(item.getSprite().getWidth(), item.getSprite().getHeight()).div(2));
			item.render(screen, itemPos);

			String strAmt = Integer.toString(amt);
			int offset = (int) ((double) digits.getSpriteWidth() * (strAmt.length() % 2 == 1 ? strAmt.length() / 2 + 0.5 : strAmt.length() / 2));
			int startX = (int) position.getX() + CELL_SIZE / 2 - offset;
			int startY = (int) position.getY() + CELL_SIZE - 3 - digits.getSpriteHeight();
			for (char c : strAmt.toCharArray()) {
				int d = Character.getNumericValue(c);
				Sprite s = digits.getSprite(d).coloured(0, 0, 0, DIGIT_COLOR);
				screen.renderSprite(new Vector(startX, startY), s);
				startX += digits.getSpriteWidth() + 1;
			}
		}
	}

	public void initPosition() {
		this.position = new Vector((1 + x) * CELL_GAP + x * CELL_SIZE, (1 + y) * CELL_GAP + y * CELL_SIZE);
	}

	public void reset() {
		item = null;
		amt = 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		initPosition();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		initPosition();
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmt() {
		return amt;
	}

	public void setAmt(int amt) {
		this.amt = amt;
	}

	public void setItemAmount(Item item, int amt) {
		this.item = item;
		this.amt = amt;
	}
}
