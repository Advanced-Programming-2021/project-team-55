package yugioh.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.model.Player;

import java.net.URL;


public class DetermineStarterMenu extends WelcomeMenu {

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/DetermineStarterMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(pane);
        Stage stage = new Stage();
        DetermineStarterMenu.stage = stage;
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
