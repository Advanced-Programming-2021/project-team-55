package yugioh.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.Player;
import yugioh.model.User;
import yugioh.model.cards.Card;
import yugioh.model.exceptions.GameException;
import yugioh.view.GameRegexes;
import yugioh.view.Regexes;
import yugioh.view.Responses;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.Duel;
import yugioh.view.gamephases.GameResponses;
import yugioh.view.menus.CheatMenu;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

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
        gameController.endGameRound();
    }

    public void increaseMoney(int amount, User user) {
        user.changeMoney(amount);
    }

    public String selectHandForce(String cardName, GameController gameController) throws GameException {
        String response = "";
        Card card = Card.getNewCardObjectByName(cardName);
        if (card == null) {
            throw new GameException(Responses.NO_CARD_EXISTS.response);
        } else {
            gameController.currentTurnPlayer.getGameBoard().addCardToHandDeck(card, true);
            response += GameResponses.CHEAT_ACTIVATED_SELECT_FORCE.response + "\nnew card added to the hand : " + cardName;
        }
        return response;
    }

    public String addOptionalCardAndSelect(String cardName, GameController gameController, boolean shouldBeSelected) throws GameException {
        Card toBeAdded = Card.getNewCardObjectByName(cardName);
        if (toBeAdded == null)
            throw new GameException(Responses.NO_CARD_EXISTS.response);
        gameController.getDrawPhaseController().addCardToHandDeck(gameController.getCurrentTurnPlayer(), toBeAdded, shouldBeSelected);
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
        command = ViewInterface.sortFields(command);
        String response = "";
        if (command.matches(GameRegexes.INCREASE_LP.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.INCREASE_LP.regex);
            increaseLPAmount(Integer.parseInt(matcher.group(1)), Duel.getGameController().currentTurnPlayer);
            response = GameResponses.CHEAT_ACTIVATED_LP_INCREASED.response;
        } else if (command.matches(GameRegexes.SET_WINNER.regex)) {
            setWinner(Duel.getGameController());
            response = GameResponses.CHEAT_ACTIVATED_WINNER_SET.response;
            CheatMenu.getCheatStage().close();
        } else if (command.matches(GameRegexes.SELECT_CARD_FORCE.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SELECT_CARD_FORCE.regex);
            try {
                response = selectHandForce(matcher.group(1), Duel.getGameController());
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(Regexes.INCREASE_MONEY.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, Regexes.INCREASE_MONEY.regex);
            cheatController.increaseMoney(Integer.parseInt(matcher.group(1)), User.loggedInUser);
            response = Responses.CHEAT_ACTIVATED_MONEY_INCREASED.response;
        } else if (command.matches(GameRegexes.CHEAT_ADD_OPTIONAL_CARD.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.CHEAT_ADD_OPTIONAL_CARD.regex);
            try {
                response = addOptionalCardAndSelect(matcher.group(1), Duel.getGameController(), false);
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.MAKE_ME_AI.regex)) {
            response = makeMeAI(Duel.getGameController());
        } else if (command.equals("close") || command.equals("exit")) {
            CheatMenu.getCheatStage().close();
        } else {
            response = Responses.INVALID_COMMAND.response;
        }
        return response;
    }

    public void runCommand() {
        resultConsole.setText(resultConsole.getText() + "\n> " + processCommand(CheatController.getInstance().console.getText()));
        console.setText("");
    }

}
