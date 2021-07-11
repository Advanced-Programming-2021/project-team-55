package yugioh.client.controller.gamephasescontrollers;

import yugioh.client.model.Player;
import yugioh.client.model.board.Cell;
import yugioh.client.model.cards.Card;
import yugioh.client.model.exceptions.GameException;
import yugioh.client.view.gamephases.GameResponses;

import java.util.ArrayList;

public class DrawPhaseController {

    private final GameController gameController;

    public DrawPhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void addCardToHandDeck(Player player, Card toBeAdded, boolean shouldBeSelected) {
        Cell cell = new Cell(toBeAdded);
        if (shouldBeSelected) {
            Cell.setSelectedCell(cell);
        }
        player.getGameBoard().addCardToHandDeck(toBeAdded, true);
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
