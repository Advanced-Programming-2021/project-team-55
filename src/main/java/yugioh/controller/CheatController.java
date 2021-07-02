package yugioh.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.Player;
import yugioh.model.User;
import yugioh.model.board.Cell;
import yugioh.model.cards.Card;
import yugioh.model.exceptions.GameException;
import yugioh.view.Regexes;
import yugioh.view.Responses;
import yugioh.view.gamephases.GameResponses;

import java.net.URL;
import java.util.ResourceBundle;

public class CheatController implements Initializable {

    private static CheatController cheatController;
    public TextField console;
    public TextArea resultConsole;

    public static CheatController getInstance() {
        if (cheatController == null) {
            cheatController = new CheatController();
        }
        return cheatController;
    }

    public void increaseLPAmount(int amount, Player player) {
        player.setLP(player.getLP() + amount);
    }

    public void setWinner(GameController gameController) {
        gameController.endDuel();
    }

    public void increaseMoney(int amount, User user) {
        user.changeMoney(amount);
    }

    public String selectHandForce(String cardName, GameController gameController) throws GameException {
        String response = "";
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            throw new GameException(Responses.NO_CARD_EXISTS.response);
        } else {
            gameController.currentTurnPlayer.getGameBoard().addCardToHandDeck(cardName);
            response += GameResponses.CHEAT_ACTIVATED_SELECT_FORCE + "\nnew card added to the hand : " + cardName;
        }
        return response;
    }

    public String addOptionalCardAndSelect(String cardName, GameController gameController) throws GameException {
        Card toBeAdded = Card.getNewCardObjectByName(cardName);
        if (toBeAdded == null)
            throw new GameException(Responses.NO_CARD_EXISTS.response);
        Cell addedCell = gameController.getDrawPhaseController().addCardToHandDeck(gameController.getCurrentTurnPlayer(), toBeAdded);
        Cell.setSelectedCell(addedCell);
        return "new card added to the hand and selected: " + cardName;
    }

    public String makeMeAI(GameController gameController) {
        gameController.getCurrentTurnPlayer().setAI(true);
        return "you're now ai :D";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cheatController = this;
    }

    public String processCommand(String command) {
        String response = "";
        if (command.matches(Regexes.CHANGE_NICKNAME.regex)) {

        } else if (command.matches("hello")) {
            System.out.println("hi");
            response = "hi";
        } else if (command.matches(Regexes.SHOW_MENU.regex)) {
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    public void runCommand() {
        resultConsole.setText(resultConsole.getText() + "\n> " + processCommand(CheatController.getInstance().console.getText()));
    }

}
