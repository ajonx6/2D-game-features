package com.curaxu.game.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
	private String path;
	private Clip clip;
	private boolean playing = false;
	private int numTimesToPlay = 0;
	
	private String tag;

	public Sound(String path) {
		try {
			File soundFile = new File("res/" + path + ".wav");
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			this.clip = (Clip) AudioSystem.getLine(info);
			this.clip.open(ais);
			this.path = path;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public Sound(Sound copy) {
		this(copy.path);
		playing = false;
		numTimesToPlay = 0;
	}
	
	public void play() {
		playing = true;
		clip.setFramePosition(0);
		if (numTimesToPlay != 0) clip.loop(numTimesToPlay);
		clip.start();
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void stopAndRestart() {
		stop();
		clip.setFramePosition(0);
	}
	
	public void restartAndPlay() {
		stopAndRestart();
		clip.start();
	}
	
	public Sound loop(int numPlay) {
		if (numPlay > 0) numPlay--;
		clip.loop(numPlay);
		this.numTimesToPlay = numPlay;
		return this;
	}
	
	public int getLength() {
		return clip.getFrameLength();
	}
	
	public boolean isFinished() {
		return clip.getFramePosition() > 0 && !clip.isRunning();
	}

	public String getPath() {
		return path;
	}

	public Clip getClip() {
		return clip;
	}

	public boolean isPlaying() {
		return playing;
	}

	public int getNumTimesToPlay() {
		return numTimesToPlay;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}