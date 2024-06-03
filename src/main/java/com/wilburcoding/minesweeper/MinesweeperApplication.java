package com.wilburcoding.minesweeper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MinesweeperApplication extends Application {
    final String[] AMT_COLORS = {"#1976d2", "#3a8e3d", "#d33433", "#7b1fa2", "#fd9004", "#0197a6", "#424242", "#D9D9D9"};
    public int seconds = 1;

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 640);
        ComboBox<String> difficultyBox = (ComboBox<String>) scene.lookup("#difficulty");
        ObservableList<String> difficultyList = FXCollections.observableArrayList(
                "Easy", "Medium", "Hard");
        difficultyBox.setItems(difficultyList);
        difficultyBox.setValue("Medium");
        Button start = (Button) scene.lookup("#startButton");
        Label mainLabel = (Label) scene.lookup("#infoText");
        MinesweeperGame game = new MinesweeperGame(5);

        Timeline updateT = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            if (game.isGameOngoing()) {
                                int minutes = seconds / 60;
                                int sec = seconds % 60;
                                mainLabel.setText(minutes + ":" + (sec < 10 ? "0" : "") + sec + " - " + game.getMineLeft() + " flags left");
                                seconds++;

                            }
                        }));
        updateT.setCycleCount(Timeline.INDEFINITE);
        updateT.play();
        EventHandler<ActionEvent> playHandler = e1 -> {
            VBox mainGame = (VBox) scene.lookup("#mainGame");

            Platform.runLater(() -> {
                mainLabel.setText("Loading...");
                String value = difficultyBox.getValue();
                if (value.equals("Hard")) {
                    game.setBoard(20);

                } else if (value.equals("Easy")) {
                    game.setBoard(10);

                } else {
                    game.setBoard(15);

                }

                for (int i = 0; i < game.getSize(); i++) {
                    HBox hbox = new HBox();
                    hbox.prefHeight(500.0 / game.getSize());
                    hbox.prefWidth(500.0);
                    for (int j = 0; j < game.getSize(); j++) {
                        Button button = new Button();
                        button.setText("\u200E");
                        button.setMinHeight(500.0 / game.getSize());
                        button.setMinWidth(500.0 / game.getSize());
                        button.setMaxWidth(500.0 / game.getSize());
                        button.setId(i + "," + j);
                        button.setStyle("-fx-border-color: black;-fx-font-size: 10;-fx-font-weight: 800;-fx-background-color: " + ((i + j) % 2 == 0 ? "#8cff8c" : "#68c668"));

                        hbox.getChildren().add(button);


                    }
                    //Brutally learned that there has to be something inside the HBox for it to be shown
                    mainGame.getChildren().add(hbox);
                }
                game.generateInitialBoard();
                game.clearArea(game.getSize()/2 - 1,  game.getSize()/2 - 1);
                mainLabel.setText("0.00 - " + game.getMineCount() + " flags left");
                for (int i = 0; i < game.getSize(); i++) {
                    for (int j = 0; j < game.getSize(); j++) {
                        final Button button = (Button) scene.lookup("#" + i + "," + j);
                        button.setText(game.getBoard()[i][j].toString());
                        String baseStyle = "-fx-padding:0;-fx-background-radius: 0;-fx-border-color: black;-fx-border-width:0;-fx-font-size: 18;-fx-font-weight: 900;";
                        button.setStyle(baseStyle + "-fx-background-color: " + ((i + j) % 2 == 0 ? "#a9d751" : "#a1cf48"));
                        if (game.getBoard()[i][j].getState() == MinesweeperState.FOUND) {
                            button.setStyle(baseStyle + "-fx-background-color: " + ((i + j) % 2 == 0 ? "#e5c29f" : "#d6b899"));
                        }
                        if (game.getBoard()[i][j].getCountMines() > 0) {
                            button.setTextFill(Color.valueOf(AMT_COLORS[game.getBoard()[i][j].getCountMines() - 1]));

                        }
                        final int finalI = i;
                        final int finalJ = j;
                        EventHandler<MouseEvent> clickHandler = click -> {
                            MinesweeperCell cell = game.getBoard()[finalI][finalJ];
                            if (click.getButton() == MouseButton.PRIMARY) {
                                if (cell.isMine()) {
                                    System.out.println("GGs");
                                } else {
                                    if (cell.getState() != MinesweeperState.FOUND) {
                                        cell.setState(MinesweeperState.FOUND);
                                        if (cell.getCountMines() == 0) {
                                            game.clearArea(finalI, finalJ);
                                            //Refresh the board
                                            refreshBoard(scene, game, baseStyle);
                                        }
                                        button.setText(cell.toString());
                                        button.setStyle(baseStyle + "-fx-background-color: " + ((finalI + finalJ) % 2 == 0 ? "#e5c29f" : "#d6b899"));
                                        if (cell.getCountMines() > 0) {
                                            button.setTextFill(Color.valueOf(AMT_COLORS[cell.getCountMines() - 1]));

                                        }
                                    }
                                }
                            } else if (click.getButton() == MouseButton.SECONDARY) {
                                if (cell.getState() != MinesweeperState.FOUND) {

                                    if (cell.getState() == MinesweeperState.FLAGGED) {
                                        cell.setState(MinesweeperState.HIDDEN);
                                    } else {
                                        cell.setState(MinesweeperState.FLAGGED);
                                    }
                                    button.setText(cell.toString());
                                    button.setTextFill(Color.valueOf("e53400"));
                                    button.setStyle(baseStyle + "-fx-background-color: " + ((finalI + finalJ) % 2 == 0 ? "#a9d751" : "#a1cf48"));
                                }

                            } else if (click.getButton() == MouseButton.MIDDLE) {
                                if (cell.getState() == MinesweeperState.FOUND) {
                                    boolean result = game.speedClear(finalI, finalJ);
                                    if (!result) {
                                        refreshBoard(scene, game, baseStyle);
                                    } else {
                                        System.out.println("you lost");
                                    }

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

    private void refreshBoard(Scene scene, MinesweeperGame game, String baseStyle) {
        for (int k = 0; k < game.getSize(); k++) {
            for (int l = 0; l < game.getSize(); l++) {
                final Button buttonChange = (Button) scene.lookup("#" + k + "," + l);
                buttonChange.setText(game.getBoard()[k][l].toString());
                buttonChange.setStyle(baseStyle + "-fx-background-color: " + ((k + l) % 2 == 0 ? "#a9d751" : "#a1cf48"));
                if (game.getBoard()[k][l].state == MinesweeperState.FOUND) {
                    buttonChange.setStyle(baseStyle + "-fx-background-color: " + ((k + l) % 2 == 0 ? "#e5c29f" : "#d6b899"));
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}