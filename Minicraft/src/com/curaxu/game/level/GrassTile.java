package com.curaxu.game.level;

import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheets;

public class GrassTile extends Tile {
    public boolean hasTree = false;

    public GrassTile(int x, int y, Level level) {
        super(x, y, 1, null, level);
    }

    public void setSprite() {
        setSprite(0, 1, 0XFF337A45, 0XFF6DB767, 0xFF2B5636, 0);
    }

    public void renderBonus(Screen screen) {
        if (hasTree) screen.renderSprite(x, y, new Sprite(SpriteSheets.tile_sheet.getSprite(3, 0), 0xFF704629, 0xFF438759, 0xFF54A86E, 0));
    }
}