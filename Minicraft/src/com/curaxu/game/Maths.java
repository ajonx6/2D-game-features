package com.curaxu.game;

public class Maths {
	// Interpolates between start and end
	public static double linearInterpolate(double start, double end, double ratio) {
		return clamp(start + (end - start) * ratio, start, end);
	}

	// Clamps value v between the 2 bounds
	public static double clamp(double value, double bound1, double bound2) {
		double bottom = Math.min(bound1, bound2);
		double top = Math.max(bound1, bound2);
		return Math.max(bottom, Math.min(value, top));
	}

	public static int modulus(int a, int b) {
		return ((a % b) + b) % b;
	}

	public static double easeInOutQuad(double x) {
		return x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2;
	}

	public static double easeOutBounce(double x) {
		double n1 = 7.5625;
		double d1 = 2.75;

		if (x < 1.0 / d1) {
			return n1 * x * x;
		} else if (x < 2.0 / d1) {
			return n1 * (x -= 1.5 / d1) * x + 0.75;
		} else if (x < 2.5 / d1) {
			return n1 * (x -= 2.25 / d1) * x + 0.9375;
		} else {
			return n1 * (x -= 2.625 / d1) * x + 0.984375;
		}
	}

	public static double easeOutElastic(double x) {
		double c4 = 2.0 * Math.PI / 3.0;
		return x == 0.0 ? 0.0 : x == 1.0 ? 1.0 : Math.pow(2.0, -10.0 * x) * Math.sin((x * 10.0 - 0.75) * c4) + 1.0;
	}
}