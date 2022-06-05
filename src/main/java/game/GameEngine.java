package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;
import java.util.Random;

import static game.GameSettings.*;

public class GameEngine {
    private final GraphicsContext gameFieldGraphics;
    private int[][] gridArray;
    private static final Random random = new Random();

    public GameEngine(GraphicsContext gameFieldGraphics) {
        this.gameFieldGraphics = gameFieldGraphics;
        gridArray = new int[COLS][ROWS];
        gameFieldGraphics.setFill(BACKGROUND_COLOR);
        gameFieldGraphics.fillRect(0, 0, W_WIDTH, W_HEIGHT);
    }

    public void randomFill() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                gridArray[i][j] = random.nextInt(2);
            }
        }
        draw();
    }

    public void drawCell(Double x, Double y) {
        int i = (int) (x / CELL_SIZE);
        int j = (int) (y / CELL_SIZE);
        gridArray[i][j] = 1;

        draw();
    }

    public void clearField() {
        Arrays.stream(gridArray).forEach(arr -> Arrays.fill(arr,0));
        draw();
    }

    private void draw() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if (gridArray[i][j] == 1) {
                    gameFieldGraphics.setFill(CELL_LIVE_COLOR);
                    gameFieldGraphics.fillRect((i * CELL_SIZE) + 1, (j * CELL_SIZE) + 1,
                            CELL_SIZE - 2, CELL_SIZE - 2);
                } else {
                    gameFieldGraphics.setFill(CELL_DEAD_COLOR);
                    gameFieldGraphics.fillRect((i * CELL_SIZE) + 1, (j * CELL_SIZE) + 1,
                            CELL_SIZE - 2, CELL_SIZE - 2);
                }
            }
        }
    }

    public void nextStep() {
        int[][] nextArray = new int[COLS][ROWS];

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                int neighbors = countAliveNeighbors(i, j);

                if (neighbors == 3) {
                    nextArray[i][j] = 1;
                } else if (neighbors < 2 || neighbors > 3) {
                    nextArray[i][j] = 0;
                } else {
                    nextArray[i][j] = gridArray[i][j];
                }
            }
        }
        gridArray = nextArray;
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
                sum += gridArray[i + k][l + j];
            }
        }
        sum -= gridArray[i][j];

        return sum;
    }

    //private int countAliveNeighborsAdv(int i, int j) {

        //for(int k = )
   // }
}
