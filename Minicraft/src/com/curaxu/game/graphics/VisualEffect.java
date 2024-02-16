package com.curaxu.game.graphics;

import java.util.Arrays;

public class VisualEffect {
	public static final ScrollableSprite ENCHANTMENT = new ScrollableSprite(new Sprite("vfx/enchanted"), 33, 60);
	public static final AnimatedSprite SPARKLE = new AnimatedSprite(new SpriteSheet("vfx/sparkle", 48, 48)).setTimes(generateSparkleTimes());
	public static final Sprite DAMAGE = new Sprite("vfx/damage");
	
	public static double[] generateSparkleTimes() {
		double[] timers = new double[48];
		Arrays.fill(timers, 0.05);
		return timers;
	}
}