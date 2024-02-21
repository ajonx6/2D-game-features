package com.curaxu.game.entity.components;

import com.curaxu.game.Pair;
import com.curaxu.game.Vector;
import com.curaxu.game.entity.Entity;
import com.curaxu.game.graphics.AbstractSprite;
import com.curaxu.game.graphics.LayeredSprite;
import com.curaxu.game.graphics.Screen;
import com.curaxu.game.graphics.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SpriteListComponent extends Component {
	private HashMap<String, LayeredSprite> sprites = new HashMap<>();
	private LayeredSprite currSprite;

	public SpriteListComponent(Entity entity, String initial, Pair<String, AbstractSprite>... sprites) {
		super(entity);
		for (Pair<String, AbstractSprite> p : sprites) {
			this.sprites.put(p.getKey(), new LayeredSprite(p.getValue()));
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
		screen.render(Screen.ENTITY_LAYER, entity.getScreenPos(), currSprite);
	}

	public void addToSprites(String name, Sprite sprite, int alpha, boolean overlay) {
		for (LayeredSprite layer : sprites.values()) {
			layer.addLayer(name, sprite, alpha, overlay);
		}
	}

	public void removeFromSprites(String name) {
		for (LayeredSprite layer : sprites.values()) {
			layer.removeLayer(name);
		}
	}

	public void setSprite(String name) {
		currSprite.reset();
		currSprite = sprites.get(name);
	}

	public LayeredSprite getSprite(String name) {
		return sprites.get(name);
	}

	public LayeredSprite getCurrentSprite() {
		return currSprite;
	}

	public List<LayeredSprite> getSprites() {
		return new ArrayList<>(sprites.values());
	}

	public String getName() {
		return "SpriteList";
	}
}
