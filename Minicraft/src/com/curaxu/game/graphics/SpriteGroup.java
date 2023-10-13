package com.curaxu.game.graphics;

import java.util.ArrayList;
import java.util.List;

public class SpriteGroup {
	public List<AbstractSprite> group = new ArrayList<>();

	public SpriteGroup addSprite(AbstractSprite s) {
		group.add(s);
		return this;
	}
	
	public AbstractSprite getSprite(int i) {
		return group.get(i);
	}
}