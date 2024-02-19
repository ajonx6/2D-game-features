package com.curaxu.game.events.cutscenes;

import java.util.ArrayList;
import java.util.List;

public class Cutscene {
	private List<CutsceneEvent> events = new ArrayList<>();

	private boolean active = false;
	private int currEvent = 0;

	public void play() {
		active = true;
		events.get(currEvent).start();
	}

	public void tick(double delta) {
		if (!active) return;
		if (events.get(currEvent).tick(delta)) {
			currEvent++;
			if (currEvent >= events.size()) active = false;
			else events.get(currEvent).start();
		}
	}

	public Cutscene addEvent(CutsceneEvent event) {
		events.add(event);
		return this;
	}
}