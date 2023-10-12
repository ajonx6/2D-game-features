package com.curaxu.game.entity.components;

import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.entity.components.Component;
import com.curaxu.game.graphics.AbstractSprite;
import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.SpriteSheet;

public class SpriteListComponent extends Component {
    private AbstractSprite[] sprites;
    private int index = 0;

    public SpriteListComponent(Entity entity, AbstractSprite... sprites) {
        super(entity);
        this.sprites = new AbstractSprite[sprites.length];
        System.arraycopy(sprites, 0, this.sprites, 0, sprites.length);
    }

    public SpriteListComponent(Entity entity, SpriteSheet sheet) {
        super(entity);
        this.sprites = new AbstractSprite[sheet.getSprites().length];
        System.arraycopy(sheet.getSprites(), 0, this.sprites, 0, sprites.length);
    }

    public void tick(double delta) {
        entity.setCenterWorldPos(entity.getWorldPos().add(new Vector(sprites[index].getWidth(), sprites[index].getHeight()).div(2)));
        sprites[index].tick(delta);
    }
    
    public void render(Screen screen) {
        if (index < 0 || index >= sprites.length) index = 0;
        screen.renderSprite(entity.getScreenPos(), sprites[index]);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public AbstractSprite[] getSprites() {
        return sprites;
    }

    public String getName() {
        return "SpriteList";
    }
}
