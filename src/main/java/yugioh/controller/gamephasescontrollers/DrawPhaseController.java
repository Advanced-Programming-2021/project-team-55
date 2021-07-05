package yugioh.controller.gamephasescontrollers;

import yugioh.model.Player;
import yugioh.model.board.Cell;
import yugioh.model.cards.Card;
import yugioh.model.exceptions.GameException;
import yugioh.view.gamephases.GameResponses;

import java.util.ArrayList;

public class DrawPhaseController {

    private final GameController gameController;

    public DrawPhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public Cell addCardToHandDeck(Player player, Card toBeAdded) {
        Cell toBeAddedCell = new Cell(toBeAdded);
        player.getGameBoard().getHandCards().add(toBeAddedCell);
        player.getGameBoard().getDeckZone().remove(0);
        return toBeAddedCell;
    }

    public String removeFirstDeckCardFromDeckToPlay(Player player) throws GameException {
        if (!checkCardFrequency(player.getGameBoard().getHandCards())) {
            throw new GameException(GameResponses.HAND_DECK_IS_FULL.response);
        }
        if (gameController.turnCount <= 2) {
            return "";
        } else if (player.getGameBoard().getDeckZone().size() == 0 && player.getGameBoard().getHandCards().size() == 0) {
            gameController.game.addWinner(gameController.currentTurnOpponentPlayer);
            gameController.game.addLoser(gameController.currentTurnPlayer);
            gameController.endGameRound();
        }
        Card removedCard = player.getGameBoard().getDeckZone().get(0).getCellCard();
        player.getGameBoard().addCardsToHandDeck(1, true);
        return "new card added to the hand : " + removedCard.getName();
    }

    private boolean checkCardFrequency(ArrayList<Cell> handCards) {
        return handCards.size() < 6;
    }

}
