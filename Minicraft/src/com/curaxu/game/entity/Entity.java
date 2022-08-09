package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheets;
import com.curaxu.game.level.Level;
import com.curaxu.game.level.Tile;

public class Entity {
    public int x, y;
    private int movespeed = 2;
    private Tile standing = null;
    private boolean onland = true;
    private Sprite spriteOnLand = new Sprite(SpriteSheets.tile_sheet.getSprite(1), 0xFFEAB2E6, 0XFFBED9F7, 0XFFF44B6A, 0);
    private Sprite spriteSwimming = new Sprite(SpriteSheets.tile_sheet.getSprite(2), 0xFFEAB2E6, 0XFFBED9F7, 0XFFF44B6A, 0XFF93D2F9);

    private Sprite sprite = spriteOnLand;
    private Level level;

    public Entity(Level level) {
        this.x = Game.PIXEL_WIDTH / 2 - Game.TILE_SIZE / 2;
        this.y = Game.PIXEL_HEIGHT / 2 - Game.TILE_SIZE / 2;
        this.level = level;
    }

    public void move(double dx, double dy) {
        if (onland) movespeed = 2;
        else movespeed = 1;

        Screen.xOffset -= dx * movespeed;
        Screen.yOffset -= dy * movespeed;
        x += dx * movespeed;
        y += dy * movespeed;
    }

    public void tick() {
        standing = level.getTileAtWorldPos(x + Game.TILE_SIZE / 2, y + Game.TILE_SIZE / 2);
        if (standing == null) return;
        onland = standing.getID() == 1;
        if (onland) sprite = spriteOnLand;
        else sprite = spriteSwimming;
    }

    public void render(Screen screen) {
        screen.renderSprite(x, y, sprite);
    }
}