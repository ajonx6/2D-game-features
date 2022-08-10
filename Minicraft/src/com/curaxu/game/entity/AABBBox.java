package com.curaxu.game.entity;

import com.curaxu.game.graphics.Screen;

public class AABBBox extends Component {
    private int x, y;
    private int width, height;
    private int uncollideColour = 0x77ff00ff;
    private int collideColour = 0x7700ffff;

    private boolean colliding = false;

    public AABBBox(Entity entity, int x, int y, int width, int height) {
        super(entity);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void tick() {
        this.x = entity.worldX;
        this.y = entity.worldY;

        colliding = false;
    }

    public void render(Screen screen) {
        screen.renderRect(entity.screenX, entity.screenY, width, height, colliding ? collideColour : uncollideColour);
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void collided() {
        colliding = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return "aabb";
    }
}