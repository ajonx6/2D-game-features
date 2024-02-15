package com.curaxu.game;

public class Vector {
	private double x = 0, y = 0;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Vector v) {
		if (v != null) {
			this.x = v.x;
			this.y = v.y;
		}
	}

	public Vector() {}

	public double length() {
		return Math.sqrt(lengthSquared());
	}

	public double lengthSquared() {
		return x * x + y * y;
	}

	public double distanceTo(Vector v) {
		double dx = v.x - x;
		double dy = v.y - y;
		return new Vector(dx, dy).length();
	}

	public Vector normalize() {
		double length = length();
		if (length == 0) return new Vector();
		return this.div(length);
	}

	public double dot(Vector v) {
		return x * v.x + y * v.y;
	}

	public Vector abs() {
		return new Vector(Math.abs(x), Math.abs(y));
	}

	public Vector add(Vector v) {
		return new Vector(x + v.getX(), y + v.getY());
	}

	public Vector add(double r) {
		return new Vector(x + r, y + r);
	}

	public Vector add(double a, double b) {
		return new Vector(x + a, y + b);
	}

	public Vector sub(Vector v) {
		return new Vector(x - v.getX(), y - v.getY());
	}

	public Vector sub(double r) {
		return new Vector(x - r, y - r);
	}

	public Vector sub(double a, double b) {
		return new Vector(x - a, y - b);
	}

	public Vector mul(Vector v) {
		return new Vector(x * v.getX(), y * v.getY());
	}

	public Vector mul(double r) {
		return new Vector(x * r, y * r);
	}

	public Vector div(Vector v) {
		return new Vector(x / v.getX(), y / v.getY());
	}

	public Vector div(double r) {
		return new Vector(x / r, y / r);
	}

	public boolean equals(Vector v) {
		return x == v.x && y == v.y;
	}

	public boolean isZero() {
		return x == 0 && y == 0;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}