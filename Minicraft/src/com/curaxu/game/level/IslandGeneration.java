package com.curaxu.game.level;

import java.util.Random;

public class IslandGeneration {
    private static final int MAX_ITERATIONS = 10;

    private int width, height;
    private boolean[] cells;
    private double trueProbs;

    private int numIterations = 1;
    private Random random = new Random(100);

    public IslandGeneration(int width, int height, double trueProbs) {
        this.width = width;
        this.height = height;
        this.trueProbs = trueProbs;
        this.cells = new boolean[width * height];
    }

    public void generate() {
        initialiseCells();

        while (numIterations < MAX_ITERATIONS) {
            nextIteration();
            numIterations++;
        }
    }

    public void initialiseCells() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || y == 0 || x == width - 1 || x == height - 1) cells[x + y * width] = true;
                else cells[x + y * width] = random.nextDouble() < trueProbs;
            }
        }
    }

    public void nextIteration() {
        boolean[] newCells = new boolean[cells.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int n = numNeighbours(x, y);
                if (cells[x + y * width] && (n == 0 || n == 1 || n == 2)) newCells[x + y * width] = false;
                else if (!cells[x + y * width] && (n == 6 || n == 7 || n == 8)) newCells[x + y * width] = true;
                else newCells[x + y * width] = cells[x + y * width];
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x + y * width] = newCells[x + y * width];
            }
        }
    }

    public int numNeighbours(int x, int y) {
        int neighbours = 0;
        for (int yy = -1; yy <= 1; yy++) {
            for (int xx = -1; xx <= 1; xx++) {
                if (yy == 0 && xx == 0) continue;
                int yyy = y + yy;
                int xxx = x + xx;
                if (yyy >= 0 && xxx >= 0 && yyy < height && xxx < width) {
                    neighbours += cells[xxx + yyy * width] ? 1 : 0;
                } else {
                    neighbours++;
                }
            }
        }
        return neighbours;
    }

    public boolean[] getCells() {
        return cells;
    }
}