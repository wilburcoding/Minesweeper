module com.wilburcoding.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wilburcoding.minesweeper to javafx.fxml;
    exports com.wilburcoding.minesweeper;
}