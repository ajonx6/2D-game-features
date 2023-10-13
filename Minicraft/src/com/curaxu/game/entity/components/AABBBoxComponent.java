package com.curaxu.game.entity.components;

import com.curaxu.game.Settings;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.AbstractSprite;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;

public class AABBBoxComponent extends Component {
    private Vector position;
    private int width, height;
    private double offx, offy;
    private int uncollideColour = 0x77ff00ff;
    private int collideColour = 0x7700ffff;

    private boolean colliding = false;

    public AABBBoxComponent(Entity entity, Vector position, int width, int height) {
        super(entity);
        this.position = position == null ? entity.getScreenPos() : position;
        this.width = width;
        this.height = height;
        this.offx = this.offy = 0;
    }

    public AABBBoxComponent(Entity entity, Vector position, SpriteListComponent sprites, int spriteIndex) {
        super(entity);
        this.position = position == null ? entity.getScreenPos() : position;

        AbstractSprite sprite = sprites.getCurrentSprite();
        int up = Integer.MAX_VALUE, down = Integer.MIN_VALUE, left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;
        for (int y = 0; y < sprite.getHeight(); y++) {
            for (int x = 0; x < sprite.getWidth(); x++) {
                int color = sprite.getPixels()[x + y * sprite.getWidth()];
                if (color == 0) continue;
                if (y < up) up = y;
                if (y > down) down = y;
                if (x < left) left = x;
                if (x > right) right = x;
            }
        }

        this.position = this.position.add(new Vector(left, up));
        this.width = right - left + 1;
        this.height = down - up + 1;
        this.offx = left;
        this.offy = up;
    }

    public void tick(double delta) {
        this.position = entity.getScreenPos().add(new Vector(offx, offy));
        colliding = false;
    }

    public void render(Screen screen) {
        if (Settings.debugMode) screen.renderRect(entity.getScreenPos().add(new Vector(offx, offy)), width, height, colliding ? collideColour : uncollideColour);
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

    public double getOffx() {
        return offx;
    }

    public double getOffy() {
        return offy;
    }

    public String getName() {
        return "AABBBox";
    }

    public String toString() {
        return "AABBBoxComponent{" +
                "position=" + position +
                ", width=" + width +
                ", height=" + height +
                ", offx=" + offx +
                ", offy=" + offy +
                ", uncollideColour=" + uncollideColour +
                ", collideColour=" + collideColour +
                ", colliding=" + colliding +
                '}';
    }
}