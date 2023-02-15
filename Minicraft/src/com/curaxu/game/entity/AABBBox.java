package com.curaxu.game.entity;

import com.curaxu.game.Settings;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.GrayscaleSprite;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;

public class AABBBox extends Component {
    private Vector position;
    private int width, height;
    private double offx, offy;
    private int uncollideColour = 0x77ff00ff;
    private int collideColour = 0x7700ffff;

    private boolean colliding = false;

    public AABBBox(Entity entity, Vector position, int width, int height) {
        super(entity);
        this.position = position;
        this.width = width;
        this.height = height;
        this.offx = this.offy = 0;
    }

    public AABBBox(Entity entity, Vector position, SpriteList sprites, int spriteIndex) {
        super(entity);
        this.position = position;

        Sprite sprite = sprites.getSprites()[spriteIndex];
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

        this.position = position.add(new Vector(left, up));
        this.width = right - left + 1;
        this.height = down - up + 1;
        this.offx = left;
        this.offy = up;
    }

    public void tick(double delta) {
        this.position = entity.getWorldPos().add(new Vector(offx, offy));
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

    public String getName() {
        return "AABBBox";
    }
}