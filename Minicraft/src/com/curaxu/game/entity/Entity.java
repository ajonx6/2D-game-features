package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.components.Component;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.level.Tile;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    public Vector worldPos;
    public Vector centerWorldPos;
    public Vector screenPos;
    public Vector footPos = new Vector();
    public String tag;
    public Tile standing;
    public List<Component> components = new ArrayList<>();
    public List<Entity> children = new ArrayList<>();
    public Entity parent;

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

    public void addChild(Entity e) {
        children.add(e);
        e.parent = this;
    }
    
    public void tick(double delta) {
        standing = Tile.getTileByID(Game.getInstance().getLevel().getTileIDAtWorldPos(worldPos.add(footPos)));

        for (Component c : components) {
            c.tick(delta);
        }

        if (parent == null) screenPos = worldPos.add(Game.getInstance().getScreen().getOffset());

        for (Entity e : children) {
            e.screenPos = screenPos.add(e.worldPos);
        }
        
        for (Entity e : children) {
            e.tick(delta);
        }
    }
    
    public void render(Screen screen) {
        for (Component c : components) {
            c.render(screen);
        }
        
        for (Entity e : children) {
            e.render(screen);
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

    public Tile getStanding() {
        return standing;
    }

    public void setFootPosition(Vector footPos) {
        this.footPos = footPos;
    }
}