package com.curaxu.game.graphics;

import com.curaxu.game.Pair;
import com.curaxu.game.Vector;
import com.curaxu.game.graphics.text.CustomFont;
import com.curaxu.game.graphics.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Screen {
	public static final int TILE_LAYER = 0;
	public static final int ENTITY_LAYER = 1;
	public static final int TEXT_LAYER = 2;
	public static final int DEBUG_LAYER = 3;
	public static final int UI_LAYER = 4;

	// public List<Pair<Sprite, Point>> textLayer = new ArrayList<>();
	public List<List<SpriteData>> layers = new ArrayList<>();

	private Vector offset = new Vector();
	private int width, height;
	private int[] pixels;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];

		for (int i = 0; i < 5; i++) {
			layers.add(new ArrayList<>());
		}
	}

	public void tick() {
		for (List<SpriteData> layer : layers) {
			layer.clear();
		}
	}

	public void render(int layer, Vector pos, AbstractSprite sprite) {
		layers.get(layer).add(new SpriteData(sprite, pos));
	}

	public void render(int layer, double x, double y, AbstractSprite sprite) {
		layers.get(layer).add(new SpriteData(sprite, new Vector(x, y)));
	}

	public void renderAll() {
		for (List<SpriteData> layer : layers) {
			for (SpriteData spriteData : layer) {
				if (spriteData.getSpriteToCover() != null) {
					if (spriteData.getSprite() instanceof ScrollableSprite)
						renderSprite(spriteData.getPosition(), (ScrollableSprite) spriteData.getSprite(), spriteData.getSpriteToCover());
					else
						renderSprite(spriteData.getPosition(), spriteData.getSprite(), spriteData.getSpriteToCover());
				} else {
					if (spriteData.getSprite() instanceof ScrollableSprite)
						renderSprite(spriteData.getPosition(), (ScrollableSprite) spriteData.getSprite());
					else if (spriteData.getSprite() instanceof LayeredSprite) {
						renderSprite(spriteData.getPosition(), (LayeredSprite) spriteData.getSprite());
					} else
						renderSprite(spriteData.getPosition(), spriteData.getSprite());
				}
			}
		}
	}

	public void setPixel(int i, int col) {
		double alpha = (double) ((col >> 24) & 0xff) / 255.0;
		if (alpha == 1.0) {
			pixels[i] = col;
		} else if (alpha > 0.0) {
			int oldColour = pixels[i];
			int newRed = ((oldColour >> 16) & 0xff) - (int) ((((oldColour >> 16) & 0xff) - ((col >> 16) & 0xff)) * alpha);
			int newGreen = ((oldColour >> 8) & 0xff) - (int) ((((oldColour >> 8) & 0xff) - ((col >> 8) & 0xff)) * alpha);
			int newBlue = (oldColour & 0xff) - (int) (((oldColour & 0xff) - (col & 0xff)) * alpha);
			pixels[i] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
	}

	private void renderRect(Vector pos, int w, int h, int colour) {
		for (int yy = 0; yy < h; yy++) {
			int yp = (int) (pos.getY() + yy);
			for (int xx = 0; xx < w; xx++) {
				int xp = (int) (pos.getX() + xx);
				if (inBounds(xp, yp)) {
					setPixel(xp + yp * width, colour);
				}
			}
		}
	}

	private void renderSprite(Vector pos, AbstractSprite sprite) {
		int[] pixels = sprite.getPixels();
		for (int yy = 0; yy < sprite.getHeight(); yy++) {
			int yp = (int) (pos.getY() + yy);
			for (int xx = 0; xx < sprite.getWidth(); xx++) {
				int xp = (int) (pos.getX() + xx);
				if (inBounds(xp, yp)) {
					setPixel(xp + yp * width, pixels[xx + yy * sprite.getWidth()]);
				}
			}
		}
	}

	private void renderSprite(Vector pos, AbstractSprite sprite, AbstractSprite toCover) {
		int[] pixels = sprite.getPixels();
		int[] coverPixels = toCover.getPixels();

		int px = sprite.getWidth() / 2 - toCover.getWidth() / 2;
		int py = sprite.getHeight() / 2 - toCover.getHeight() / 2;
		for (int yy = 0; yy < toCover.getHeight(); yy++) {
			int yp = (int) (pos.getY() + yy);
			for (int xx = 0; xx < toCover.getWidth(); xx++) {
				int xp = (int) (pos.getX() + xx);
				if ((coverPixels[xx + yy * toCover.getWidth()] & 0xffffff) == 0) continue;
				if (inBounds(xp, yp)) {
					setPixel(xp + yp * width, pixels[(px + xx) + (py + yy) * sprite.getWidth()]);
				}
			}
		}
	}

	private void renderSprite(Vector pos, ScrollableSprite sprite) {
		int[] pixels = sprite.getPixels();
		for (int yy = 0; yy < sprite.getHeight(); yy++) {
			int yp = (int) (pos.getY() + yy);
			for (int xx = 0; xx < sprite.getWidth(); xx++) {
				int xp = (int) (pos.getX() + xx);
				if (inBounds(xp, yp)) {
					setPixel(xp + yp * width, pixels[((int) (xx + sprite.offsetX) % sprite.getWidth()) + ((int) (yy + sprite.offsetY) % sprite.getHeight()) * sprite.getWidth()]);
				}
			}
		}
	}

	private void renderSprite(Vector pos, ScrollableSprite sprite, AbstractSprite toCover) {
		int[] pixels = sprite.getPixels();
		int[] coverPixels = toCover.getPixels();

		int px = sprite.getWidth() / 2 - toCover.getWidth() / 2;
		int py = sprite.getHeight() / 2 - toCover.getHeight() / 2;
		for (int yy = 0; yy < toCover.getHeight(); yy++) {
			int yp = (int) (pos.getY() + yy);
			for (int xx = 0; xx < toCover.getWidth(); xx++) {
				int xp = (int) (pos.getX() + xx);
				if ((coverPixels[xx + yy * toCover.getWidth()] & 0xffffff) == 0) continue;
				if (inBounds(xp, yp)) {
					setPixel(xp + yp * width, pixels[((int) (px + xx + sprite.offsetX) % sprite.getWidth()) + ((int) (py + yy + sprite.offsetY) % sprite.getHeight()) * sprite.getWidth()]);
				}
			}
		}
	}

	private void renderSprite(Vector pos, LayeredSprite sprite) {
		if (sprite.getLayers().isEmpty()) return;
		LayeredSprite.Layer firstLayer = sprite.getLayers().get(0);
		renderSprite(pos, firstLayer.getSprite());

		for (int l = 1; l < sprite.getLayers().size(); l++) {
			LayeredSprite.Layer thisLayer = sprite.getLayers().get(l);
			Vector drawPos = pos.add(firstLayer.getSprite().getWidth() / 2.0 - thisLayer.getSprite().getWidth() / 2.0, firstLayer.getSprite().getHeight() / 2.0 - thisLayer.getSprite().getHeight() / 2.0);
			if (thisLayer.getSprite() instanceof ScrollableSprite) {
				if (thisLayer.isOverlay())
					renderSprite(pos, (ScrollableSprite) thisLayer.getSprite(), firstLayer.getSprite());
				else renderSprite(drawPos, (ScrollableSprite) thisLayer.getSprite());
			} else {
				if (thisLayer.isOverlay()) renderSprite(pos, thisLayer.getSprite(), firstLayer.getSprite());
				else renderSprite(drawPos, thisLayer.getSprite());
			}
		}
	}

	private void renderLight(Vector pos, int colour, int radius, boolean fade) {
		for (int yy = -radius; yy <= radius; yy++) {
			int yp = (int) (pos.getY() + yy);
			for (int xx = -radius; xx <= radius; xx++) {
				int xp = (int) (pos.getX() + xx);
				double distFromCenter = pos.sub(new Vector(xp, yp)).length();
				if (distFromCenter < radius && inBounds(xp, yp)) {
					double factor = fade ? (1.0 - distFromCenter / radius) : 1.0;
					int oldAlpha = (colour >> 24) & 0xff;
					int newAlpha = (int) (factor * (double) oldAlpha);
					int c = (newAlpha << 24) | (colour & 0xffffff);
					setPixel(xp + yp * width, c);
				}
			}
		}
	}

	public boolean inBounds(int xp, int yp) {
		return xp >= 0 && yp >= 0 && xp < width && yp < height;
	}

	public void clear() {
		clear(0);
	}

	public void clear(int colour) {
		for (int i = 0; i < pixels.length; i++) {
			setPixel(i, colour | 0xff000000);
		}
	}

	public void setOffset(Vector v) {
		offset = new Vector(v);
	}

	public Vector getOffset() {
		return offset;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}
}
