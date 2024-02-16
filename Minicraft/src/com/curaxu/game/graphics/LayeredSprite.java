package com.curaxu.game.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LayeredSprite implements AbstractSprite {
	public static class Layer {
		private AbstractSprite sprite;
		private int alpha;
		private boolean overlay = false;

		public Layer(AbstractSprite sprite, int alpha, boolean overlay) {
			this.sprite = sprite;
			this.alpha = alpha;
			this.overlay = overlay;
		}

		public AbstractSprite getSprite() {
			return sprite;
		}

		public int getAlpha() {
			return alpha;
		}

		public boolean isOverlay() {
			return overlay;
		}
	}

	private HashMap<String, Layer> names = new HashMap<>();
	private List<Layer> layers = new ArrayList<>();

	public LayeredSprite(AbstractSprite sprite) {
		addLayer("main", sprite, 255, false);
	}

	public LayeredSprite addLayer(String name, AbstractSprite sprite, int alpha, boolean overlay) {
		AbstractSprite copy = sprite.copy();
		for (int i = 0; i < copy.getPixels().length; i++) {
			if (copy.getPixels()[i] == 0) continue;
			copy.getPixels()[i] = ((alpha) << 24) | (copy.getPixels()[i] & 0xffffff);
		}
		Layer layer = new Layer(copy, alpha, overlay);
		layers.add(layer);
		names.put(name, layer);

		return this;
	}

	public boolean containsLayer(String name) {
		return names.containsKey(name);
	}

	public void removeLayer(String name) {
		layers.remove(names.get(name));
		names.remove(name);
	}

	public void tick(double delta) {
		for (Layer layer : layers) {
			layer.getSprite().tick(delta);
		}
	}

	public int getWidth() {
		return layers.isEmpty() ? 0 : layers.get(0).sprite.getWidth();
	}

	public int getHeight() {
		return layers.isEmpty() ? 0 : layers.get(0).sprite.getHeight();
	}

	public int[] getPixels() {
		return layers.isEmpty() ? null : layers.get(0).sprite.getPixels();
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void reset() {
		for (Layer layer : layers) {
			layer.sprite.reset();
		}
	}

	public AbstractSprite copy() {
		return null;
	}
}