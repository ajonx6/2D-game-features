package com.curaxu.game.entity;

import com.curaxu.game.Settings;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Screen;

public class AABBBox extends Component {
    private Vector position;
    private int width, height;
    private int uncollideColour = 0x77ff00ff;
    private int collideColour = 0x7700ffff;

    private boolean colliding = false;

    public AABBBox(Entity entity, Vector position, int width, int height) {
        super(entity);
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public void tick(double delta) {
        this.position = entity.getWorldPos();
        colliding = false;
    }

    public void render(Screen screen) {
        if (Settings.debugMode) screen.renderRect(entity.getScreenPos(), width, height, colliding ? collideColour : uncollideColour);
    }
    public void collided() {
        colliding = true;
    }

    public Vector getPosition() {
        return position;
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