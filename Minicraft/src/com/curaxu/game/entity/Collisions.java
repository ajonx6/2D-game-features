package com.curaxu.game.entity;

import com.curaxu.game.Vector;

public class Collisions {
    public static boolean collisionWithBox(AABBBox a, AABBBox b) {
        boolean collision = a.getPosition().getX() < b.getPosition().getX() + b.getWidth()  &&
            a.getPosition().getX() + a.getWidth() > b.getPosition().getX()  &&
            a.getPosition().getY() < b.getPosition().getY() + b.getHeight() &&
            a.getPosition().getY() + a.getHeight() > b.getPosition().getY();
        return collision;
    }

    public boolean collisionWithPoint(AABBBox a, Vector pos) {
        return pos.getX() >= a.getPosition().getX() && pos.getY() >= a.getPosition().getY() && pos.getX() < a.getPosition().getX() + a.getWidth() && pos.getY() < a.getPosition().getY() + a.getHeight();
    }
}
