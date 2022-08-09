package com.curaxu.game.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SoundManager implements Runnable {
	public static HashMap<String, Sound> sounds = new HashMap<>();
	public static List<SoundRequest> requests = new ArrayList<>();

	public static boolean finishedLoading = false;
	public static boolean running = false;

	public static synchronized void request(String name) {
		requests.add(new SoundRequest(name));
	}

	public synchronized void tick() {
		Iterator<SoundRequest> it = requests.iterator();

		while (it.hasNext()) {
			SoundRequest s = it.next();
			play(s.getName());
			it.remove();
		}
	}

	public void run() {
		loadAllSounds();
		System.out.println("Finished loading all sounds...");

		running = true;
		while (running) {
			tick();
		}
	}

	public static void stop(String name) {
		if (!sounds.containsKey(name)) System.err.println("Cannot find sound \"" + name + "\"");
		else sounds.get(name).stop();
	}

	public static void play(String name) {
		if (!sounds.containsKey(name)) System.err.println("Cannot find sound \"" + name + "\"");
		else sounds.get(name).play();
	}

	public static void loadAllSounds() {
		finishedLoading = true;
	}
}