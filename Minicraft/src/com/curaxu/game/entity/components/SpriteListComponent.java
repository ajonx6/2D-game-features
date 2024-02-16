package com.curaxu.game.entity.components;

import com.curaxu.game.Pair;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.AbstractSprite;
import com.curaxu.game.graphics.Screen;

import java.util.ArrayList;
import java.util.HashMap;

public class SpriteListComponent extends Component {
    private HashMap<String, AbstractSprite> sprites = new HashMap<>();
    private AbstractSprite currSprite;

    public SpriteListComponent(Entity entity, String initial, Pair<String, AbstractSprite>... sprites) {
        super(entity);
        for (Pair<String, AbstractSprite> p : sprites) {
            this.sprites.put(p.getKey(), p.getValue());
        }
        currSprite = this.sprites.get(initial);
        entity.setFootPosition(new Vector(currSprite.getWidth() / 2, currSprite.getHeight() - 2));
    }

    public void tick(double delta) {
        entity.setCenterWorldPos(entity.getWorldPos().add(new Vector(currSprite.getWidth(), currSprite.getHeight()).div(2)));
        currSprite.tick(delta);
        entity.setFootPosition(new Vector(currSprite.getWidth() / 2, currSprite.getHeight() - 2));
    }
    
    public void render(Screen screen) {
        screen.renderSprite(entity.getScreenPos(), currSprite);
    }

    public void setSprite(String name) {
        currSprite.reset();
        currSprite = sprites.get(name);
    }

    public AbstractSprite getCurrentSprite() {
        return currSprite;
    }

    public String getName() {
        return "SpriteList";
    }
}
