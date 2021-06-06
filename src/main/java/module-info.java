module yugioh {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires gson;
//    requires opencsv;
    requires java.logging;

    opens yugioh.view to javafx.fxml;
    exports yugioh.view;
    opens yugioh.controller to javafx.fxml;
    exports yugioh.controller;
    opens yugioh.model to javafx.fxml;
    exports yugioh.model;

}