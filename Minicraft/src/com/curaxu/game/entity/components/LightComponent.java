package com.curaxu.game.entity.components;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.lighting.Light;

public class LightComponent extends Component {
    public Vector offset;
    public boolean active;
    public Light light;
    
    public LightComponent(Entity entity, Vector offset, Light light, boolean active) {
        super(entity);
        this.offset = offset;
        this.active = active;
        this.light = light;
        this.light.worldPos = entity.worldPos.add(offset);
        this.light.screenPos = entity.screenPos.add(offset);
    }

    public void tick(double delta) {
        light.worldPos = entity.worldPos.add(offset);
        light.tick(delta);
    }

    public void render(Screen screen) {
        light.render(screen);
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return "Light";
    }
}
