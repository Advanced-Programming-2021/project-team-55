package yugioh.server.model.cards.monsters;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.CardStatus;
import yugioh.server.model.board.Cell;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

import yugioh.server.static model.board.CardStatus.DEFENSIVE_OCCUPIED;
import yugioh.server.static model.board.CardStatus.OFFENSIVE_OCCUPIED;

public class ExploderDragon extends Monster {

    public ExploderDragon() {
        super("Exploder Dragon", "If this card is destroyed by battle and sent to the Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks involving this attacking card."
                , 1000, 1000, 0, 3, MonsterAttribute.EARTH, MonsterType.DRAGON, CardType.EFFECTIVE);
    }

    public static String handleEffect(GameController gameController, Cell attackerCell, Cell attackedCell) {
        String response = "";
        if (isNotExploderDragon(attackerCell, attackedCell)) return "";
        else if (exploderDragonDiesDuringBeingAttacked(gameController, attackerCell, attackedCell)) {
            if (attackedCell.getCardStatus() == OFFENSIVE_OCCUPIED)
                response = "your opponent’s monster is destroyed \n";
            else if (attackedCell.getCardStatus() == DEFENSIVE_OCCUPIED)
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
