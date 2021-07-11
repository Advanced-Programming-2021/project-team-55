package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class AdminShopMenu extends AdminWelcomeMenu{
    @Override
    public void start(Stage stage) throws Exception {
        URL url=getClass().getResource("/Sample/fxml/AdminShopMenu.fxml");
        Pane pane= FXMLLoader.load(url);
        stage.setScene(new Scene(pane));
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void execute() {
        try {
            start(stage);
        } catch (Exception e) {
        }
    }
}
