package com.curaxu.game.entity;

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

    public void tick() {}

    public void render(Screen screen) {
        screen.renderSprite(entity.screenX, entity.screenY, sprites[index]);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return "SpriteList";
    }
}
