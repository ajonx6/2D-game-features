package com.curaxu.game.particle;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;

public class Particle {
    private Vector worldPos;
    private Vector screenPos;
    private double size;
    private int colour;
    private double speed;
    private Vector direction;
    private double deceleration;
    private double life;
    private boolean lifeDeterminesAlpha;

    private double alive = 0;

    public Particle(Vector worldPos, double size, int colour, double speed, double direction, double deceleration, double life, boolean lifeDeterminesAlpha) {
        this.worldPos = new Vector(worldPos);
        this.screenPos = new Vector(worldPos);
        this.size = size;
        this.colour = colour;
        this.speed = speed;
        this.direction = new Vector(Math.sin(Math.toRadians(direction)), -Math.cos(Math.toRadians(direction)));
        this.deceleration = deceleration;
        this.life = life;
        this.lifeDeterminesAlpha = lifeDeterminesAlpha;
    }

    public boolean tick(double delta) {
        worldPos = worldPos.add(direction.mul(speed).mul(delta));
        screenPos = worldPos.add(Game.getInstance().getScreen().getOffset());
        speed -= deceleration * delta;
        if (speed < 0) speed = 0;
        alive += delta;
        if (lifeDeterminesAlpha) {
            double percentage = alive / life;
            int alpha = (int) (255.0 * (1.0 - percentage));
            colour = (alpha << 24) | (colour & 0xffffff);
        }
        return alive >= life;
    }

    public void render(Screen screen) {
        screen.renderRect(screenPos.sub(size / 2.0), (int) size, (int) size, colour);
    }

    public Vector getWorldPos() {
        return worldPos;
    }

    public Vector getScreenPos() {
        return screenPos;
    }
}
