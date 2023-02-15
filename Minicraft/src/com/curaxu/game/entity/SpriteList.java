package com.curaxu.game.entity;

import com.curaxu.game.Vector;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.Screen;

public class SpriteList extends Component {
    private Sprite[] sprites;
    private int index = 0;

    public SpriteList(Entity entity, Sprite... sprites) {
        super(entity);
        this.sprites = new Sprite[sprites.length];
        System.arraycopy(sprites, 0, this.sprites, 0, sprites.length);
    }

    public void tick(double delta) {
        entity.setCenterWorldPos(entity.getWorldPos().add(new Vector(sprites[index].getWidth(), sprites[index].getHeight()).div(2)));
    }

    public void render(Screen screen) {
        if (index < 0 || index >= sprites.length) index = 0;
        screen.renderSprite(entity.getScreenPos(), sprites[index]);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public String getName() {
        return "SpriteList";
    }
}
