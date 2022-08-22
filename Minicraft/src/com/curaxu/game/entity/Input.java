package com.curaxu.game.entity;

import com.curaxu.game.KeyInput;
import com.curaxu.game.MouseInput;

public abstract class Input {
    public enum Type {ON_PRESSED, ON_DOWN, ON_UP, ON_WHEEL};

    private Entity entity;
    private int id;
    private boolean key;
    private boolean enabled = true;
    private Type type;

    public Input(Entity entity, int id, boolean key, Type type) {
        this.entity = entity;
        this.id = id;
        this.key = key;
        this.type = type;
    }

    public Input(Entity entity, int id, boolean key, Type type, boolean enabled) {
        this.entity = entity;
        this.id = id;
        this.key = key;
        this.enabled = enabled;
        this.type = type;
    }

    public void bind() {
        if (key) KeyInput.bindInput(id, this);
        else MouseInput.bindInput(id, this);
    }

    public abstract void action();

    public int getId() {
        return id;
    }

    public boolean isKey() {
        return key;
    }

    public Type getType() {
        return type;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Entity getEntity() {
        return entity;
    }
}
