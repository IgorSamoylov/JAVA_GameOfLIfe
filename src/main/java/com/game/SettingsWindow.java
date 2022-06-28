package com.game;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SettingsWindow extends Application {
    @Override
    public void start(Stage settingsStage) {
        GridPane mainRoot = new GridPane();
        Scene mainScene = new Scene(mainRoot, 400, 300);

        ObservableList<Integer> delayValues =
                FXCollections.observableArrayList(5, 10, 15, 20, 25, 30, 40, 50, 100, 200, 300);
        ComboBox<Integer> delayBox = new ComboBox<>(delayValues);
        delayBox.setValue(GameSettings.GAME_REFRESH_DELAY / 10);

        Button delayButtonSet = new Button("Set");

        mainRoot.add(delayBox, 1, 1);
        mainRoot.add(delayButtonSet, 2, 1);
        mainRoot.setHgap(50);
        mainRoot.setVgap(20);

        delayButtonSet.setOnAction(event -> {
            try {
                GameSettings.GAME_REFRESH_DELAY = delayBox.getValue() * 10;
            } catch (Exception ex) { }
        });

        settingsStage.setTitle("Settings");
        settingsStage.setScene(mainScene);
        settingsStage.show();
    }
}
