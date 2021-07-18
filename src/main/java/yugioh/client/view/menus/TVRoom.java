package yugioh.client.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class TVRoom extends WelcomeMenu{
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane= FXMLLoader.load(getClass().getResource("/yugioh/fxml/TVRoom.fxml"));
        Scene scene=WelcomeMenu.createScene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void execute() throws Exception {
        start(stage);
    }
}
