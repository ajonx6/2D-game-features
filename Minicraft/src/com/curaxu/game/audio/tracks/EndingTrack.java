package com.curaxu.game.audio.tracks;

import com.curaxu.game.audio.AudioData;
import com.curaxu.game.audio.Sound;
import com.curaxu.game.audio.SoundData;
import com.curaxu.game.audio.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EndingTrack extends Track {
	private boolean playOnCancel;
	
	public EndingTrack(AudioData data, boolean playOnCancel) {
		super(data);
		this.playOnCancel = playOnCancel;
	}

	public EndingTrack(String name, String path, boolean playOnCancel) {
		super(new SoundData(name, path, SoundData.PLAY, 1, false));
		this.playOnCancel = playOnCancel;
	}

	public void start() {}

	public void tick(double delta) {}

	public void complete() {
		SoundManager.request(data.getData());
	}
	
	public void cancel() {
		if (playOnCancel) SoundManager.request(data.getData());
	}
}