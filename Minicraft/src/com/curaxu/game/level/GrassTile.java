package com.curaxu.game.level;

public class GrassTile extends Tile {
    public GrassTile(int tx, int ty, Level level) {
        super(tx, ty, GRASS_ID, null, level);
    }

    public void setSprite() {
        setSprite(0, 1, 0XFF337A45, 0XFF6DB767, 0xFF2B5636, 0);
    }
}