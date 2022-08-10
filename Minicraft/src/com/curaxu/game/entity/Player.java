package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.graphics.Screen;

import java.util.List;

public class Player extends  Entity {
    private boolean onland = true;

    public Player() {
        super(Game.PIXEL_WIDTH / 2 - Game.TILE_SIZE / 2, Game.PIXEL_HEIGHT / 2 - Game.TILE_SIZE / 2, "player");
    }

    public void move(double dx, double dy) {
        Move move = (Move) getComponent("CanMove");
        int movespeed = move.getMoveSpeed();

        Game.getInstance().getScreen().xOffset -= dx * movespeed;
        Game.getInstance().getScreen().yOffset -= dy * movespeed;
        worldX += dx * movespeed;
        worldY += dy * movespeed;
    }

    public void tick() {
        super.tick();

        AABBBox a = (AABBBox) getComponent("aabb");
        List<Entity> collisionWithTrees = Game.getInstance().getLevel().boxEntityCollisionAll("tree", a);

        if (collisionWithTrees.size() != 0) a.collided();
        for (Entity e : collisionWithTrees) {
            ((AABBBox) e.getComponent("aabb")).collided();
        }
    }

    public void render(Screen screen) {
        screenX = Game.PIXEL_WIDTH / 2 - Game.TILE_SIZE / 2;
        screenY = Game.PIXEL_HEIGHT / 2 - Game.TILE_SIZE / 2;
        super.render(screen);
    }
}