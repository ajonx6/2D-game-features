package com.curaxu.game.entity;

import com.curaxu.game.Game;
import com.curaxu.game.Settings;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;

public class AABBBox extends Component {
    private Vector position;
    private Vector offset;
    private int width, height;
    private int uncollideColour = 0x77ff00ff;
    private int collideColour = 0x7700ffff;

    private boolean colliding = false;

    public AABBBox(Entity entity, Vector position, Vector offset, int width, int height) {
        super(entity);
        this.position = position;
        this.offset = offset;
        this.width = width;
        this.height = height;
    }

    public void tick(double delta) {
        this.position = entity.getWorldPos();
        colliding = false;
    }

    public void render(Screen screen) {
        if (Settings.debugMode) screen.renderRect(position.add(Game.getInstance().getScreen().getOffset()).add(offset), width, height, colliding ? collideColour : uncollideColour);
    }
    public void collided() {
        colliding = true;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getOffsetPosition() {
        return position.add(offset);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return "AABBBox";
    }
}