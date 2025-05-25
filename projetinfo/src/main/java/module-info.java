module vue {
    requires javafx.controls;
    requires javafx.fxml;

    opens vue to javafx.fxml;
    exports vue;
}

