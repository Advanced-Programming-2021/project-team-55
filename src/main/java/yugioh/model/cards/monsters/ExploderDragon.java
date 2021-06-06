package yugioh.model.cards.monsters;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.CardStatus;
import yugioh.model.board.Cell;
import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class ExploderDragon extends Monster {

    public ExploderDragon() {
        super("Exploder Dragon", "If this card is destroyed by battle and sent to the Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks involving this attacking card."
                , 1000, 1000, 0, 3, MonsterAttribute.EARTH, MonsterType.DRAGON, CardType.EFFECTIVE);
    }

    public static String handleEffect(GameController gameController, Cell attackerCell, Cell attackedCell) {
        String response = "";
        if (isNotExploderDragon(attackerCell, attackedCell)) return "";
        else if (exploderDragonDiesDuringBeingAttacked(gameController, attackerCell, attackedCell)) {
            if (attackedCell.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED)
                response = "your opponent’s monster is destroyed \n";
            else if (attackedCell.getCardStatus() == CardStatus.DEFENSIVE_OCCUPIED)
                response = "the defense position monster is destroyed \n";
            else
                response = "opponent’s monster card was " +
                        attackedCell.getCellCard().getName() + " the defense position monster is destroyed \n";
            response += "Exploder Dragon effect activated:  monster card: \"" + attackerCell.getCellCard().getName() + "\" is also removed and no one loses LP. ";
            removePlayers(gameController, attackerCell, attackedCell);
        } else if (exploderDragonDiesDuringAttack(gameController, attackerCell, attackedCell)) {

            response = "Your monster card is destroyed \nExploder Dragon effect activated:  monster card: \"" + attackedCell.getCellCard().getName() + "\" is also removed and no one loses LP. ";
            removePlayers(gameController, attackerCell, attackedCell);
        } else
            return "";
        return response;
    }

    private static boolean exploderDragonDiesDuringBeingAttacked(GameController gameController, Cell attackerCell, Cell attackedCell) {
        return attackedCell.getCellCard().getName().equals("Exploder Dragon") &&
                gameController.getBattlePhaseController().isAttackerStronger(attackerCell, attackedCell);
    }

    private static boolean exploderDragonDiesDuringAttack(GameController gameController, Cell attackerCell, Cell attackedCell) {
        return attackerCell.getCellCard().getName().equals("Exploder Dragon") &&
                !gameController.getBattlePhaseController().isAttackerStronger(attackerCell, attackedCell) &&
                !gameController.getBattlePhaseController().isAttackerAndAttackedPowerEqual(attackerCell, attackedCell) &&
                attackedCell.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED;
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
