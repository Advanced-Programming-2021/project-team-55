package yugioh.view.menus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.controller.menucontroller.DeckMenuController;
import yugioh.model.User;

import java.net.URL;

public class EndOfGameMenu extends WelcomeMenu {

    private static String resultString;
    private static Stage stage;

    public void execute(String resultString) throws Exception {
        this.resultString = resultString;
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/EndOfGameMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = new Scene(parent);
        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(WelcomeMenu.getStage());
        stage.setScene(scene);
        stage.show();
    }

    public static void closeStage() {
        stage.close();
    }

    public static String getResultString() {
        return resultString;
    }

}
