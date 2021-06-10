package yugioh.view.Menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class EditDeckMenu extends WelcomeMenu {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/EditDeckMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        EditDeckMenu.stage = stage;
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
