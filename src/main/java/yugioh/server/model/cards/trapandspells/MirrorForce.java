package yugioh.server.model.cards.trapandspells;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.CardStatus;
import yugioh.server.model.board.Cell;
import yugioh.server.model.board.GameBoard;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;

public class MirrorForce extends SpellAndTrap {

    public MirrorForce() {
        super("Mirror Force", "When an opponent's monster declares an attack: Destroy all your opponent's Attack Position monsters.",
                2000, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
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