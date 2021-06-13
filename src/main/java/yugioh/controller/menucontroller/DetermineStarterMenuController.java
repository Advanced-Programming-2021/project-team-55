package yugioh.controller.menucontroller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.view.Menus.DuelMenu;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.Duel;

import java.net.URL;
import java.util.ResourceBundle;

public class DetermineStarterMenuController implements Initializable {

    private static GameController gameController;
    public JFXButton head;
    public JFXButton tale;
    public Label firstPlayerName;

    public DetermineStarterMenuController() {
        DetermineStarterMenuController.gameController = Duel.getGameController();
    }

    public static void setGameController(GameController gameController) {
        DetermineStarterMenuController.gameController = gameController;
    }

    private void assignTurn(String choice) {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        String opponentPlayerName = gameController.getGame().getSecondPlayer().getUser().getNickname();

        if (Integer.parseInt(choice) == gameController.tossCoin()) {
            ViewInterface.showResult(currentPlayerName + " do you want to be the first player? yes/no");
            String input = ViewInterface.getInput();
            while (!input.equals("no") && !input.equals("yes")) {
                ViewInterface.showResult("Error: invalid choice!");
                input = ViewInterface.getInput();
            }
            switch (input) {
                case "yes": {
                    gameController.setCurrentTurnPlayer(gameController.getGame().getFirstPlayer());
                    gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getSecondPlayer());
                    break;
                }
                case "no": {
                    gameController.setCurrentTurnPlayer(gameController.getGame().getSecondPlayer());
                    gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getFirstPlayer());
                    break;
                }
            }
        } else {
            ViewInterface.showResult(opponentPlayerName + " do you want to be the first player? yes/no");
            String input = ViewInterface.getInput();
            while (!input.equals("no") && !input.equals("yes")) {
                ViewInterface.showResult("Error: invalid choice!");
                input = ViewInterface.getInput();
            }
            switch (input) {
                case "yes": {
                    gameController.setCurrentTurnPlayer(gameController.getGame().getSecondPlayer());
                    gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getFirstPlayer());
                    break;
                }
                case "no": {
                    gameController.setCurrentTurnPlayer(gameController.getGame().getFirstPlayer());
                    gameController.setCurrentTurnOpponentPlayer(gameController.getGame().getSecondPlayer());
                    break;
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        firstPlayerName.setText(currentPlayerName + " :");
    }

    public void back() throws Exception {
        new DuelMenu().execute();
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

}
