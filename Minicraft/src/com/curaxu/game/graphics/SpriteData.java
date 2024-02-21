package com.curaxu.game.graphics;

import com.curaxu.game.Vector;

public class SpriteData {
	private AbstractSprite sprite;
	private AbstractSprite spriteToCover;
	private Vector position;

	public SpriteData(AbstractSprite sprite, Vector position) {
		this.sprite = sprite;
		this.position = position;
	}

	public SpriteData(AbstractSprite sprite, AbstractSprite spriteToCover, Vector position) {
		this.sprite = sprite;
		this.spriteToCover = spriteToCover;
		this.position = position;
	}

	public AbstractSprite getSprite() {
		return sprite;
	}

	public AbstractSprite getSpriteToCover() {
		return spriteToCover;
	}

	public Vector getPosition() {
		return position;
	}
}