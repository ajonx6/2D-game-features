package com.curaxu.game.particle;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;

import java.util.Random;

public class ParticleBlueprint {
    public static final Random R = new Random();

    private int minSize = 0, maxSize = 0;
    private double minSpeed = 0, maxSpeed = 0;
    private double minDir= 0, maxDir = 0;
    private double deceleration = 0;

    private int[] colours;

    private double minLife = 0, maxLife = 0;
    private boolean lifeDeterminesAlpha = false;
    private boolean lifeDeterminesColorIndex = false;

    public ParticleBlueprint setSize(int size) {
        return setSize(size, size);
    }

    public ParticleBlueprint setSize(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        return this;
    }

    public double generateSize() {
        return minSize + R.nextDouble() * (maxSize - minSize);
    }


    public ParticleBlueprint setSpeed(double speed) {
        return setSpeed(speed, speed);
    }

    public ParticleBlueprint setSpeed(double minSpeed, double maxSpeed) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        return this;
    }

    public double generateSpeed() {
        return minSpeed + R.nextDouble() * (maxSpeed - minSpeed);
    }

    public ParticleBlueprint setDirection(double dir) {
        return setDirection(dir, dir);
    }

    public ParticleBlueprint setDirection(double minDir, double maxDir) {
        this.minDir = minDir;
        this.maxDir = maxDir;
        return this;
    }

    public double generateDirection() {
        return minDir + R.nextDouble() * (maxDir - minDir);
    }

    public ParticleBlueprint setDeceleration(double deceleration) {
        this.deceleration = deceleration;
        return this;
    }

    public double getDeceleration() {
        return deceleration;
    }

    public ParticleBlueprint setColors(int... colours) {
        this.colours = colours;
        return this;
    }

    public int getColor() {
        if (lifeDeterminesColorIndex) {
            return colours[0];
        }
        else {
            return colours[R.nextInt(colours.length)];
        }
    }

    public ParticleBlueprint setLife(double life) {
        return setLife(life, life);
    }

    public ParticleBlueprint setLife(double minLife, double maxLife) {
        this.minLife = minLife;
        this.maxLife = maxLife;
        return this;
    }

    public double generateLife() {
        return minLife + R.nextDouble() * (maxLife - minLife);
    }

    public ParticleBlueprint lifeDeterminesAlpha() {
        lifeDeterminesAlpha = true;
        return this;
    }

    public boolean doesLifeDetermineAlpha() {
        return lifeDeterminesAlpha;
    }

    public ParticleBlueprint lifeDeterminesSpriteColorIndex() {
        lifeDeterminesColorIndex = true;
        return this;
    }

    public boolean doesLifeDetermineColorIndex() {
        return lifeDeterminesColorIndex;
    }
}
