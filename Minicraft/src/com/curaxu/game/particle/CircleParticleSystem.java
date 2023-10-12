package com.curaxu.game.particle;

import com.curaxu.game.Vector;

public class CircleParticleSystem extends ParticleSystem{
    private double radius;

    public CircleParticleSystem(Vector worldPos, double pps, double radius, ParticleBlueprint blueprint) {
        super(worldPos, pps, blueprint);
        this.radius = radius;
    }

    @Override
    public void tick(double delta) {
        if (time >= particleSpawnTime) {
            time -= particleSpawnTime;

            double a = R.nextDouble() * 2.0 * Math.PI;
            double r = radius * Math.sqrt(R.nextDouble());
            double x = r * Math.cos(a);
            double y = r * Math.sin(a);

            Particle p = new Particle(worldPos.add(new Vector(x, y)), blueprint.generateSize(),
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
