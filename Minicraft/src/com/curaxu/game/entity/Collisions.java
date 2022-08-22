package com.curaxu.game.entity;

import com.curaxu.game.Vector;

public class Collisions {
    public static boolean collisionWithBox(AABBBox a, AABBBox b) {
        boolean collision = a.getOffsetPosition().getX() < b.getOffsetPosition().getX() + b.getWidth()  &&
            a.getOffsetPosition().getX() + a.getWidth() > b.getOffsetPosition().getX()  &&
            a.getOffsetPosition().getY() < b.getOffsetPosition().getY() + b.getHeight() &&
            a.getOffsetPosition().getY() + a.getHeight() > b.getOffsetPosition().getY();
        return collision;
    }

    public boolean collisionWithPoint(AABBBox a, Vector pos) {
        return pos.getX() >= a.getPosition().getX() && pos.getY() >= a.getPosition().getY() && pos.getX() < a.getPosition().getX() + a.getWidth() && pos.getY() < a.getPosition().getY() + a.getHeight();
    }
}
