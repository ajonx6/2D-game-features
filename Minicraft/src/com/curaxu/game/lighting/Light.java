package com.curaxu.game.lighting;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;

public class Light {
    public Vector worldPos;
    public Vector screenPos;
    public int radius;
    public int colour;
    public boolean fade;
    public int defaultAlpha;
    public double alpha = 1.0;

    public Light(Vector worldPos, int radius, int colour, boolean fade) {
        this.worldPos = worldPos;
        this.screenPos = worldPos;
        this.radius = radius;
        this.colour = colour;
        this.fade = fade;
        this.defaultAlpha = (colour >> 24) & 0xff;
    }
    
    public void tick(double delta) {
        screenPos = worldPos.add(Game.getInstance().getScreen().getOffset());
        // alpha = Game.getInstance().random.nextDouble() * 0.5 + 0.25;
        colour = (int) (alpha * (double) defaultAlpha) << 24 | (colour & 0xffffff);
    }

    public void render(Screen screen) {
        // screen.renderLight(screenPos, colour, radius, fade);
    }
}
