package yugioh.view.menus;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.controller.menucontroller.DeckMenuController;
import yugioh.model.User;

import java.net.URL;

public class RivalSelectionMenu extends WelcomeMenu {
    private static final DeckMenuController deckMenuController = DeckMenuController.getInstance();
    private static int rounds = 0;
    private static User rival = null;
    private static boolean doCancel = false;
    private static Stage stage;

    public static int getRounds() {
        return rounds;
    }

    public static void setRounds(int rounds) {
        RivalSelectionMenu.rounds = rounds;
    }

    public static User getRival() {
        return rival;
    }

    public static void setRival(User rival) {
        RivalSelectionMenu.rival = rival;
    }

    public static boolean isDoCancel() {
        return doCancel;
    }

    public static void setDoCancel(boolean doCancel) {
        RivalSelectionMenu.doCancel = doCancel;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        RivalSelectionMenu.stage = stage;
    }

    @Override
    public void execute() throws Exception {
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/RivalSelectionMenu.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(parent);
        Stage stage = new Stage();
        RivalSelectionMenu.stage = stage;
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
