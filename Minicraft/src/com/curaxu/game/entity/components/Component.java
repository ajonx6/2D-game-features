package com.curaxu.game.entity.components;

import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.Screen;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    protected Entity entity;
    protected List<String> prerequisites = new ArrayList<>();

    public Component(Entity entity, String... prerequisites) {
        this.entity = entity;
        this.prerequisites.addAll(List.of(prerequisites));
    }

    public Entity getEntity() {
        return entity;
    }

    public abstract void tick(double delta);

    public abstract void render(Screen screen);

    public abstract String getName();

    public List<String> getPrerequisites() {
        return prerequisites;
    }
}