package com.curaxu.game.entity;

import com.curaxu.game.Vector;
import com.curaxu.game.graphics.AbstractSprite;

public class AABBBox {
	private Vector origPosition;
	// private Vector offsetPosition;
	private Vector size;

	public AABBBox(Vector position, double width, double height) {
		this.origPosition = position;
		// this.offsetPosition = origPosition;
		this.size = new Vector(width, height);
	}

	public AABBBox(Vector position, Vector size) {
		this(position, size.getX(), size.getY());
	}

	public AABBBox(Vector position, AbstractSprite sprite) {
		this.origPosition = position;
		// this.offsetPosition = origPosition;
		// setSize(sprite);
		size = new Vector(sprite.getWidth(), sprite.getHeight());
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

	public void setPosition(Vector origPosition) {
		this.origPosition = origPosition;
	}

	public Vector getPosition() {
		return origPosition;
	}

	public Vector getOtherCorner() {
		return getPosition().add(getSize());
	}
}