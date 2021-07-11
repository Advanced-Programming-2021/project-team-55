package yugioh.client.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class CardGeneratorMenu extends WelcomeMenu {
    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/CardGeneratorMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(parent);
        stage.setScene(scene);
        stage.show();
    }
}