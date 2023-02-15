package com.curaxu.game.particle;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem {
    private Vector worldPos;
    private double particleSpawnTime;
    private double time;
    private Particle t;
    private List<Particle> particles = new ArrayList<>();

    public ParticleSystem(Vector worldPos, double pps, Particle template) {
        this.worldPos = worldPos;
        this.particleSpawnTime = 1.0 / pps;
        this.t = template;
    }

    public void tick(double delta) {
        if (time >= particleSpawnTime) {
            time -= particleSpawnTime;
            Vector direction = new Vector(Game.getInstance().getRandom().nextDouble() * 2.0 - 1.0, Game.getInstance().getRandom().nextDouble() * 2.0 - 1.0);
            direction = direction.normalize();
            particles.add(new Particle(worldPos, t.getSize(), t.getColour(), t.getSpeed(), direction, t.getDeceleration(), t.getLife(), t.doesLifeDeterminesAlpha()));
        } else {
            time += delta;
        }

        particles.removeIf(p -> p.tick(delta));
    }

    public void render(Screen screen) {
        for (Particle p : particles) {
            p.render(screen);
        }
    }
}