package controller.gamephasescontrollers;

import exceptions.GameException;
import model.Player;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Card;
import model.cards.Monster;
import view.gamephases.GameResponses;

import static model.board.CardStatus.DEFENSIVE_OCCUPIED;
import static model.board.CardStatus.OFFENSIVE_OCCUPIED;

public class BattlePhaseController implements methods {

    private final GameController gameController;

    public BattlePhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void startBattlePhase() {

    }

    public String attack(int attackedCellNumber) throws GameException {
        String response = "";
        Cell attackerCell = Cell.getSelectedCell();
        Cell attackedCell = null;
        GameBoard opponentGameBoard = gameController.currentTurnOpponentPlayer.getGameBoard();
        GameBoard playerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        if (attackedCellNumber <= 5 && attackedCellNumber >= 1) {
            attackedCell = (gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone())[attackedCellNumber - 1];
        }
        if (attackerCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!playerGameBoard.isCellInMonsterZone(attackerCell)) {
            throw new GameException(GameResponses.CANT_ATTACK_CARD.response);
        } else
            response = ((Monster) attackerCell.getCellCard()).handleMonsterAttack(attackerCell, attackedCell, opponentGameBoard, playerGameBoard, gameController);
        return response;
    }


    public boolean canCardAttack(Card card) {
        return true;
    }

    public String directAttack(GameController gameController) throws GameException {
        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        if (!currentPlayer.getGameBoard().isCellInMonsterZone(selectedCell)) {
            throw new GameException(GameResponses.CAN_NOT_ATTACK_WITH_THIS_CARD.response);
        }
        if (gameController.didCardAttackThisTurn(selectedCell)) {
            throw new GameException(GameResponses.CARD_ALREADY_ATTACKED.response);
        }
        if (!gameController.canPlayerDirectAttack(selectedCell)) {
            throw new GameException(GameResponses.CAN_NOT_DIRECT_ATTACK.response);
        }
        Monster attackerMonster = (Monster) selectedCell.getCellCard();
        gameController.getCurrentTurnOpponentPlayer().decreaseLP(attackerMonster.getAtk());
        return "your opponent receives " + attackerMonster.getAtk() + " battle damage";
    }

}
