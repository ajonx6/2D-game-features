package com.curaxu.game.audio;

// Stores data for the sound being requested
public class SoundRequest {
	// The name of the sound
	private String name;

	public SoundRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}