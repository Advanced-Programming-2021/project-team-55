package yugioh.view.menus;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;

public class GameMenu extends WelcomeMenu {

    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/GameMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = new Scene(parent);
        stage.setScene(scene);

        EventHandler<KeyEvent> keyListener = event -> {
            if (event.getCode() == KeyCode.SPACE) {
                //todo pause game
            } else if (event.getCode() == KeyCode.C && event.isControlDown() && event.isShiftDown()) {
                try {
                    new CheatMenu().execute();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            event.consume();
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyListener);
    }
}
