package game;

import javafx.scene.paint.Color;

class GameSettings {
    public static int CELL_SIZE = 20;
    public static int COLS = 55;
    public static int ROWS = 30;
    public static int W_WIDTH = CELL_SIZE * COLS;
    public static int W_HEIGHT = CELL_SIZE * ROWS;
    public static Color BACKGROUND_COLOR = Color.AQUA;
    public static Color CELL_LIVE_COLOR = Color.DARKBLUE;
    public static Color CELL_DEAD_COLOR = Color.BURLYWOOD;
    public static Color LABEL_BACKGROUND = Color.AQUA;
    public static long GAME_REFRESH_DELAY = 300_000_000;
}
