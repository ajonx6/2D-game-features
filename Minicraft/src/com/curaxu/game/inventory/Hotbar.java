package com.curaxu.game.inventory;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheet;

public class Hotbar {
	public static final Sprite CELL_SPRITE = new Sprite("inventory_cell");
	public static final Sprite SELECTED_CELL_SPRITE = new Sprite("selected_cell");
	public static final int NUM_CELLS = 6;
	public static final int CELL_SIZE = 40;
	public static final int CELL_GAP = 2;

	private Storage inventory;
	private int selected = 0;

	public Hotbar(Storage inventory) {
		this.inventory = inventory;
	}

	public void render(Screen screen) {
		int x = Game.PIXEL_WIDTH / 2 - (CELL_GAP * (NUM_CELLS - 1) / 2) - CELL_SIZE * NUM_CELLS / 2;
		int y = Game.PIXEL_HEIGHT - CELL_SIZE - CELL_GAP;

		for (int i = 0; i < NUM_CELLS; i++) {
			inventory.getCell(i, 0).render(screen, selected == i ? SELECTED_CELL_SPRITE : CELL_SPRITE, new Vector(x, y));
			x += CELL_GAP + CELL_SIZE;
		}
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}
}
