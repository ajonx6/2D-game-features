package com.curaxu.game.level;

public class WaterTile extends Tile {
    public WaterTile(int x, int y, Level level) {
        super(x, y, WATER_ID, null, level);
    }

    public void setSprite() {
        setSprite(4, 1, 0XFF3784B5, 0XFF6996B2, 0xFF54788E, 0);
    }
}