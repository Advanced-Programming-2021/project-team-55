package yugioh.view.menus;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.view.gamephases.Duel;

import java.net.URL;

public class EndOfGameMenu extends WelcomeMenu {

    private static String resultString;
    private static Stage stage;
    private boolean isEndOfTheGame;

    public static void closeStage() {
        stage.close();
    }

    public static String getResultString() {
        return resultString;
    }

    public void execute(String resultString, boolean isEndOfTheGame) throws Exception {
        Toast.isGameEnded = true;
        EndOfGameMenu.resultString = resultString;
        this.isEndOfTheGame = isEndOfTheGame;
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/yugioh/fxml/EndOfGameMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        if (!isEndOfTheGame) {
            ((VBox) pane.getChildren().get(3)).getChildren().remove(1);
            Button yesButton = new Button();
            yesButton.setText("yes");

            Button noButton = new Button();
            GameController gameController = Duel.getGameController();
            yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    gameController.setCurrentTurnPlayer(gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1));
                    gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getWinners().get(gameController.getGame().getWinners().size() - 1));
                    Duel.runGame(gameController);

                    stage.close();
                    Toast.isGameEnded = false;
                }
            });
            noButton.setText("no");
            noButton.setLayoutX(400);
            noButton.setLayoutY(450);
            yesButton.setLayoutX(600);
            yesButton.setLayoutY(450);

            yesButton.getStyleClass().add("buttonShape");
            yesButton.getStyleClass().add("buttonText");
            yesButton.getStyleClass().add("buttonHover");
            yesButton.getStyleClass().add("buttonSize");

            noButton.getStyleClass().add("buttonShape");
            noButton.getStyleClass().add("buttonText");
            noButton.getStyleClass().add("buttonHover");
            noButton.getStyleClass().add("buttonSize");
            noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    gameController.setCurrentTurnPlayer(gameController.getGame().getWinners().get(gameController.getGame().getWinners().size() - 1));
                    gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1));
                    Duel.runGame(gameController);
                    stage.close();
                    Toast.isGameEnded = false;
                }
            });
            String playerName = gameController.getGame().getLosers().get(gameController.getGame().getLosers().size() - 1).getUser().getNickname();
            Label label = new Label(playerName + " do you want to be the first player?");
            label.setLayoutX(400);
            label.setLayoutY(400);
            label.getStyleClass().add("buttonText");
            pane.getChildren().add(label);
            pane.getChildren().add(yesButton);
            pane.getChildren().add(noButton);
        }
        Scene scene = WelcomeMenu.createScene(pane);
        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(WelcomeMenu.getStage());
        stage.setScene(scene);
        stage.show();
    }

}
