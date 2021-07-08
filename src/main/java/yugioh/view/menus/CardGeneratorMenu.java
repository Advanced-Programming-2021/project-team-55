package yugioh.view.menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import yugioh.model.User;
import yugioh.model.cards.Card;

import java.net.URL;
import java.util.ArrayList;

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
