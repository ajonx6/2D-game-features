package com.curaxu.game.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// Holds and controls the data of a wav file
public class Sound {
	// Path of the wav file
	public String path;
	// Clip storing all the sound data
	public Clip clip;
	// Shows whether the sound is currently being played or not
	public boolean playing = false;
	// The number of times the sound will repeat
	public int numLoops = 0;

	// Loads in a wav file with the given path
	public Sound(String path) {
		try {
			File soundFile = new File("res/" + path + ".wav");
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	// Makes a new copy of an already existing sound
	public Sound(Sound copy) {
		this.path = copy.path;
		this.clip = copy.clip;
		this.playing = false;
		this.numLoops = copy.numLoops;
	}

	// Resets the sound and starts it
	public void play() {
		playing = true;
		clip.setFramePosition(0);
		if (numLoops != 0) clip.loop(numLoops);
		clip.start();
	}

	// Stops the sound
	public void stop() {
		playing = false;
		clip.stop();
	}

	// Specifies that the sound loops continuously until stopped
	public Sound loop() {
		return loop(Clip.LOOP_CONTINUOUSLY);
	}

	// Loops number of times specified
	public Sound loop(int times) {
		this.numLoops = (times > 0) ? (times - 1) : times;
		return this;
	}

	// Gets the length of the song in number of frames
	public int getLength() {
		return clip.getFrameLength();
	}

	// Returns true if the sound has finished
	public boolean isFinished() {
		return clip.getFramePosition() >= clip.getFrameLength();
	}
}