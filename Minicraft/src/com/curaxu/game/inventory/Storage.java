package com.curaxu.game.inventory;

import com.curaxu.game.entity.components.ItemComponent;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.items.Item;

import java.util.Arrays;

public class Storage {
	private int width, height;
	private StorageCell[] cells;

	public Storage(int width, int height) {
		this.width = width;
		this.height = height;
		this.cells = new StorageCell[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				cells[x + y * width] = new StorageCell(x, y);
			}
		}
	}
	
	public int addItemToStorage(Item item, int amt) {
		boolean loop = true;
		while (loop && amt > 0) {
			loop = addItemToStorage(item);
			if (loop) amt--;
		}
		return amt;
	}

	public boolean addItemToStorage(Item item) {
		for (StorageCell cell : cells) {
			if (cell.getItem() == null || (item.equals(cell.getItem()) && cell.getAmt() < cell.getItem().getStackSize())) {
				cell.setItem(item);
				cell.setAmt(cell.getAmt() + 1);
				return true;
			}
		}
		return false;
	}

	public void remove(Item item) {
		for (StorageCell cell : cells) {
			if (item.equals(cell.getItem())) {
				cell.setAmt(cell.getAmt() - 1);
				if (cell.getAmt() == 0) cell.setItem(null);
			}
		}
	}

	public void setCellItem(int x, int y, Item item, int amt) {
		cells[x + y * width].setItemAmount(item, amt);
	}

	public void tick(double delta) {
		for (StorageCell cell : cells) cell.tick(delta);
	}

	public void render(Screen screen) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				cells[x + y * width].render(screen);
			}
		}
	}

	public StorageCell getCell(int x, int y) {
		return cells[x + y * width];
	}

	public int getAmountOf(Item item) {
		return Arrays.stream(cells).filter(c -> item.equals(c.getItem())).mapToInt(StorageCell::getAmt).sum();
	}
}
