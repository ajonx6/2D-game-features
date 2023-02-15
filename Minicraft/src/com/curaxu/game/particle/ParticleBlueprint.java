package com.curaxu.game.particle;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;

public class ParticleBlueprint {
    private int minSize = 0, maxSize = 0;
    private double minSpeed = 0, maxSpeed = 0;
    private double minDir= 0, maxDir = 0;
    private double deceleration = 0;

    private int[] colours;
    private Sprite[] sprites;

    private double minLife = 0, maxLife = 0;
    private boolean lifeDeterminesAlpha = false;
    private boolean lifeDeterminesSprite = false;

    public ParticleBlueprint setSize(int size) {
        return setSize(size, size);
    }

    public ParticleBlueprint setSize(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        return this;
    }

    public ParticleBlueprint setSpeed(int speed) {
        return setSpeed(speed, speed);
    }

    public ParticleBlueprint setSpeed(int minSpeed, int maxSpeed) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        return this;
    }

    public ParticleBlueprint setDirection(double dir) {
        return setDirection(dir, dir);
    }

    public ParticleBlueprint setDirection(double minDir, double maxDir) {
        this.minDir = minDir;
        this.maxDir = maxDir;
        return this;
    }

    public ParticleBlueprint setDeceleration(double deceleration) {
        this.deceleration = deceleration;
        return this;
    }

    public ParticleBlueprint setColors(int... colours) {
        this.colours = colours;
        return this;
    }

    public ParticleBlueprint setSprites(Sprite... sprites) {
        this.sprites = sprites;
        return this;
    }

    public ParticleBlueprint setLife(double life) {
        return setLife(life, life);
    }

    public ParticleBlueprint setLife(double minLife, double maxLife) {
        this.minLife = minLife;
        this.maxLife = maxLife;
        return this;
    }

    public ParticleBlueprint lifeDeterminesAlpha() {
        lifeDeterminesAlpha = true;
        return this;
    }

    public ParticleBlueprint lifeDeterminesSprite() {
        lifeDeterminesSprite = true;
        return this;
    }
}
