package game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static game.GameSettings.*;


public class GameOfLife extends Application {
    public static long epoch = 0;
    public static long alive = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox mainRoot = new VBox(20);
        Scene mainScene = new Scene(mainRoot, W_WIDTH, W_HEIGHT + 120);

        final Canvas gameField = new Canvas(W_WIDTH, W_HEIGHT);

        // Upper control panel setup
        Button randomFillB = new Button("Random Fill");
        Button runB = new Button("Run");
        Button stopB = new Button("Stop");
        Button stepB = new Button("Step");
        Button clearB = new Button("Clear");

        HBox controlPanel1 = new HBox(20, randomFillB, runB, stopB, stepB, clearB);
        controlPanel1.setAlignment(Pos.BASELINE_CENTER);

        // Lower control panel setup
        Button fasterB = new Button("Faster");
        Button slowerB = new Button("Slower");
        Label aliveLabel = new Label("000000");
        Label epochLabel = new Label("000000");
        Background labelBackground = new Background(
                new BackgroundFill(LABEL_BACKGROUND, null, null));
        aliveLabel.setBackground(labelBackground);
        epochLabel.setBackground(labelBackground);

        HBox controlPanel2 = new HBox(20, aliveLabel, fasterB, slowerB, epochLabel);
        controlPanel2.setAlignment(Pos.BASELINE_CENTER);

        mainRoot.getChildren().addAll(gameField, controlPanel1, controlPanel2);

        // Main window setup
        primaryStage.setTitle("CONWAY'S GAME OF LIFE");
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        //primaryStage.setFullScreen(true);

        // Game engine init
        GraphicsContext gameFieldGraphics = gameField.getGraphicsContext2D();
        GameEngine gameEngine = new GameEngine(gameFieldGraphics, aliveLabel, epochLabel);
        gameEngine.clearField();
        GameAnimationTimer runAnimation = new GameAnimationTimer(gameEngine);

        // Buttons bindings setup
        gameField.setOnMouseClicked(mouseEvent ->
                gameEngine.drawCell(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
        randomFillB.setOnAction(keyEvent -> gameEngine.randomFill());
        runB.setOnAction(keyEvent -> runAnimation.start());
        stepB.setOnAction(keyEvent -> gameEngine.nextStep());
        stopB.setOnAction(keyEvent -> runAnimation.stop());
        clearB.setOnAction(keyEvent -> {
            gameEngine.clearField();
            runAnimation.stop();
        });
        fasterB.setOnAction(keyEvent -> GAME_REFRESH_DELAY -= 50);
        slowerB.setOnAction(keyEvent -> GAME_REFRESH_DELAY += 50);

        // Alert on close window setup
        Alert closeWindowAlert = new Alert(Alert.AlertType.CONFIRMATION);
        closeWindowAlert.setContentText("Really close?");
        primaryStage.setOnCloseRequest(event ->
                closeWindowAlert.showAndWait().ifPresent(btnType -> {
                    if (btnType == ButtonType.CANCEL) event.consume();
                }));

        // Create main window
        primaryStage.show();
    }
}
