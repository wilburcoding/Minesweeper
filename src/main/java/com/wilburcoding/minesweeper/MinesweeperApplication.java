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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MinesweeperApplication extends Application {
    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MinesweeperApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 550);
        ComboBox<String> difficultyBox = (ComboBox<String>) scene.lookup("#difficulty");
        ObservableList<String> difficultyList = FXCollections.observableArrayList(
                "Easy", "Medium", "Hard");
        difficultyBox.setItems(difficultyList);
        Button start = (Button) scene.lookup("#startButton");
        EventHandler<ActionEvent> playHandler = e1 -> {
            VBox mainGame = (VBox) scene.lookup("#mainGame");

            Platform.runLater(() -> {
                for (int i = 0; i < 20; i++) {
                    HBox hbox = new HBox();
                    hbox.prefHeight(25.0);
                    hbox.prefWidth(500.0);
                    for (int j = 0; j < 20; j++) {
                        Button button = new Button();
                        button.setText("1");
                        button.prefHeight(25.0);
                        button.prefWidth(25.0);
                        button.setStyle("-fx-border-color: black");
                        hbox.getChildren().add(button);


                    }
                    //Brutally learned that there has to be something inside the HBox for it to be shown
                    mainGame.getChildren().add(hbox);
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