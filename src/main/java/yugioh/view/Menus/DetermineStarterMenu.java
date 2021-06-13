package yugioh.view.Menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;


public class DetermineStarterMenu extends WelcomeMenu {

    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/DetermineStarterMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }

}
