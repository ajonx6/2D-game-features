package com.curaxu.game.audio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoundGroup implements AudioData {	
	public static final Random RANDOM = new Random();
	
	private List<String> names = new ArrayList<>();
	private List<String> paths = new ArrayList<>();
	private SoundData data;
	
	public SoundGroup(SoundData data) {
		this.data = data;
	}
	
	public SoundGroup addSound(String name, String path) {
		names.add(name);
		paths.add(path);
		return this;
	}
	
	public SoundData getData() {
		int index = RANDOM.nextInt(paths.size());
		// addSound(index)
		return new SoundData(names.get(index), paths.get(index), data.getType(), data.getNumLoops(), data.doesOverwrite());
	}
}