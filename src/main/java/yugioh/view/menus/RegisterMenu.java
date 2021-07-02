package yugioh.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class RegisterMenu extends WelcomeMenu {
    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    protected String processCommand(String command) {
        return null;
    }


    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/RegisterMenu.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
