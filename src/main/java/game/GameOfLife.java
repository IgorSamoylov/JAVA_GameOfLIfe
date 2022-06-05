package game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static game.GameSettings.*;


public class GameOfLife extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox mainRoot = new VBox(20);
        Scene mainScene = new Scene(mainRoot, W_WIDTH, W_HEIGHT + 120);

        final Canvas gameField = new Canvas(W_WIDTH, W_HEIGHT);

        Button randomFillB = new Button("Random Fill");
        Button runB = new Button("Run");
        Button stopB = new Button("Stop");
        Button stepB = new Button("Step");
        Button clearB = new Button("Clear");
        HBox controlPanel1 = new HBox(20, randomFillB, runB, stopB,stepB, clearB);
        controlPanel1.setAlignment(Pos.BASELINE_CENTER);

        Button fasterB = new Button("Faster");
        Button slowerB = new Button("Slower");
        HBox controlPanel2 = new HBox(20, fasterB, slowerB);
        controlPanel2.setAlignment(Pos.BASELINE_CENTER);

        mainRoot.getChildren().addAll(gameField, controlPanel1, controlPanel2);

        primaryStage.setTitle("CONWAY'S GAME OF LIFE");
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        //primaryStage.setFullScreen(true);

        GraphicsContext gameFieldGraphics = gameField.getGraphicsContext2D();
        GameEngine gameEngine = new GameEngine(gameFieldGraphics);
        gameEngine.clearField();

        GameAnimationTimer runAnimation = new GameAnimationTimer(gameEngine);

        gameField.setOnMouseClicked(mouseEvent ->
                gameEngine.drawCell(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
        randomFillB.setOnAction(keyEvent -> gameEngine.randomFill());
        runB.setOnAction(keyEvent -> runAnimation.start());
        stepB.setOnAction(keyEvent -> gameEngine.nextStep());
        stopB.setOnAction(keyEvent -> runAnimation.stop());
        clearB.setOnAction(keyEvent -> gameEngine.clearField());
        fasterB.setOnAction(keyEvent -> GAME_REFRESH_DELAY-= 50);
        slowerB.setOnAction(keyEvent -> GAME_REFRESH_DELAY+= 50);

        Alert closeWindowAlert = new Alert(Alert.AlertType.CONFIRMATION);
        closeWindowAlert.setContentText("Really close?");
        primaryStage.setOnCloseRequest(event ->
                closeWindowAlert.showAndWait().ifPresent(btnType -> {
                    if (btnType == ButtonType.CANCEL) event.consume();
                }));

        primaryStage.show();
    }
}
