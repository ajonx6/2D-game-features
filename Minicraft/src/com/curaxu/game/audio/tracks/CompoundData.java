package com.curaxu.game.audio.tracks;

import java.util.ArrayList;
import java.util.List;

public class CompoundData  {
	private List<Track> trackData = new ArrayList<>();
	private boolean shouldStop = false;
	
	public CompoundData(Track... tracks) {
		trackData.addAll(List.of(tracks));
	}
	
	public void start() {
		for (Track track : trackData) {
			track.start();
		}
	}

	public void tick(double delta) {
		for (Track track : trackData) {
			track.tick(delta);
		}
	}

	public void complete() {
		System.out.println("Compound data complete");
		for (Track track : trackData) {
			track.complete();
		}
		shouldStop = true;
	}

	public void cancel() {
		System.out.println("Compound data cancel");
		for (Track track : trackData) {
			track.cancel();
		}
		shouldStop = true;
	}

	public boolean shouldStop() {
		return shouldStop;
	}
}