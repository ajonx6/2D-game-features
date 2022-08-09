package com.curaxu.game.level;

import com.curaxu.game.graphics.Screen;

public class WaterTile extends Tile {
    public WaterTile(int x, int y, Level level) {
        super(x, y, 2, null, level);
    }

    public void setSprite() {
        setSprite(4, 1, 0XFF3784B5, 0XFF6996B2, 0xFF54788E, 0);
    }

    public void renderBonus(Screen screen) {}
}