package com.curaxu.game.audio;

import com.curaxu.game.audio.tracks.CompoundData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoundManager {// implements Runnable {
	public static HashMap<String, Sound> currentlyPlaying = new HashMap<>();
	public static List<CompoundData> currentCompoundSounds = new ArrayList<>();

	public static void request(CompoundData data) {
		currentCompoundSounds.add(data);
		data.start();
	}

	public static Sound request(String name, String path, int type, boolean overwrite) {
		return request(name, path, type, 0, overwrite);
	}

	public static Sound request(String name, String path, int type, int loops, boolean overwrite) {
		return request(new SoundData(name, path, type, loops, overwrite));
	}

	public static Sound request(SoundData request) {
		Sound s = null;
		if (request.getType() == SoundData.PLAY) {
			s = play(request);
		} else if (request.getType() == SoundData.STOP) {
			stop(request);
		} else if (request.getType() == SoundData.STOP_AND_RESTART) stopAndRestart(request);
		else if (request.getType() == SoundData.RESTART_AND_PLAY) s = restartAndPlay(request);
		return s;
	}

	public static void tick(double delta) {
		HashMap<String, Sound> copy = new HashMap<>(currentlyPlaying);
		for (String s : copy.keySet()) {
			Sound sound = copy.get(s);
			if (sound.isFinished()) {
				request(s, sound.getPath(), SoundData.STOP, false);
			}
		}

		List<CompoundData> copyCompound = new ArrayList<>(currentCompoundSounds);
		for (CompoundData d : copyCompound) {
			d.tick(delta);
			if (d.shouldStop()) {
				System.out.println("Removing compound");
				currentCompoundSounds.remove(d);
			}
		}
	}

	public static Sound play(SoundData request) {
		System.out.println("Playing " + request.getName());
		Sound sound = new Sound(request.getPath());
		sound.loop(request.getNumLoops());
		if (currentlyPlaying.containsKey(request.getName())) {
			if (request.doesOverwrite()) {
				stop(request);
				currentlyPlaying.put(request.getName(), sound);
				sound.setTag(request.getName());
			} else {
				int n = 2;
				while (true) {
					if (currentlyPlaying.containsKey(request.getName() + "-" + n)) {
						n++;
						continue;
					}
					currentlyPlaying.put(request.getName() + "-" + n, sound);
					sound.setTag(request.getName() + "-" + n);
					break;
				}
			}
		} else {
			currentlyPlaying.put(request.getName(), sound);
			sound.setTag(request.getName());
		}
		sound.play();
		return sound;
	}

	public static void stop(SoundData request) {		
		System.out.println("Stopping " + request.getName());
		if (currentlyPlaying.containsKey(request.getName())) {
			currentlyPlaying.get(request.getName()).stop();
			currentlyPlaying.remove(request.getName());
		}
	}

	public static void stopAndRestart(SoundData request) {
		System.out.println("Stopping and restarting " + request.getName());
		if (currentlyPlaying.containsKey(request.getName())) {
			currentlyPlaying.get(request.getName()).stopAndRestart();
		}
	}

	public static Sound restartAndPlay(SoundData request) {
		System.out.println("Restarting and playing " + request.getName());
		if (currentlyPlaying.containsKey(request.getName())) {
			currentlyPlaying.get(request.getName()).restartAndPlay();
			return currentlyPlaying.get(request.getName());
		}
		return null;
	}
}

// package com.curaxu.game.audio;
// 
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// 
// public class SoundManager implements Runnable {
// 	public static List<SoundData> requests = new ArrayList<>();
// 	public static HashMap<String, Sound> currentlyPlaying = new HashMap<>();
// 	
// 	public static boolean running = false;
// 	
// 	public static synchronized void request(String name, String path, int type, boolean overwrite) {
// 		request(name, path, type, 0, overwrite);
// 	}
// 
// 	public static synchronized void request(String name, String path, int type, int loops, boolean overwrite) {
// 		requests.add(new SoundData(name, path, type, loops, overwrite));
// 	}
// 	
// 	public static synchronized void tick() {
// 		while (requests.size() > 0) {
// 			SoundData request = requests.remove(0);
// 			if (request.getType() == SoundData.PLAY) play(request);
// 			else if (request.getType() == SoundData.STOP) stop(request);
// 			else if (request.getType() == SoundData.STOP_AND_RESTART) stopAndRestart(request);
// 			else if (request.getType() == SoundData.RESTART_AND_PLAY) restartAndPlay(request);
// 		}
// 		
// 		HashMap<String, Sound> copy = new HashMap<>(currentlyPlaying);
// 		for (String s : copy.keySet()) {
// 			Sound sound = copy.get(s);
// 			if (sound.isFinished()) {
// 				request(s, sound.getPath(), SoundData.STOP, false);
// 			}
// 		}
// 	}
// 
// 	public void run() {
// 		running = true;
// 		while (running) {
// 			tick();
// 		}
// 	}
// 	
// 	public static void play(SoundData request) {
// 		System.out.println("PLAYING");
// 		Sound sound = new Sound(request.getPath());
// 		sound.loop(request.getNumLoops());
// 		if (currentlyPlaying.containsKey(request.getName())) {
// 			if (request.doesOverwrite()) {
// 				stop(request);
// 				currentlyPlaying.put(request.getName(), sound);
// 			} else {
// 				int n = 2;
// 				while (true) {
// 					if (currentlyPlaying.containsKey(request.getName() + "-" + n)) {
// 						n++;
// 						continue;
// 					}
// 					currentlyPlaying.put(request.getName() + "-" + n, sound);
// 					break;
// 				}
// 			}
// 		} else {
// 			currentlyPlaying.put(request.getName(), sound);
// 		}
// 		sound.play();
// 	}
// 	
// 	public static void stop(SoundData request) {
// 		System.out.println("STOPPING");
// 		if (currentlyPlaying.containsKey(request.getName())) {
// 			currentlyPlaying.get(request.getName()).stop();
// 			currentlyPlaying.remove(request.getName());
// 		}
// 	}
// 
// 	public static void stopAndRestart(SoundData request) {
// 		System.out.println("RESTARTING STOPPING");
// 		if (currentlyPlaying.containsKey(request.getName())) {
// 			currentlyPlaying.get(request.getName()).stopAndRestart();
// 		}
// 	}
// 
// 	public static void restartAndPlay(SoundData request) {
// 		System.out.println("RESTARTING PLAYING");
// 		if (currentlyPlaying.containsKey(request.getName())) {
// 			currentlyPlaying.get(request.getName()).restartAndPlay();
// 		}
// 	}
// }