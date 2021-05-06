package controller.gamephasescontrollers;

import exceptions.GameException;
import model.Player;
import model.board.CardStatus;
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
        } else if (!isSummonable(selectedCell.getCellCard())) {
            throw new GameException(GameResponses.CANT_SUMMON_CARD.response);
        } else if (gameController.DoPlayerSetOrSummonedThisTurn()) {
            throw new GameException(GameResponses.ALREADY_SUMMONED_SET_IN_THIS_TURN.response);
        }
        currentPlayer.getGameBoard().addCardToMonsterCardZone(selectedCell.getCellCard());
        currentPlayer.getGameBoard().getHandCards().remove(selectedCell);
        gameController.setDidPlayerSetOrSummonThisTurn(true);
        Cell.deselectCell();
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
            Cell.setSelectedCell(null);
        }
    }

    default void setPosition(String position, GameController gameController) throws GameException {
        Cell cell = Cell.getSelectedCell();
        GameBoard playerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        if (cell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!playerGameBoard.isCellInMonsterZone(cell)) {
            throw new GameException(GameResponses.CANT_CHANGE_CARD_POSITION.response);
        } else if (position.equals("attack") && cell.getCardStatus() != CardStatus.DEFENSIVE_OCCUPIED ||
                position.equals("defense") && cell.getCardStatus() != CardStatus.OFFENSIVE_OCCUPIED) {
            throw new GameException(GameResponses.CARD_IS_ALREADY_IN_WANTED_POSITION.response);
        } else if (gameController.changedPositionCells.contains(cell)) {
            throw new GameException(GameResponses.ALREADY_CHANGED_CARD_POSITION_IN_THIS_TURN.response);
        } else {
            gameController.changedPositionCells.add(cell);
            Cell.setSelectedCell(null);
            if (position.equals("attack")) {
                cell.setCardStatus(CardStatus.OFFENSIVE_OCCUPIED);
            } else {
                cell.setCardStatus(CardStatus.DEFENSIVE_OCCUPIED);
            }
        }
    }

    default void flipSummon(GameController gameController) throws GameException {
        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        if (!currentPlayer.getGameBoard().isCellInMonsterZone(selectedCell)) {
            throw new GameException(GameResponses.CAN_NOT_CHANGE_CARD_POSITION.response);
        }
        //todo  ببینین ارور دوم رو درست هندل کردم: در همین دور تازه روی زمین گذاشته شده باشد
        if (selectedCell.getCardStatus() != CardStatus.DEFENSIVE_HIDDEN || gameController.getChangedPositionCells().contains(selectedCell)) {
            throw new GameException(GameResponses.CAN_NOT_FLIP_SUMMON.response);
        }
        selectedCell.setCardStatus(CardStatus.OFFENSIVE_OCCUPIED);
    }

    default void specialSummon(Cell cell) {

    }

    default void ritualSummon(Cell cell) {

    }

    default void changeMonsterMode(Cell cell) {

    }

    default boolean isSummonable(Card card) {
        return card.isMonster();
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
        int[]opponentCellNumbering={3,1,0,2,4};
        for (int i = 0; i < 5; i++) {
            if (opponentPlayerGameBoard.getSpellAndTrapCardZone()[opponentCellNumbering[i]]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (opponentPlayerGameBoard.getSpellAndTrapCardZone()[opponentCellNumbering[i]]
                        .getCardStatus()) {
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
            if (opponentPlayerGameBoard.getMonsterCardZone()[opponentCellNumbering[i]]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (opponentPlayerGameBoard.getMonsterCardZone()[opponentCellNumbering[i]]
                        .getCardStatus()) {
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
        int[]playerCellNumbering={4,2,0,1,3};
        for (int i = 0; i < 5; i++) {
            if (playerGameBoard.getMonsterCardZone()[playerCellNumbering[i]]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (playerGameBoard.getMonsterCardZone()[playerCellNumbering[i]]
                        .getCardStatus()) {
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
            if (playerGameBoard.getSpellAndTrapCardZone()[playerCellNumbering[i]]
                    .getCellCard() == null) {
                response += "\tE";
            } else {
                switch (playerGameBoard.getSpellAndTrapCardZone()[playerCellNumbering[i]]
                        .getCardStatus()) {
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
