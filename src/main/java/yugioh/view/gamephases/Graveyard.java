package yugioh.view.gamephases;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
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
        Timeline fadeOutTimeline = new Timeline();
        KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(500), new KeyValue(graveyardStage.getScene().getRoot().opacityProperty(), 0));
        fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
        fadeOutTimeline.setOnFinished((aeb) -> graveyardStage.close());
        fadeOutTimeline.play();
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

        parent.setOpacity(0);

        graveyardStage = new Stage();
        graveyardStage.setX(720);
        graveyardStage.initOwner(primaryStage);
        graveyardStage.initStyle(StageStyle.TRANSPARENT);
        graveyardStage.setScene(scene);
        graveyardStage.show();


        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(250), new KeyValue(graveyardStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey1);
        fadeInTimeline.play();
    }

}
