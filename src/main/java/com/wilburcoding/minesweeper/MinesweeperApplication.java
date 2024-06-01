package com.wilburcoding.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MinesweeperApplication extends Application {
    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 640);
        ComboBox<String> difficultyBox = (ComboBox<String>) scene.lookup("#difficulty");
        ObservableList<String> difficultyList = FXCollections.observableArrayList(
                "Easy", "Medium", "Hard");
        difficultyBox.setItems(difficultyList);
        Button start = (Button) scene.lookup("#startButton");
        MinesweeperGame game = new MinesweeperGame();
        EventHandler<ActionEvent> playHandler = e1 -> {
            VBox mainGame = (VBox) scene.lookup("#mainGame");

            Platform.runLater(() -> {
                for (int i = 0; i < 20; i++) {
                    HBox hbox = new HBox();
                    hbox.prefHeight(25.0);
                    hbox.prefWidth(500.0);
                    for (int j = 0; j < 20; j++) {
                        Button button = new Button();
                        button.setText("\u200E");
                        button.setMaxHeight(25.0);
                        button.setMinWidth(25.0);
                        button.setId(i+","+j);
                        button.setStyle("-fx-border-color: black;-fx-font-size: 10;-fx-font-weight: 800");

                        hbox.getChildren().add(button);


                    }
                    //Brutally learned that there has to be something inside the HBox for it to be shown
                    mainGame.getChildren().add(hbox);
                }
                game.generateInitialBoard();
                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        final Button button = (Button) scene.lookup("#" + i + "," + j);
                        button.setText(game.getBoard()[i][j].toString());
                        final int finalI = i;
                        final int finalJ = j;
                        EventHandler<MouseEvent> clickHandler = click -> {
                            if (click.getButton() == MouseButton.PRIMARY) {
                                if (game.getBoard()[finalI][finalJ].isMine()) {
                                    System.out.println("GGs");
                                } else {
                                    game.getBoard()[finalI][finalJ].setState(MinesweeperState.FOUND);
                                    button.setText(game.getBoard()[finalI][finalJ].toString());
                                    button.setStyle("-fx-border-color: black;-fx-font-size: 10;-fx-font-weight: 800;-fx-text-fill: #6666ff");

                                }
                            } else {
                                game.getBoard()[finalI][finalJ].setState(MinesweeperState.FLAGGED);
                                button.setText(game.getBoard()[finalI][finalJ].toString());
                                button.setStyle("-fx-border-color: black;-fx-font-size: 10;-fx-font-weight: 800;-fx-text-fill: #ff7366");

                            }

                        };
                        button.setOnMouseClicked(clickHandler);
                    }
                }

            });
        };
        start.setOnAction(playHandler);

        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}