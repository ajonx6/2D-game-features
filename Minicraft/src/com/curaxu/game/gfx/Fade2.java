package com.curaxu.game.gfx;

import com.curaxu.game.Game;
import com.curaxu.game.util.Timer;

public class Fade2 {
	public static final int INACTIVE = 0;
	public static final int OUT = 1;
	public static final int STAY = 2;
	public static final int IN = 3;

	public static int fadeColour;
	public static Timer out, stay, in;
	public static double alpha = 0.0;
	public static int state = INACTIVE, oldState;

	public static void startFade(double o, double d, double e, int colour) {
		out = new Timer(o);
		stay = new Timer(d);
		in = new Timer(e);
		fadeColour = colour;
		state = OUT;
	}

	public static void tick(double delta) {
		if (state == OUT) {
			alpha = out.percent();
			if (out.tick(delta)) {
				oldState = OUT;
				state = STAY;
				alpha = 1.0;
			}
		} else if (state == STAY) {
			if (stay.tick(delta)) {
				state = IN;
				oldState = STAY;
			}
		} else if (state == IN) {
			alpha = 1.0 - in.percent();
			if (in.tick(delta)) {
				state = INACTIVE;
				oldState = IN;
				alpha = 0.0;
			}
		}
	}

	public static void render(Screen2 screen) {
		screen.renderRect(0, 0, Game.WIDTH, Game.HEIGHT, fadeColour | ((int) (alpha * 255.0) << 24));
	}
}