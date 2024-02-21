package com.curaxu.game.graphics;

import java.util.Arrays;

public class VisualEffect {
	public static final ScrollableSprite ENCHANTMENT = new ScrollableSprite(new Sprite("vfx/enchanted"), 33, 60);
	public static final AnimatedSprite SPARKLE = new AnimatedSprite(new SpriteSheet("vfx/sparkle", 48, 48)).setTimes(generateSparkleTimes());
	public static final Sprite DAMAGE = new Sprite("vfx/damage");
	public static final AnimatedSprite FIRE = new AnimatedSprite(new SpriteSheet("vfx/fire", 40, 40)).setTimes(generateFireTimes());

	public static double[] generateSparkleTimes() {
		double[] timers = new double[48];
		Arrays.fill(timers, 0.05);
		return timers;
	}

	public static double[] generateFireTimes() {
		double[] timers = new double[4];
		Arrays.fill(timers, 1.0);
		return timers;
	}
}