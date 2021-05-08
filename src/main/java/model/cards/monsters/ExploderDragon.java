package model.cards.monsters;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class ExploderDragon extends Monster {

    public ExploderDragon() {
        super("Exploder Dragon", "If this card is destroyed by battle and sent to the Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks involving this attacking card."
                , 1000, 1000, 0, 3, MonsterAttribute.EARTH, MonsterType.DRAGON, CardType.EFFECTIVE);
    }

    public static String handleEffect(GameController gameController, Cell attackerCell, Cell attackedCell) {
        if (isNotExploderDragon(attackerCell, attackedCell)) return "";
        if (exploderDragonDiesDuringBeingAttacked(gameController, attackerCell, attackedCell)) {
            removePlayers(gameController, attackerCell, attackedCell);
            return "Exploder Dragon effect activated:  monster card: \\\"" + attackerCell.getCellCard().getName() + "\\\" removed and no one loses LP. ";
        } else if (exploderDragonDiesDuringAttack(gameController, attackerCell, attackedCell)) {
            removePlayers(gameController, attackerCell, attackedCell);
            return "Exploder Dragon effect activated:  monster card: \\\"" + attackedCell.getCellCard().getName() + "\\\" removed and no one loses LP. ";
        }
        return "";
    }

    private static boolean exploderDragonDiesDuringBeingAttacked(GameController gameController, Cell attackerCell, Cell attackedCell) {
        return attackedCell.getCellCard().getName().equals("Exploder Dragon") &&
                gameController.getBattlePhaseController().isAttackerStronger(attackerCell, attackedCell);
    }

    private static boolean exploderDragonDiesDuringAttack(GameController gameController, Cell attackerCell, Cell attackedCell) {
        return attackerCell.getCellCard().getName().equals("Exploder Dragon") &&
                !gameController.getBattlePhaseController().isAttackerStronger(attackerCell, attackedCell) &&
                !gameController.getBattlePhaseController().isAttackerAndAttackedPowerEqual(attackerCell, attackedCell) &&
                attackedCell.getCardStatus()== CardStatus.OFFENSIVE_OCCUPIED;
    }

    private static void removePlayers(GameController gameController, Cell attackerCell, Cell attackedCell) {
        attackedCell.removeCardFromCell(gameController.getCurrentTurnOpponentPlayer().getGameBoard());
        attackerCell.removeCardFromCell(gameController.getCurrentTurnPlayer().getGameBoard());
    }

    public static boolean isNotExploderDragon(Cell attackerCell, Cell attackedCell) {
        return !attackedCell.getCellCard().getName().equals("Exploder Dragon") &&
                !attackerCell.getCellCard().getName().equals("Exploder Dragon");
    }
}
