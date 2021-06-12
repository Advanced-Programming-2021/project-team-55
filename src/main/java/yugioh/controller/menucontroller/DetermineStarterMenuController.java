package yugioh.controller.menucontroller;

import javafx.fxml.Initializable;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.view.Menus.DuelMenu;
import yugioh.view.ViewInterface;

import java.net.URL;
import java.util.ResourceBundle;

public class DetermineStarterMenuController implements Initializable {

    private static GameController gameController;

    public DetermineStarterMenuController(GameController gameController) {
        DetermineStarterMenuController.gameController = gameController;
    }

    public static void setGameController(GameController gameController) {
        DetermineStarterMenuController.gameController = gameController;
    }

    private void assignTurn() {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
        String opponentPlayerName = gameController.getGame().getSecondPlayer().getUser().getNickname();
        if (gameController.getCurrentRound() == 1) {
            String request = currentPlayerName + " choose a side: 1-head 2-tale";
            ViewInterface.showResult(request);
            String choice = ViewInterface.getInput();
            while (!choice.equals("1") && !choice.equals("2")) {
                ViewInterface.showResult("Error: invalid choice!");
                choice = ViewInterface.getInput();
            }
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void back() throws Exception {
        new DuelMenu().execute();
    }
}
