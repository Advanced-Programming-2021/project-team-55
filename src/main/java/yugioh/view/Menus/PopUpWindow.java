package yugioh.view.Menus;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpWindow extends Application {
    private final String response;
    private final boolean isError;
    private Stage popUp;
    private final EventHandler okButtonHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
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
        popUp = new Stage();
        VBox messageBox = new VBox(20);
        Text text = new Text(response);
        if (isError) {
            text.setFill(Color.RED);
            popUp.setTitle("ERROR");
            popUp.initModality(Modality.APPLICATION_MODAL);
        } else {
            text.setFill(Color.GREEN);
            popUp.initModality(Modality.NONE);
        }
//        BackgroundFill background_fill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
//        Background background = new Background(background_fill);
//        messageBox.setBackground(background);
//        scene.getStylesheets().add("/Sample/CSS/MenuCss.css");
        Button okButton = new Button("ok");
        okButton.setOnAction(okButtonHandler);
        messageBox.getChildren().add(okButton);
        text.setFont(Font.font(20));
//                messageBox.getChildren().add(text);
        messageBox.setAlignment(Pos.BASELINE_CENTER);
        Scene scene = new Scene(messageBox, 400, 100);
        popUp.setScene(scene);
        popUp.setX(640.0);
        popUp.setY(230.0);
        popUp.show();
    }
}
