package yugioh.controller.menucontroller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.view.menus.DetermineStarterMenu;
import yugioh.view.menus.Toast;
import yugioh.view.gamephases.Duel;

import java.net.URL;
import java.util.ResourceBundle;

public class DetermineStarterMenuController implements Initializable {

    private static GameController gameController;
    public JFXButton head;
    public JFXButton tale;
    public Label firstPlayerName;
    public Label yesNoQuestion;
    public JFXButton noButton;
    public JFXButton yesButton;

    public DetermineStarterMenuController() {
        DetermineStarterMenuController.gameController = Duel.getGameController();
    }

    public static void setGameController(GameController gameController) {
        DetermineStarterMenuController.gameController = gameController;
    }

    private void assignTurn(String choice) {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        String opponentPlayerName = gameController.getGame().getSecondPlayer().getUser().getNickname();

        int randomNumber = gameController.tossCoin();
        switch (randomNumber) {//todo add coin animation
            case 1:
                Toast.makeText(DetermineStarterMenu.getStage(), "HEAD!");
                break;
            case 2:
                Toast.makeText(DetermineStarterMenu.getStage(), "TALE!");
                break;
        }
        if (Integer.parseInt(choice) == randomNumber) {
            yesNoQuestion.setText(currentPlayerName + " do you want to be the first player?");
            noButton.setOpacity(1);
            yesButton.setOpacity(1);
        } else {
            yesNoQuestion.setText(opponentPlayerName + " do you want to be the first player?");
            noButton.setOpacity(1);
            yesButton.setOpacity(1);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        firstPlayerName.setText(currentPlayerName + " :");
        noButton.setOpacity(0);
        yesButton.setOpacity(0);
    }

    public void back() throws Exception {
//        new DuelMenu().execute();
        DetermineStarterMenu.getStage().close();
    }

    public void headClicked() {
        head.setDisable(true);
        tale.setDisable(true);
        assignTurn("1");
    }

    public void taleClicked() {
        head.setDisable(true);
        tale.setDisable(true);
        assignTurn("2");
    }

    public void noClicked() {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        String opponentPlayerName = gameController.getGame().getSecondPlayer().getUser().getNickname();
        if (yesNoQuestion.getText().startsWith(currentPlayerName)) {
            gameController.setCurrentTurnPlayer(gameController.getGame().getSecondPlayer());
            gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getFirstPlayer());
            DetermineStarterMenu.getStage().close();
        } else if (yesNoQuestion.getText().startsWith(opponentPlayerName)) {
            gameController.setCurrentTurnPlayer(gameController.getGame().getFirstPlayer());
            gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getSecondPlayer());
            DetermineStarterMenu.getStage().close();
        }
    }

    public void yesClicked() {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        String opponentPlayerName = gameController.getGame().getSecondPlayer().getUser().getNickname();
        if (yesNoQuestion.getText().startsWith(currentPlayerName)) {
            gameController.setCurrentTurnPlayer(gameController.getGame().getFirstPlayer());
            gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getSecondPlayer());
            DetermineStarterMenu.getStage().close();
        } else if (yesNoQuestion.getText().startsWith(opponentPlayerName)) {
            gameController.setCurrentTurnPlayer(gameController.getGame().getSecondPlayer());
            gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getFirstPlayer());
            DetermineStarterMenu.getStage().close();
        }
    }

}
