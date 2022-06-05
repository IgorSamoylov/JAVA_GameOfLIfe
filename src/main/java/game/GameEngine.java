package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.Random;

import static game.GameSettings.*;

public class GameEngine {
    private final GraphicsContext gameFieldGraphics;
    private final Label aliveLabel;
    private final Label epochLabel;
    private boolean[][] gridArray;
    private static final Random random = new Random();

    public GameEngine(GraphicsContext gameFieldGraphics, Label aliveLabel, Label epochLabel) {
        this.gameFieldGraphics = gameFieldGraphics;
        this.aliveLabel = aliveLabel;
        this.epochLabel = epochLabel;
        gridArray = new boolean[COLS][ROWS];
        gameFieldGraphics.setFill(BACKGROUND_COLOR);
        gameFieldGraphics.fillRect(0, 0, W_WIDTH, W_HEIGHT);
    }

    public void randomFill() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                gridArray[i][j] = random.nextBoolean();
            }
        }
        draw();
    }

    public void drawCell(Double x, Double y) {
        int i = (int) (x / CELL_SIZE);
        int j = (int) (y / CELL_SIZE);
        gridArray[i][j] = !gridArray[i][j];
        draw();
    }

    public void clearField() {
        Arrays.stream(gridArray).forEach(arr -> Arrays.fill(arr,false));
        GameOfLife.epoch = 0;
        draw();
    }

    private void draw() {
        int alive = 0;
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (gridArray[i][j]) {
                    gameFieldGraphics.setFill(CELL_LIVE_COLOR);
                    gameFieldGraphics.fillRect((i * CELL_SIZE) + 1, (j * CELL_SIZE) + 1,
                            CELL_SIZE - 2, CELL_SIZE - 2);
                    alive++;
                } else {
                    gameFieldGraphics.setFill(CELL_DEAD_COLOR);
                    gameFieldGraphics.fillRect((i * CELL_SIZE) + 1, (j * CELL_SIZE) + 1,
                            CELL_SIZE - 2, CELL_SIZE - 2);
                }
            }
        }
        GameOfLife.alive = alive;
        String aliveNumber = String.format("%06d", alive);
        aliveLabel.setText(aliveNumber);

        String epochNumber = String.format("%06d", GameOfLife.epoch);
        epochLabel.setText(epochNumber);
    }

    public void nextStep() {
        boolean[][] nextArray = new boolean[COLS][ROWS];

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                int neighbors = countAliveNeighbors(i, j);

                if (neighbors == 3) {
                    nextArray[i][j] = true;
                } else if (neighbors < 2 || neighbors > 3) {
                    nextArray[i][j] = false;
                } else {
                    nextArray[i][j] = gridArray[i][j];
                }
            }
        }
        gridArray = nextArray;
        GameOfLife.epoch++;
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
