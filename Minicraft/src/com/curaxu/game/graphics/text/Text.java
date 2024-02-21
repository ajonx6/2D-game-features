package com.curaxu.game.graphics.text;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;

public class Text {
	private CustomFont font;
	private String text;
	private double x, y;
	private boolean screenPos;

	public Text(CustomFont font, String text, double x, double y, boolean screenPos) {
		this.font = font;
		this.text = text.toUpperCase();
		this.x = x;
		this.y = y;
		this.screenPos = screenPos;
	}

	public void render(Screen screen) {
		int currx = 0;
		for (int i = 0; i < text.length(); i++) {
			Sprite charSprite = font.getSprite(text.charAt(i));
			if (charSprite == null) currx += 8;
			else {
				Vector position = new Vector(x + currx, y - charSprite.getHeight());
				if (!screenPos) position = position.add(Game.getInstance().getScreen().getOffset());
				screen.render(Screen.UI_LAYER, position, charSprite);
				currx += charSprite.getWidth() + 1;
			}
		}
	}
}