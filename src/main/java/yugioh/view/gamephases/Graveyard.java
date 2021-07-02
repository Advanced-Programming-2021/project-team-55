package yugioh.view.gamephases;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.model.board.Cell;
import yugioh.model.cards.Card;
import yugioh.view.menus.WelcomeMenu;

import java.net.URL;
import java.util.ArrayList;

public class Graveyard  extends WelcomeMenu{

    private static Stage graveyardStage;
    private static ArrayList<Card> graveyardCards;

    public static ArrayList<Card> getGraveyardCards() {
        return graveyardCards;
    }

    public static Stage getGraveyardStage() {
        return graveyardStage;
    }

    public static void close() {
//        Duel.getGameController().currentPhase = Duel.getGameController().phases.get(Duel.getGameController().phases.size() - 2);//todo check
        getGraveyardStage().close();
    }

    public void execute(ArrayList<Cell> graveyard) {
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

}
