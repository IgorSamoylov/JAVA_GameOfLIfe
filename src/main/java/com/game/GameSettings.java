package com.game;

import javafx.scene.paint.Color;

class GameSettings {
    public static int CELL_SIZE = 20;
    public static int COLS = 55;
    public static int ROWS = 30;
    public static int W_WIDTH = CELL_SIZE * COLS;
    public static int W_HEIGHT = CELL_SIZE * ROWS;
    public static Color GAMEFIELD_BACKGROUND_COLOR = Color.BISQUE;
    public static Color MAINWINDOW_BACKGROUND_COLOR = Color.CORNSILK;
    public static Color CELL_LIVE_COLOR = Color.DARKGREEN;
    public static Color CELL_DEAD_COLOR = Color.BURLYWOOD;
    public static Color LABEL_BACKGROUND = Color.AQUAMARINE;
    public static int GAME_REFRESH_DELAY = 400;
}
