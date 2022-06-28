package com.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.Random;

public class GameEngine extends Thread {
    private static final Random random = new Random();
    private final GraphicsContext gameFieldGraphics;
    private final Label aliveLabel;
    private final Label epochLabel;
    private boolean[][] gridArray;
    private boolean[][] nextGridArray;

    public GameEngine(GraphicsContext gameFieldGraphics, Label aliveLabel, Label epochLabel) {
        this.gameFieldGraphics = gameFieldGraphics;
        this.aliveLabel = aliveLabel;
        this.epochLabel = epochLabel;
        gridArray = new boolean[GameSettings.COLS][GameSettings.ROWS];
        nextGridArray = new boolean[GameSettings.COLS][GameSettings.ROWS];
        gameFieldGraphics.setFill(GameSettings.BACKGROUND_COLOR);
        gameFieldGraphics.fillRect(0, 0, GameSettings.W_WIDTH, GameSettings.W_HEIGHT);
    }

    public void randomFill() {
        for (int i = 0; i < GameSettings.COLS; i++) {
            for (int j = 0; j < GameSettings.ROWS; j++) {
                gridArray[i][j] = random.nextBoolean();
            }
        }
        draw();
    }

    public void drawCell(int i, int j) {
        gridArray[i][j] = !gridArray[i][j];
        draw();
    }

    public void clearField() {
        Arrays.stream(gridArray).forEach(arr -> Arrays.fill(arr, false));
        GameStats.epoch = 0;
        draw();
    }

    private void draw() {
        int alive = 0;
        for (int i = 0; i < GameSettings.COLS; i++) {
            for (int j = 0; j < GameSettings.ROWS; j++) {
                if (gridArray[i][j]) {
                    gameFieldGraphics.setFill(GameSettings.CELL_LIVE_COLOR);
                    gameFieldGraphics.fillRect((i * GameSettings.CELL_SIZE) + 1, (j * GameSettings.CELL_SIZE) + 1,
                            GameSettings.CELL_SIZE - 2, GameSettings.CELL_SIZE - 2);
                    alive++;
                } else {
                    gameFieldGraphics.setFill(GameSettings.CELL_DEAD_COLOR);
                    gameFieldGraphics.fillRect((i * GameSettings.CELL_SIZE) + 1, (j * GameSettings.CELL_SIZE) + 1,
                            GameSettings.CELL_SIZE - 2, GameSettings.CELL_SIZE - 2);
                }
            }
        }
        GameStats.alive = alive;
        String aliveNumber = String.format("%06d", alive);
        aliveLabel.setText(aliveNumber);

        String epochNumber = String.format("%06d", GameStats.epoch);
        epochLabel.setText(epochNumber);
    }

    public void nextStep() {
        for (int i = 0; i < GameSettings.COLS; i++) {
            for (int j = 0; j < GameSettings.ROWS; j++) {
                int neighbors = countAliveNeighbors(i, j);

                if (neighbors == 3) {
                    nextGridArray[i][j] = true;
                } else if (neighbors < 2 || neighbors > 3) {
                    nextGridArray[i][j] = false;
                } else {
                    nextGridArray[i][j] = gridArray[i][j];
                }
            }
        }
        // Swap these arrays to prevent memory clogging
        boolean[][] referenceArrayHolder = gridArray;
        gridArray = nextGridArray;
        nextGridArray = referenceArrayHolder;

        GameStats.epoch++;
        draw();
    }

    private int countAliveNeighbors(int i, int j) {
        int sum = 0;
        int iStart = i == 0 ? 0 : -1;
        int iEndInclusive = i == gridArray.length - 1 ? 0 : 1;
        int jStart = j == 0 ? 0 : -1;
        int jEndInclusive = j == gridArray[0].length - 1 ? 0 : 1;

        for (int k = iStart; k <= iEndInclusive; k++) {
            for (int l = jStart; l <= jEndInclusive; l++) {
                sum += gridArray[i + k][l + j] ? 1 : 0;
            }
        }
        sum -= gridArray[i][j] ? 1 : 0;

        return sum;
    }
}
