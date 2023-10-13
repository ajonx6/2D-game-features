package com.curaxu.game.audio.tracks;

import com.curaxu.game.audio.AudioData;
import com.curaxu.game.audio.Sound;
import com.curaxu.game.audio.SoundData;

public abstract class Track {
	protected AudioData data;
	protected Sound sound;

	public Track(AudioData data) {
		this.data = data;
	}

	public abstract void start();
	
	public abstract void tick(double delta);
	
	public abstract void complete();
	
	public abstract void cancel();
}