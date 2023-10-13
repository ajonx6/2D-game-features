package com.curaxu.game.audio;

public class SoundData implements AudioData {
	public static final int PLAY = 0;
	public static final int STOP = 1;
	public static final int STOP_AND_RESTART = 2;
	public static final int RESTART_AND_PLAY = 3;
	
	private String name;
	private String path;
	private int type;
	private int numLoops;
	private boolean overwrite;

	public SoundData(String name, String path, int type, int numLoops, boolean overwrite) {
		this.name = name;
		this.path = path;
		this.type = type;
		this.numLoops = numLoops;
		this.overwrite = overwrite;
	}

	public SoundData(int type, int numLoops, boolean overwrite) {
		this.type = type;
		this.numLoops = numLoops;
		this.overwrite = overwrite;
	}
	
	public SoundData getData() {
		return this;
	}

	public String getName() {
		return name;
	}

	public SoundData setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public SoundData setPath(String path) {
		this.path = path;
		return this;
	}

	public int getType() {
		return type;
	}

	public SoundData setType(int type) {
		this.type = type;
		return this;
	}

	public int getNumLoops() {
		return numLoops;
	}

	public SoundData setNumLoops(int numLoops) {
		this.numLoops = numLoops;
		return this;
	}

	public boolean doesOverwrite() {
		return overwrite;
	}
}