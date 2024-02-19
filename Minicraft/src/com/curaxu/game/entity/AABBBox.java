package com.curaxu.game.entity;

import com.curaxu.game.Vector;
import com.curaxu.game.graphics.AbstractSprite;

public class AABBBox {
	private Vector worldPos;
	private Vector offset;
	private Vector size;

	public AABBBox(Vector worldPos, Vector offset, Vector size) {
		this(worldPos, offset, size.getX(), size.getY());
	}

	public AABBBox(Vector worldPos, Vector offset, double width, double height) {
		this.worldPos = worldPos;
		this.offset = offset;
		this.size = new Vector(width, height);
	}

	public AABBBox(Vector worldPos, Vector offset, AbstractSprite sprite) {
		this.worldPos = worldPos;
		this.offset = offset;
		this.size = new Vector(sprite.getWidth(), sprite.getHeight());
	}

	public double getWidth() {
		return size.getX();
	}

	public void setWidth(int width) {
		size.setX(width);
	}

	public double getHeight() {
		return size.getY();
	}

	public void setHeight(int height) {
		size.setY(height);
	}

	public Vector getSize() {
		return size;
	}

	public void setSize(AbstractSprite sprite) {
		size = new Vector(sprite.getWidth(), sprite.getHeight());
	}

	public void setWorldPosition(Vector worldPos) {
		this.worldPos = worldPos;
	}

	public Vector getAbsolutePosition() {
		return worldPos.add(offset);
	}

	public Vector getOffset() {
		return offset;
	}

	public Vector getOtherCorner() {
		return getAbsolutePosition().add(getSize());
	}
}