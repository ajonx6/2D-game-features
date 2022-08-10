package com.curaxu.game.entity;

import com.curaxu.game.graphics.Screen;

public abstract class Component {
    protected Entity entity;

    public Component(Entity entity, String... prerequisites) {
        this.entity = entity;
        for (String s : prerequisites) {
            if (entity.getComponent(s) == null) {
                System.err.println("Error: " + getName() + " for " + entity.tag + " needs component " + s);
                System.exit(0);
            }
        }
    }

    public Entity getEntity() {
        return entity;
    }

    public abstract void tick();

    public abstract void render(Screen screen);

    public abstract String getName();
}
