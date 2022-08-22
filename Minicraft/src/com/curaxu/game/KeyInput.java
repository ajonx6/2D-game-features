package com.curaxu.game;

import com.curaxu.game.entity.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyInput extends KeyAdapter {
	public static final int NUM_KEYS = 256;
	public static final boolean[] keys = new boolean[NUM_KEYS];
	public static final boolean[] lastKeys = new boolean[NUM_KEYS];

	public static HashMap<Integer, List<Input>> binds = new HashMap<>();

	public static void bindInput(int key, Input input) {
		if (!binds.containsKey(key)) binds.put(key, new ArrayList<>());
		binds.get(key).add(input);
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public static void tick() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (binds.containsKey(i)) {
				for (Input in : binds.get(i)) {
					if (in.getType().equals(Input.Type.ON_PRESSED) && keys[i]) in.action();
					if (in.getType().equals(Input.Type.ON_DOWN) && keys[i] && !lastKeys[i]) in.action();
					if (in.getType().equals(Input.Type.ON_UP) && !keys[i] && lastKeys[i]) in.action();
				}
			}
		}

		for (int i = 0; i < NUM_KEYS; i++) {
			lastKeys[i] = keys[i];
		}
	}

	public static boolean isDown(int key) {
		return keys[key];
	}

	public static boolean wasPressed(int key) {
		return isDown(key) && !lastKeys[key];
	}

	public static boolean wasReleased(int key) {
		return !isDown(key) && lastKeys[key];
	}
}