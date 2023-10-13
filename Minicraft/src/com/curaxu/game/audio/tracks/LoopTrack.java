package com.curaxu.game.audio.tracks;

import com.curaxu.game.audio.SoundData;
import com.curaxu.game.audio.SoundManager;

import javax.sound.sampled.Clip;

public class LoopTrack extends Track {
	public LoopTrack(String name, String path) {
		super(new SoundData(name, path, SoundData.PLAY, Clip.LOOP_CONTINUOUSLY, false));
	}

	public void start() {
		sound = SoundManager.request(data.getData());
	}

	public void tick(double delta) {}

	public void complete() {
		SoundManager.request(new SoundData(data.getData().getName(), data.getData().getPath(), SoundData.STOP, 0, false));
	}
	
	public void cancel() {
		SoundManager.request(new SoundData(data.getData().getName(), data.getData().getPath(), SoundData.STOP, 0, false));
	}
}