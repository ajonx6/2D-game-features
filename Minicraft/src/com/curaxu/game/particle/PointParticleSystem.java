package com.curaxu.game.particle;

import com.curaxu.game.Vector;

public class PointParticleSystem extends ParticleSystem{
    public PointParticleSystem(Vector worldPos, double pps, ParticleBlueprint blueprint) {
        super(worldPos, pps, blueprint);
    }

    @Override
    public void tick(double delta) {
        if (time >= particleSpawnTime) {
            time -= particleSpawnTime;

            Particle p = new Particle(worldPos, blueprint.generateSize(),
                    blueprint.getColor(),
                    blueprint.generateSpeed(),
                    blueprint.generateDirection(),
                    blueprint.getDeceleration(),
                    blueprint.generateLife(),
                    blueprint.doesLifeDetermineAlpha());
            particles.add(p);
        } else {
            time += delta;
        }

        particles.removeIf(p -> p.tick(delta));
    }
}
