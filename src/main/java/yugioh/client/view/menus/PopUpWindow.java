package yugioh.client.view.menus;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.client.model.User;
import yugioh.client.view.gamephases.Duel;

import static yugioh.client.view.SoundPlayable.playButtonSound;

public class PopUpWindow extends Application {
    private final String response;
    private final boolean isError;
    private Stage popUp;
    private final EventHandler okButtonHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            playButtonSound("backButton");
            popUp.close();
        }
    };

    public PopUpWindow(String response) {
        if (response.startsWith("Error: ")) {
            this.response = response.substring(7);
            this.isError = true;
        } else {
            this.response = response;
            this.isError = false;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            if (!Duel.getGameController().currentTurnPlayer.getUser().equals(User.getLoggedInUser())) return;
        } catch (Exception ignored) {
        }
        popUp = new Stage();
        popUp.initOwner(stage);
        popUp.initStyle(StageStyle.UTILITY);
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setScene(generatePopup());
        popUp.show();
    }

    private Scene generatePopup() {
        Label label = new Label(response);
        label.getStyleClass().add("popupText");
        label.setTextFill(Paint.valueOf("#ccdefc"));
        if (isError) {
            label.setTextFill(Paint.valueOf("#e22b34"));
            popUp.setTitle("ERROR");
        } else
            popUp.initModality(Modality.NONE);
        Button okButton = new Button("Ok");
        okButton.getStyleClass().add("buttonShape");
        okButton.getStyleClass().add("buttonText");
        okButton.getStyleClass().add("buttonHover");
        okButton.getStyleClass().add("buttonSize");
        okButton.setTextFill(Paint.valueOf("#ccdefc"));
        okButton.setPrefHeight(60);
        okButton.setPrefWidth(200);
        okButton.setOnAction(okButtonHandler);
        VBox messageBox = new VBox(20);
        messageBox.setSpacing(10);
        messageBox.getStylesheets().add("/yugioh/CSS/Menu.css");
        messageBox.getChildren().add(new Label(""));
        messageBox.getChildren().add(label);
        messageBox.getChildren().add(okButton);
        messageBox.setAlignment(Pos.BASELINE_CENTER);
        return new Scene(messageBox, 600, 170);
    }
}
