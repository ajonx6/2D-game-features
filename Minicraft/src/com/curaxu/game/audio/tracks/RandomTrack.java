package com.curaxu.game.audio.tracks;

import com.curaxu.game.audio.AudioData;
import com.curaxu.game.audio.Sound;
import com.curaxu.game.audio.SoundData;
import com.curaxu.game.audio.SoundManager;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTrack extends Track {
	public static final Random RANDOM = new Random();
	
	private List<Sound> currSounds = new ArrayList<>();
	private double minWait, maxWait;
	private double timeToWait = 0;
	private double time = 0;
	
	public RandomTrack(AudioData data, double minWait, double maxWait) {
		super(data);
		this.minWait = minWait;
		this.maxWait = maxWait;
	}

	public RandomTrack(String name, String path, double minWait, double maxWait) {
		super(new SoundData(name, path, SoundData.PLAY, 1, false));
		this.minWait = minWait;
		this.maxWait = maxWait;
	}
	
	public void generateWaitTime() {
		timeToWait = RANDOM.nextDouble() * (maxWait - minWait) + minWait;
	}

	public void start() {
		time = 0;
		generateWaitTime();
	}

	public void tick(double delta) {
		time += delta;
		if (time > timeToWait) {
			time -= timeToWait;
			generateWaitTime();
			currSounds.add(SoundManager.request(data.getData()));
		}
		
		List<Sound> copy = new ArrayList<>(currSounds);
		for (Sound sound : copy) {
			if (sound.isFinished()) currSounds.remove(sound);
		}
	}

	public void complete() {
		for (Sound sound : currSounds) {
			SoundManager.request(new SoundData(sound.getTag(), data.getData().getPath(), SoundData.STOP, 0, false));
		}
	}
	
	public void cancel() {
		for (Sound sound : currSounds) {
			SoundManager.request(new SoundData(sound.getTag(), data.getData().getPath(), SoundData.STOP, 0, false));
		}
	}
}