package com.curaxu.game.graphics.text;

import com.curaxu.game.graphics.Sprite;
import com.curaxu.game.util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CustomFont {
	public static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890%:!?+-=*/,';()`\"$#.\\";

	private static HashMap<String, CustomFont> fonts = new HashMap<>();

	private String fontName;
	private List<String> fontData;
	private Sprite fontSheet;
	private Sprite[] charSprites;

	public CustomFont(String name) {
		fonts.put(name, this);
		this.fontName = name;
		this.fontSheet = new Sprite("ui/" + name);
		this.fontData = Util.load("ui/" + name + ".dat");
		this.charSprites = new Sprite[CHARS.length()];

		int currx = 0;
		for (int l = 0; l < fontData.size(); l++) {
			int[] coords = Arrays.stream(fontData.get(l).split(",")).mapToInt(Integer::parseInt).toArray();
			charSprites[l] = fontSheet.cut(currx, 0, coords[0], coords[1]);
			currx += charSprites[l].getWidth();
		}

		System.out.println("Done");
	}
	
	public static void init() {
		new CustomFont("brown_font");
	}

	public static CustomFont getFont(String name) {
		return fonts.get(name);
	}

	public String getFontName() {
		return fontName;
	}

	public List<String> getFontData() {
		return fontData;
	}

	public Sprite getSprite(char c) {
		if (CHARS.indexOf(c) == -1) return null;
		else return charSprites[CHARS.indexOf(c)];
	}
}