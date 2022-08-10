package com.curaxu.game.entity;

public class Collisions {
    public static boolean collisionWithBox(AABBBox a, AABBBox b) {
        boolean collision = a.getX() < b.getX() + b.getWidth()  &&
            a.getX() + a.getWidth() > b.getX()  &&
            a.getY() < b.getY() + b.getHeight() &&
            a.getY() + a.getHeight() > b.getY();
        return collision;
    }

    public boolean collisionWithPoint(AABBBox a, int x, int y) {
        return x >= a.getX() && y >= a.getY() && x < a.getX() + a.getWidth() && y < a.getY() + a.getHeight();
    }
}
