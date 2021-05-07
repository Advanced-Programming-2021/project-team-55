package controller.gamephasescontrollers;

import model.exceptions.GameException;
import model.board.Cell;
import model.board.GameBoard;
import view.gamephases.GameResponses;

public interface methods {

    default void selectCard(String zone, int number, boolean opponent, GameController gameController) throws GameException {
        GameBoard currentPlayerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        GameBoard opponentPlayerGameBoard = gameController.currentTurnOpponentPlayer.getGameBoard();
        Cell selectedCell = null;
        switch (zone) {
            case "Monster": {
                if (number > 5) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);
                } else {
                    if (opponent) {
                        selectedCell = opponentPlayerGameBoard.getMonsterCardZone()[number];
                    } else {
                        selectedCell = currentPlayerGameBoard.getMonsterCardZone()[number];
                    }
                }
                break;
            }
            case "Spell": {
                if (number > 5) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);
                }
                if (opponent) {
                    selectedCell = opponentPlayerGameBoard.getSpellAndTrapCardZone()[number];
                } else {
                    selectedCell = currentPlayerGameBoard.getSpellAndTrapCardZone()[number];
                }
                break;
            }
            case "Field": {
                if (opponent) {
                    selectedCell = opponentPlayerGameBoard.getFieldZone();
                } else {
                    selectedCell = currentPlayerGameBoard.getFieldZone();
                }
                break;
            }
            case "Hand": {
                if (number > currentPlayerGameBoard.getHandCards().size()) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);
                }
                selectedCell = currentPlayerGameBoard.getHandCards().get(number);
                break;
            }
        }
        if (selectedCell.getCellCard() == null) {
            throw new GameException(GameResponses.NO_CARDS_FOUND.response);
        } else {
            Cell.setSelectedCell(selectedCell);
        }

    }

    default void deselect() throws GameException {
        Cell.setSelectedCell(null);

    }
}
