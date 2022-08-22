package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.level.Tile;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    protected Vector worldPos;
    protected Vector centerWorldPos;
    protected Vector screenPos;
    protected String tag;
    protected int tileIDunderneath;
    protected List<Component> components = new ArrayList<>();

    public Entity(int worldX, int worldY, String tag) {
        this.worldPos = new Vector(worldX, worldY);
        this.centerWorldPos = new Vector(worldX, worldY);
        this.screenPos = new Vector(worldX, worldY);
        this.tag = tag;
    }

    public Entity(Vector worldPos, String tag) {
        this.worldPos = new Vector(worldPos);
        this.centerWorldPos = new Vector(worldPos);
        this.screenPos = new Vector(worldPos);
        this.tag = tag;
    }

    public void tick(double delta) {
        tileIDunderneath = Game.getInstance().getLevel().getTileIDAtWorldPos(centerWorldPos);

        for (Component c : components) {
            c.tick(delta);
        }

        screenPos = worldPos.add(Game.getInstance().getScreen().getOffset());
    }

    public void render(Screen screen) {
        for (Component c : components) {
            c.render(screen);
        }
    }

    public void addComponent(Component c) {
        components.add(c);
    }

    public Component getComponent(String name) {
        for (Component c : components) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    public Vector getCenterWorldPos() {
        return centerWorldPos;
    }

    public void setCenterWorldPos(Vector v) {
        centerWorldPos = new Vector(v);
    }

    public Vector getWorldPos() {
        return worldPos;
    }

    public Vector getScreenPos() {
        return screenPos;
    }

    public String getTag() {
        return tag;
    }

    public int getStanding() {
        return tileIDunderneath;
    }
}