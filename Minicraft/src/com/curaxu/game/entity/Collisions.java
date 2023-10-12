package com.curaxu.game.entity;

import com.curaxu.game.Vector;
import com.curaxu.game.entity.components.AABBBoxComponent;

public class Collisions {
    public static boolean collisionWithBox(AABBBoxComponent a, AABBBoxComponent b) {
        boolean collision = a.getPosition().getX() < b.getPosition().getX() + b.getWidth()  &&
            a.getPosition().getX() + a.getWidth() > b.getPosition().getX()  &&
            a.getPosition().getY() < b.getPosition().getY() + b.getHeight() &&
            a.getPosition().getY() + a.getHeight() > b.getPosition().getY();
        return collision;
    }

    public static boolean collisionWithPoint(AABBBoxComponent a, Vector pos) {
        return pos.getX() >= a.getPosition().getX() && pos.getY() >= a.getPosition().getY() && pos.getX() < a.getPosition().getX() + a.getWidth() && pos.getY() < a.getPosition().getY() + a.getHeight();
    }
}
