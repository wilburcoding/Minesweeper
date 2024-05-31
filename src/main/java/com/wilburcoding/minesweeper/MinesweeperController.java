package com.wilburcoding.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MinesweeperController {
    public VBox mainGame;
    @FXML
    private Label mainText;

    @FXML
    protected void startButtonClick() {
        mainText.setText("Welcome to JavaFX Application!");
    }
}