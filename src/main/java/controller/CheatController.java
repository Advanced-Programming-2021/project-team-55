package controller;

import controller.gamephasescontrollers.GameController;
import model.exceptions.GameException;
import model.Player;
import model.User;
import model.board.Cell;
import model.cards.Card;
import view.Responses;
import view.gamephases.GameResponses;

public class CheatController {
    private static CheatController cheatController;

    private CheatController() {

    }

    public static CheatController getInstance() {
        if (cheatController == null) {
            return new CheatController();
        } else {
            return cheatController;
        }
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

}
