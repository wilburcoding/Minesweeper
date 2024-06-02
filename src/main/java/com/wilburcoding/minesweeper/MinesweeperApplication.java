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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MinesweeperApplication extends Application {
    final String[] AMT_COLORS = {"#1976d2","#3a8e3d","#d33433","#7b1fa2","#fd9004","#0197a6","#424242","#D9D9D9"};

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
                        button.setMaxWidth(25.0);
                        button.setId(i+","+j);
                        button.setStyle("-fx-border-color: black;-fx-font-size: 10;-fx-font-weight: 800;-fx-background-color: " + ((i+j)%2==0 ? "#8cff8c":"#68c668"));

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
                        String baseStyle = "-fx-padding:0;-fx-background-radius: 0;-fx-border-color: black;-fx-border-width:0;-fx-font-size: 18;-fx-font-weight: 900;";
                        button.setStyle(baseStyle + "-fx-background-color: " + ((i + j) % 2 == 0 ? "#a9d751" : "#a1cf48"));
                        if (game.getBoard()[i][j].state == MinesweeperState.FOUND) {
                            button.setStyle(baseStyle + "-fx-background-color: " + ((i + j) % 2 == 0 ? "#e5c29f" : "#d6b899"));
                        }
                        if (game.getBoard()[i][j].getCountMines() > 0) {
                            button.setTextFill(Color.valueOf(AMT_COLORS[game.getBoard()[i][j].getCountMines()-1]));

                        }
                        final int finalI = i;
                        final int finalJ = j;
                        EventHandler<MouseEvent> clickHandler = click -> {
                            if (click.getButton() == MouseButton.PRIMARY) {
                                if (game.getBoard()[finalI][finalJ].isMine()) {
                                    System.out.println("GGs");
                                } else {
                                    game.getBoard()[finalI][finalJ].setState(MinesweeperState.FOUND);
                                    button.setText(game.getBoard()[finalI][finalJ].toString());
                                    button.setStyle(baseStyle + "-fx-background-color: " + ((finalI + finalJ) % 2 == 0 ? "#e5c29f" : "#d6b899"));
                                    if (game.getBoard()[finalI][finalJ].getCountMines() > 0) {
                                        button.setTextFill(Color.valueOf(AMT_COLORS[game.getBoard()[finalI][finalJ].getCountMines()-1]));

                                    }
                                }
                            } else {
                                if (game.getBoard()[finalI][finalJ].getState() == MinesweeperState.FLAGGED) {
                                    game.getBoard()[finalI][finalJ].setState(MinesweeperState.HIDDEN);
                                    button.setText(game.getBoard()[finalI][finalJ].toString());
                                    button.setTextFill(Color.valueOf("e53400"));
                                    button.setStyle(baseStyle + "-fx-background-color: " + ((finalI + finalJ) % 2 == 0 ? "#a9d751" : "#a1cf48"));
                                } else {
                                    game.getBoard()[finalI][finalJ].setState(MinesweeperState.FLAGGED);
                                    button.setText(game.getBoard()[finalI][finalJ].toString());
                                    button.setTextFill(Color.valueOf("e53400"));
                                    button.setStyle(baseStyle + "-fx-background-color: " + ((finalI + finalJ) % 2 == 0 ? "#a9d751" : "#a1cf48"));
                                }


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