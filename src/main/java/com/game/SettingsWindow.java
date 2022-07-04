package com.game;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class SettingsWindow extends Application {
    @Override
    public void start(Stage settingsStage) {

        ObservableList<Integer> delayValues =
                FXCollections.observableArrayList(5, 10, 15, 20, 25, 30, 40, 50, 100, 200, 300);
        ComboBox<Integer> delayBox = new ComboBox<>(delayValues);
        delayBox.setValue(GameSettings.GAME_REFRESH_DELAY / 10);
        delayBox.setOnAction(event -> {
            try {
                GameSettings.GAME_REFRESH_DELAY = delayBox.getValue() * 10;
            } catch (Exception ignored) { }
        });

        Label gameDelay = new Label("Game Delay");
        gameDelay.setAlignment(Pos.BASELINE_LEFT);

        CheckBox checkRepeats = new CheckBox("Check repeats");
        checkRepeats.setSelected(GameSettings.CHECK_REPEATS);
        checkRepeats.setOnAction(event -> {
            GameSettings.CHECK_REPEATS = checkRepeats.isSelected();
            GameStats.repeatCounter = 0;
        });

        GridPane mainRoot = new GridPane();
        mainRoot.add(delayBox, 1, 1);
        mainRoot.add(gameDelay, 2, 1);
        mainRoot.add(checkRepeats, 1, 2);
        mainRoot.setHgap(50);
        mainRoot.setVgap(20);

        Scene mainScene = new Scene(mainRoot, 400, 300);
        settingsStage.setScene(mainScene);
        settingsStage.setTitle("Settings");

        settingsStage.show();
    }
}
