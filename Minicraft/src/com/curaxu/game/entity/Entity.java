package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.level.Tile;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    protected int worldX, worldY;
    protected int screenX, screenY;
    protected String tag;
    protected Tile standing;
    protected List<Component> components = new ArrayList<>();

    public Entity(int worldX, int worldY, String tag) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenX = worldX;
        this.screenY = worldY;
        this.tag = tag;
    }

    public void tick() {
        screenX = worldX + Game.getInstance().getScreen().xOffset;
        screenY = worldY + Game.getInstance().getScreen().yOffset;
        standing = Game.getInstance().getLevel().getTileAtWorldPos(worldX + Game.TILE_SIZE / 2, worldY + Game.TILE_SIZE / 2);

        for (Component c : components) {
            c.tick();
        }
    }

    public void render(Screen screen) {
        for (Component c : components) {
            c.render(screen);
        }
    }

    public Entity addComponent(Component c) {
        components.add(c);
        return this;
    }

    public Component getComponent(String name) {
        for (Component c : components) {
            if (c.getName().equals(name)) return c;
        }
        // System.err.println("Error finding component " + name + " for " + tag);
        return null;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public String getTag() {
        return tag;
    }

    public Tile getStanding() {
        return standing;
    }
}