package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;

public class MirrorForce extends SpellAndTrap {

    public MirrorForce() {
        super("Mirror Force", "When an opponent's monster declares an attack: Destroy all your opponent's Attack Position monsters.",
                2000, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard opponentPlayerGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        for (Cell cell : opponentPlayerGameBoard.getMonsterCardZone()) {
            if (!cell.isEmpty() && cell.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {
                cell.removeCardFromCell(opponentPlayerGameBoard);
            }
        }
        ViewInterface.showResult("Mirror Force effect activated : all your opponent's Attack Position monsters destroyed");
        gameController.getBattlePhaseController().setAttackDisabled();
        updateSpellInGameBoard(gameController);
    }

}