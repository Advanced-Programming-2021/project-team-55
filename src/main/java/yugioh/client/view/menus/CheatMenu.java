package yugioh.client.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.client.controller.CheatController;

import java.net.URL;

public class CheatMenu extends WelcomeMenu {

    private static Stage cheatStage;

    public static Stage getCheatStage() {
        return cheatStage;
    }

    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/CheatMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(pane);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UTILITY);
        newStage.initOwner(stage);
        newStage.show();
        cheatStage = newStage;
        CheatController.getInstance().console.setOnAction((event) -> CheatController.getInstance().runCommand());
    }
}
