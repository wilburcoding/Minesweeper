package com.wilburcoding.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MinesweeperController {
    @FXML
    private Label mainText;

    @FXML
    protected void onHelloButtonClick() {
        mainText.setText("Welcome to JavaFX Application!");
    }
}