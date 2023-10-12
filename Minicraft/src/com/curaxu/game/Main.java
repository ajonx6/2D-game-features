package com.curaxu.game;

import javax.swing.JFrame;

import com.curaxu.game.util.ThreadPool;
import com.curaxu.game.audio.SoundManager;

public class Main {
	public static void main(String[] args) {
		ThreadPool pool = new ThreadPool(2);
		System.out.println("Running on OS: " + System.getProperty("os.name"));

		Game game = Game.getInstance();
		game.frame = new JFrame(Game.TITLE);
		game.frame.setUndecorated(true);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setSize(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		game.frame.setResizable(false);
		game.frame.setLocationRelativeTo(null);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setVisible(true);

		pool.runTask(new SoundManager());
		pool.runTask(game);
		pool.join();
	}
}