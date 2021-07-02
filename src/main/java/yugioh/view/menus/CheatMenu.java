package yugioh.view.menus;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.controller.CheatController;

import java.net.URL;

public class CheatMenu extends WelcomeMenu {

    private static Stage cheatMenuStage;

    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/CheatMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UTILITY);
        newStage.initOwner(stage);
        newStage.show();
        cheatMenuStage = newStage;
//        EventHandler<KeyEvent> keyListener = event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                CheatController.getInstance().processCommand(CheatController.getInstance().console.getText());
//            }
//            event.consume();
//        };
//        CheatMenu.getCheatMenuStage().addEventFilter(KeyEvent.KEY_PRESSED, keyListener);
        CheatController.getInstance().console.setOnAction((event) -> CheatController.getInstance().runCommand());
    }

    public static Stage getCheatMenuStage() {
        return cheatMenuStage;
    }

}
