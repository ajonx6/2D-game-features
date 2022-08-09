package com.curaxu.game.level;

import com.curaxu.game.Game;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.SpriteSheet;
import com.curaxu.game.graphics.SpriteSheets;

public abstract class Tile {
    protected int x, y;
    protected int tx, ty;
    protected int id;
    protected Sprite sprite;
    protected Level level;

    public Tile(int tx, int ty, int id, Sprite sprite, Level level) {
        this.x = tx * Game.TILE_SIZE;
        this.y = ty * Game.TILE_SIZE;
        this.tx = tx;
        this.ty = ty;
        this.id = id;
        this.sprite = sprite;
        this.level = level;
    }

    public abstract void setSprite();

    protected void setSprite(int sheetx, int sheety, int c1, int c2, int c3, int c4) {
        int upID = ty == 0 ? 0 : level.getTile(tx, ty - 1).id;
        int downID = ty == level.getHeight() - 1 ? 0 : level.getTile(tx, ty + 1).id;
        int leftID = tx == 0 ? 0 : level.getTile(tx - 1, ty).id;
        int rightID = tx == level.getWidth() - 1 ? 0 : level.getTile(tx + 1, ty).id;

        boolean u = upID == id;
        boolean d = downID == id;
        boolean l = leftID == id;
        boolean r = rightID == id;

        // sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 1, sheety + 1), c1, c2, c3, c4);

        if (u && d && l && r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 1, sheety + 1), c1, c2, c3, c4); //1 1
        if (u && d && l && !r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 2, sheety + 1), c1, c2, c3, c4); //2 1
        if (u && d && !l && r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx, sheety + 1), c1, c2, c3, c4); //0 1
        if (u && d && !l && !r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 1, sheety + 3), c1, c2, c3, c4); //1 3
        if (u && !d && l && r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 1, sheety + 2), c1, c2, c3, c4); //1 2
        if (u && !d && l && !r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 2, sheety + 2), c1, c2, c3, c4); //2 2
        if (u && !d && !l && r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx, sheety + 2), c1, c2, c3, c4); //0 2
        if (u && !d && !l && !r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 3, sheety), c1, c2, c3, c4); //3 0
        if (!u && d && l && r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 1, sheety), c1, c2, c3, c4); //1 0
        if (!u && d && l && !r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 2, sheety), c1, c2, c3, c4); //2 0
        if (!u && d && !l && r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx, sheety), c1, c2, c3, c4); //0 0
        if (!u && d && !l && !r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 3, sheety + 1), c1, c2, c3, c4); //3 1
        if (!u && !d && l && r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 2, sheety + 3), c1, c2, c3, c4); //2 3
        if (!u && !d && l && !r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 3, sheety + 2), c1, c2, c3, c4); //3 2
        if (!u && !d && !l && r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx + 3, sheety + 3), c1, c2, c3, c4); //3 3
        if (!u && !d && !l && !r) sprite = new Sprite(SpriteSheets.tile_sheet.getSprite(sheetx, sheety + 3), c1, c2, c3, c4); //0 3
    }

    public abstract void renderBonus(Screen screen);

    public void render(Screen screen) {
        screen.renderSprite(x, y, sprite);
        renderBonus(screen);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getID() {
        return id;
    }
}