package com.curaxu.game.particle;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class ParticleSystem {
    public static final Random R = new Random();

    protected Vector worldPos;
    protected double particleSpawnTime;
    protected double time;
    protected ParticleBlueprint blueprint;
    protected List<Particle> particles = new ArrayList<>();

    public ParticleSystem(Vector worldPos, double pps, ParticleBlueprint blueprint) {
        this.worldPos = worldPos;
        this.particleSpawnTime = 1.0 / pps;
        this.blueprint = blueprint;
    }

    public abstract void tick(double delta);

    public void render(Screen screen) {
        for (Particle p : particles) {
            p.render(screen);
        }
    }
}