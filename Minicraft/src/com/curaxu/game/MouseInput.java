package com.curaxu.game;

import com.curaxu.game.entity.Input;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {
	public static final int NUM_BUTTONS = 4;
	public static final boolean[] buttons = new boolean[NUM_BUTTONS];
	public static final boolean[] lastButtons = new boolean[NUM_BUTTONS];

	public static int mx = 0, my = 0;
	public static int oldScroll = 0;
	public static int scroll = 0;

	public static HashMap<Integer, List<Input>> binds = new HashMap<>();

	public static void bindInput(int button, Input input) {
		if (!binds.containsKey(button)) binds.put(button, new ArrayList<>());
		binds.get(button).add(input);
	}

	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}

	public static void tick() {
		for (int i = 0; i < NUM_BUTTONS; i++) {
			if (binds.containsKey(i)) {
				for (Input in : binds.get(i)) {
					if (in.getType().equals(Input.Type.ON_PRESSED) && buttons[i]) in.action();
					if (in.getType().equals(Input.Type.ON_DOWN) && buttons[i] && !lastButtons[i]) in.action();
					if (in.getType().equals(Input.Type.ON_UP) && !buttons[i] && lastButtons[i]) in.action();
					if (in.getType().equals(Input.Type.ON_WHEEL) && oldScroll != scroll) in.action();
				}
			}
		}

		for (int i = 0; i < NUM_BUTTONS; i++) {
			lastButtons[i] = buttons[i];
		}

		oldScroll = scroll;
	}

	public static boolean isDown(int button) {
		return buttons[button];
	}

	public static boolean wasPressed(int button) {
		return isDown(button) && !lastButtons[button];
	}

	public static boolean wasReleased(int button) {
		return !isDown(button) && lastButtons[button];
	}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}
}