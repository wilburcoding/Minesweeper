package com.wilburcoding.minesweeper;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        VBox vbox = (VBox) scene.lookup("#mainGame");
        HBox hbox = new HBox();
        hbox.prefHeight(25);
        hbox.prefWidth(500);
        hbox.setStyle("-fx-background-color: #ff7272");
        vbox.getChildren().add(hbox);

        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}