package com.curaxu.game.gfx.sprites;

public class WalkingSpriteSheet2 extends SpriteSheet2 {
	public Sprite2 UP_WALK1, UP_WALK2, UP_STILL;
	public Sprite2 DOWN_WALK1, DOWN_WALK2, DOWN_STILL;
	public Sprite2 LEFT_WALK1, LEFT_WALK2, LEFT_STILL;
	public Sprite2 RIGHT_WALK1, RIGHT_WALK2, RIGHT_STILL;

	public WalkingSpriteSheet2(String path, int size) {
		super(path, size);
		initSprites();
	}

	public WalkingSpriteSheet2(String path, int width, int height) {
		super(path, width, height);
		initSprites();
	}

	public void initSprites() {
		UP_WALK1 = getSprite(0, 3);
		UP_STILL = getSprite(1, 3);
		UP_WALK2 = getSprite(2, 3);

		DOWN_WALK1 = getSprite(0, 0);
		DOWN_STILL = getSprite(1, 0);
		DOWN_WALK2 = getSprite(2, 0);

		LEFT_WALK1 = getSprite(0, 1);
		LEFT_STILL = getSprite(1, 1);
		LEFT_WALK2 = getSprite(2, 1);

		RIGHT_WALK1 = getSprite(0, 2);
		RIGHT_STILL = getSprite(1, 2);
		RIGHT_WALK2 = getSprite(2, 2);
	}
}