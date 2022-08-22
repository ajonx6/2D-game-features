package com.curaxu.game.lighting;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;

public class Light {
    private Vector worldPos;
    private Vector screenPos;
    private int radius;
    private int colour;

    public Light(Vector worldPos, int radius, int colour) {
        this.worldPos = worldPos;
        this.screenPos = worldPos;
        this.radius = radius;
        this.colour = colour;
    }

    public void tick() {
        screenPos = worldPos.add(Game.getInstance().getScreen().getOffset());
    }

    public void render(Screen screen) {
        screen.renderLight(screenPos, colour, radius);
    }

    public Vector getPosition() {
        return worldPos;
    }

    public int getRadius() {
        return radius;
    }

    public int getColour() {
        return colour;
    }
}
