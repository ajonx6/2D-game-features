package com.curaxu.game.gfx.sprites;

public class Animation2 {
	public Sprite2 sprite;
	public Sprite2[] frames;
	public double frameLength;

	public Animation2(double frameLength, Sprite2... sprites) {
		this.sprite = sprites[0];
		this.frames = sprites;
		this.frameLength = frameLength;
	}

	public Sprite2 getSprite(double time) {
		int frameNumber = (int) (time / frameLength);
		frameNumber = frameNumber % ((frames.length * 2) - 2);
		if (frameNumber >= frames.length) frameNumber = frames.length - 2 - (frameNumber - frames.length);
		return frames[frameNumber];
	}
}