package com.curaxu.game.util;

public class Vector {
	public double x, y;

	public Vector() {
		this(0, 0);
	}

	public Vector(Vector r) {
		this(r.x, r.y);
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(double rx, double ry) {
		x += rx;
		y += ry;
	}

	public Vector copy() {
		return new Vector(x, y);
	}

	public int intX() {
		return (int) x;
	}

	public int intY() {
		return (int) y;
	}
}