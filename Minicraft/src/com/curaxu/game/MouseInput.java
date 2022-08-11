package com.curaxu.game;

import java.awt.event.*;

public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {
	public static final int NUM_BUTTONS = 4;
	public static final boolean[] buttons = new boolean[NUM_BUTTONS];
	public static final boolean[] lastButtons = new boolean[NUM_BUTTONS];

	public static int mx = 0, my = 0;
	public static int scroll = 0;

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
			lastButtons[i] = buttons[i];
		}
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