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
import yugioh.view.menus.WelcomeMenu;

import java.net.URL;
import java.util.ArrayList;

public class Graveyard extends WelcomeMenu{

    private static Stage graveyardStage;
    private static ArrayList<Cell> graveyard;
    private GameController gameController;

    public static void makeText(Stage ownerStage, String toastMsg) {
        int toastDelay = 2500;
        int fadeInDelay = 250;
        int fadeOutDelay = 500;
        Stage toastStage = new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-padding: 50px;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey1);
        fadeInTimeline.setOnFinished((ae) ->
        {
            new Thread(() -> {
                try {
                    Thread.sleep(toastDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Timeline fadeOutTimeline = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
                fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
                fadeOutTimeline.play();
            }, "toast").start();
        });
        fadeInTimeline.play();
    }

    public void execute(ArrayList<Cell> graveyard) {
        Graveyard.graveyard = graveyard;
        gameController = Duel.getGameController();
//        String response = processCommand(ViewInterface.getInput());
//        ViewInterface.showResult(response);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/Graveyard.fxml");
        Parent parent = FXMLLoader.load(url);
        Scene scene = WelcomeMenu.createScene(parent);
        Stage stage = new Stage();
//        stage.initOwner(primaryStage);
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UTILITY);
//        stage.setScene(scene);
//        EditDeckMenu.stage = stage;
        stage.setScene(scene);
//        stage.show();
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
