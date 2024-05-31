package com.wilburcoding.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MinesweeperController {
    public VBox mainGame;
    @FXML
    private Label mainText;

    @FXML
    protected void startButtonClick() {
        mainText.setText("Minesweeper!!");
        for (int i = 0; i < 20; i++) {
            HBox hbox = new HBox();
            hbox.prefHeight(25.0);
            hbox.prefWidth(500.0);
            hbox.setStyle("-fx-border-color: black");
            hbox.getChildren().add(new Label(""));
            //Brutally learned that there has to be something inside the HBox for it to be shown
            mainGame.getChildren().add(hbox);
        }

    }
}