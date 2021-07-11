package yugioh.client.view.gamephases;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.client.view.menus.WelcomeMenu;

import java.net.URL;

public class CardSelectionMenu extends WelcomeMenu {
    public static Stage cardSelectionStage = new Stage();

    static {
        cardSelectionStage.initOwner(WelcomeMenu.stage);
        cardSelectionStage.initStyle(StageStyle.UNDECORATED);
        cardSelectionStage.setX(500);
        cardSelectionStage.setY(250);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/CardSelectionMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void execute() throws Exception {
        start(cardSelectionStage);
    }
}
