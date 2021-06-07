package yugioh.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class PopUpWindow extends Application {

    private final String message;
    private final boolean isError;
    private Stage stage;

    public PopUpWindow(String message, boolean isError, Stage stage) {
        this.message = message;
        this.isError = isError;
        start(stage);
    }

    @Override
    public void start(Stage stage) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        if (isError) {
            dialogVbox.setStyle("-fx-background-color: #dd3737");
            dialog.setTitle("Error");
        } else dialogVbox.setStyle("-fx-background-color: #41bf3f");
        dialogVbox.getChildren().add(new Text(message));
        Button button = new Button("OK!");
        button.setOnMouseClicked(this::close);
        dialogVbox.getChildren().add(button);
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 100);
        dialogScene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("/pacman/CSS/pagesStyle.css")).toExternalForm());
        dialog.setScene(dialogScene);
        this.stage = dialog;
        dialog.show();
    }

    private void close(MouseEvent mouseEvent) {
        this.stage.close();
    }

}
