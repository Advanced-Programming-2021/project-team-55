package controller.gamephasescontrollers;

import exceptions.GameException;
import model.Player;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Card;
import model.cards.Monster;
import view.ConsoleColors;
import view.gamephases.GameResponses;

public interface MainPhasesController {

    default void monsterInsert(Cell cell) {

    }

    default void monsterSet(Cell cell) {

    }

    default void monsterSummon(GameController gameController) throws GameException {
        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        if (!isSummonable(selectedCell)) {
            throw new GameException(GameResponses.CANT_SUMMON_CARD.response);
        }
        if (gameController.DoPlayerSetOrSummonedThisTurn()) {
            throw new GameException(GameResponses.ALREADY_SUMMONED_SET_IN_THIS_TURN.response);
        }
        currentPlayer.getGameBoard().addCardToMonsterCardZone(selectedCell.getCellCard());
        currentPlayer.getGameBoard().getHandCards().remove(selectedCell);
        gameController.setDidPlayerSetOrSummonThisTurn(true);
    }

    default void setCard(GameController gameController) throws GameException {
        Cell selectedCell = Cell.getSelectedCell();
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        Card selectedCard = selectedCell.getCellCard();
        if (selectedCard == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!playerGameBoard.getHandCards().contains(selectedCell)) {
            throw new GameException(GameResponses.CANT_SET_CARD.response);
        } else {
            if (selectedCard instanceof Monster) {
                if (gameController.DoPlayerSetOrSummonedThisTurn()) {
                    throw new GameException(GameResponses.ALREADY_SUMMONED_SET_IN_THIS_TURN.response);
                }
                playerGameBoard.addCardToMonsterCardZone(selectedCard);
                playerGameBoard.getHandCards().remove(selectedCell);
                gameController.setDidPlayerSetOrSummonThisTurn(true);
            } else {
                playerGameBoard.addCardToSpellAndTrapCardZone(selectedCard);
                playerGameBoard.getHandCards().remove(selectedCell);

            }
        }
    }

    default void specialSummon(Cell cell) {

    }

    default void ritualSummon(Cell cell) {

    }

    default void changeMonsterMode(Cell cell) {

    }

    default boolean isSummonable(Cell cell) {
        return true;
    }

    default boolean isRitualSummonable(Cell cell) {
        return false;
    }

    default boolean isSpecialSummonable(Cell cell) {
        return false;
    }

    default boolean isSettable(Cell cell) {
        return false;
    }

    default void nonMonsterInsert(Cell cell) {

    }

    default boolean isFieldZoneFull() {
        return false;
    }

    default boolean hasEnoughTribute(int cardLevel) {
        return false;
    }

    default boolean isInFieldZone(Cell cell) {
        return false;
    }

    default boolean isTributesAddressesValid(int[] addresses) {
        return false;
    }

    default String showGameBoard(Player currentPlayer, Player opponentPlayer) {
        String response = ConsoleColors.BLUE + "\t\t" + opponentPlayer.getUser().getNickname() + ":" + opponentPlayer.getLP() + "\n";
        GameBoard playerGameBoard = currentPlayer.getGameBoard();
        GameBoard opponentPlayerGameBoard = opponentPlayer.getGameBoard();
        for (int i = 0; i < 6 - opponentPlayerGameBoard.getHandCards().size(); i++) {
            response += "\t";
        }
        for (int i = 0; i < opponentPlayerGameBoard.getHandCards().size(); i++) {
            response += "\tc";
        }
        response += "\n" + opponentPlayerGameBoard.getDeckZone().size() + "\n";
        response += "\t4\t2\t1\t3\t5\n";
        for (int i = 0; i < 5; i++) {
            if (opponentPlayerGameBoard.getSpellAndTrapCardZone()[4 - i]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (opponentPlayerGameBoard.getSpellAndTrapCardZone()[4 - i]
                        .getCardPosition()) {
                    case HIDDEN: {
                        response += "\tH";
                        break;
                    }
                    case OCCUPIED: {
                        response += "\tO";
                        break;
                    }

                }
            }
        }
        response += "\n";
        for (int i = 0; i < 5; i++) {
            if (opponentPlayerGameBoard.getMonsterCardZone()[4 - i]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (opponentPlayerGameBoard.getMonsterCardZone()[4 - i]
                        .getCardPosition()) {
                    case DEFENSIVE_HIDDEN: {
                        response += "\tDH";
                        break;
                    }
                    case DEFENSIVE_OCCUPIED: {
                        response += "\tDO";
                        break;
                    }
                    case OFFENSIVE_OCCUPIED: {
                        response += "\tOO";
                    }
                }
            }
        }
        response += "\n" + opponentPlayerGameBoard.getGraveyard().size() + "\t\t\t\t\t\t";
        if (opponentPlayerGameBoard.getFieldZone() != null) {
            response += "E";
        } else {
            response += "O";
        }
        response += "\n\n--------------------------\n\n";
        if (playerGameBoard.getFieldZone() != null) {
            response += "E";
        } else {
            response += "O";
        }
        response += "\t\t\t\t\t\t" + playerGameBoard.getGraveyard().size() + "\n";
        //int[]cellNumbers={3,1,0,2,4};
        for (int i = 0; i < 5; i++) {
            if (playerGameBoard.getMonsterCardZone()[i]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (playerGameBoard.getMonsterCardZone()[i]
                        .getCardPosition()) {
                    case DEFENSIVE_HIDDEN: {
                        response += "\tDH";
                        break;
                    }
                    case DEFENSIVE_OCCUPIED: {
                        response += "\tDO";
                        break;
                    }
                    case OFFENSIVE_OCCUPIED: {
                        response += "\tOO";
                    }
                }
            }
        }
        response += "\n";

        for (int i = 0; i < 5; i++) {
            if (playerGameBoard.getSpellAndTrapCardZone()[i]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (playerGameBoard.getSpellAndTrapCardZone()[i]
                        .getCardPosition()) {
                    case HIDDEN: {
                        response += "\tH";
                        break;
                    }
                    case OCCUPIED: {
                        response += "\tO";
                        break;
                    }

                }
            }
        }
        response += "\n\t5\t3\t1\t2\t4";
        response += "\n\t\t\t\t\t\t" + playerGameBoard.getDeckZone().size() + "\n";
        for (int i = 0; i < playerGameBoard.getHandCards().size(); i++) {
            response += "c\t";
        }
        response += "\n\t\t" + currentPlayer.getUser().getNickname() + ":" + currentPlayer.getLP() + ConsoleColors.RESET;
        return response;
    }

}
