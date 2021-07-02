package yugioh.view.gamephases;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.cards.Card;
import yugioh.view.menus.WelcomeMenu;

import java.net.URL;
import java.util.ArrayList;

public class Graveyard extends WelcomeMenu {

    private static Stage graveyardStage;
    private static ArrayList<Card> graveyardCards;
    private GameController gameController;

    public static ArrayList<Card> getGraveyardCards() {
        return graveyardCards;
    }

    public static Stage getGraveyardStage() {
        return graveyardStage;
    }

    public void execute(ArrayList<Cell> graveyard) {
        gameController = Duel.getGameController();
        graveyardCards = new ArrayList<>();
        for (Cell cell : graveyard) {
            graveyardCards.add(cell.getCellCard());
        }
        try {
            start(WelcomeMenu.getStage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/Graveyard.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(parent);
        scene.setFill(Color.TRANSPARENT);
        graveyardStage = new Stage();
        graveyardStage.setX(720);
        graveyardStage.initOwner(primaryStage);
        graveyardStage.initStyle(StageStyle.TRANSPARENT);
        graveyardStage.setScene(scene);
        graveyardStage.show();
    }

    protected String processCommand(String command) {
        String response = "";
        if (command.matches("back")) {
            gameController.currentPhase = gameController.phases.get(gameController.phases.size() - 2);
        } else {
            response = GameResponses.INVALID_COMMAND.response;
        }
        return response;
    }
}
