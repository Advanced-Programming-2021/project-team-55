package yugioh.controller.menucontroller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.view.Menus.DuelMenu;
import yugioh.view.Menus.Toast;
import yugioh.view.Menus.WelcomeMenu;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.Duel;

import java.net.URL;
import java.util.ResourceBundle;

public class DetermineStarterMenuController implements Initializable {

    private static GameController gameController;
    public JFXButton head;
    public JFXButton tale;

    public DetermineStarterMenuController() {
        DetermineStarterMenuController.gameController = Duel.getGameController();
    }

    public static void setGameController(GameController gameController) {
        DetermineStarterMenuController.gameController = gameController;
    }

    private void assignTurn() {
        String currentPlayerName = gameController.getGame().getFirstPlayer().getUser().getNickname();
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
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void back() throws Exception {
        new DuelMenu().execute();
    }

    public void headClicked() {
        assignTurn();
    }

    public void taleClicked() {
    }

}
