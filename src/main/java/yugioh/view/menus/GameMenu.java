package yugioh.view.menus;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.controller.menucontroller.WelcomeMenuController;

import java.net.URL;

public class GameMenu extends WelcomeMenu {
    public Pane pane;

    public void execute() throws Exception {
        start(stage);
    }

    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/GameMenu.fxml");
        pane = FXMLLoader.load(url);

        Scene scene = WelcomeMenu.createScene(pane);
        EventHandler<KeyEvent> keyListener = event -> {
            if (event.getCode() == KeyCode.SPACE) {
                try {
                    new GameMenuController().pauseClicked();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.getCode() == KeyCode.C && event.isControlDown() && event.isShiftDown()) {
                try {
                    new CheatMenu().execute();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            else if (event.getCode() == KeyCode.M && event.isControlDown()) {
                WelcomeMenuController.muteSound();
            }
            event.consume();
        };
        WelcomeMenuController.backAudio.setVolume(0.15);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyListener);
        stage.setScene(scene);
    }

    public Pane getPane() {
        return pane;
    }
}
