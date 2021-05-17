package model.cards.monsters;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;
import org.jetbrains.annotations.NotNull;

import static model.board.CardStatus.DEFENSIVE_OCCUPIED;
import static model.board.CardStatus.OFFENSIVE_OCCUPIED;
import static view.gamephases.GameResponses.DEFENSIVE_OCCUPIED_DESTROYED;
import static view.gamephases.GameResponses.OFFENSIVE_OCCUPIED_DESTROYED;

public class ExploderDragon extends Monster {

    public ExploderDragon() {
        super("Exploder Dragon", "If this card is destroyed by battle and sent to the Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks involving this attacking card."
                , 1000, 1000, 0, 3, MonsterAttribute.EARTH, MonsterType.DRAGON, CardType.EFFECTIVE);
    }

    public static String handleEffect(GameController gameController, Cell attackerCell, Cell attackedCell) {
        String response;
        if (isNotExploderDragon(attackerCell, attackedCell)) return "";
        else if (exploderDragonDiesDuringBeingAttacked(gameController, attackerCell, attackedCell)) {
            if (attackedCell.getCardStatus() == OFFENSIVE_OCCUPIED)
                response = OFFENSIVE_OCCUPIED_DESTROYED +"\n";
            else if (attackedCell.getCardStatus() == DEFENSIVE_OCCUPIED)
                response = DEFENSIVE_OCCUPIED_DESTROYED + "\n";
            else
                response = defensiveHiddenResponse(attackedCell);
            response += effectActivatedResponse(attackerCell);
            removePlayers(gameController, attackerCell, attackedCell);
        } else if (exploderDragonDiesDuringAttack(gameController, attackerCell, attackedCell)) {
            response = exploderDragonDiesDuringAttackResponse(attackedCell);
            removePlayers(gameController, attackerCell, attackedCell);
        } else
            return "";
        return response;
    }

    @NotNull
    private static String exploderDragonDiesDuringAttackResponse(Cell attackedCell) {
        return "Your monster card is destroyed \n"+
                "Exploder Dragon effect activated:  monster card: \"" +
                attackedCell.getCellCard().getName() +
                "\" is also removed and no one loses LP. ";
    }

    @NotNull
    private static String effectActivatedResponse(Cell attackerCell) {
        return "Exploder Dragon effect activated:  monster card: \"" +
                attackerCell.getCellCard().getName() +
                "\" is also removed and no one loses LP. ";
    }

    @NotNull
    private static String defensiveHiddenResponse(Cell attackedCell) {
        return "opponent’s monster card was " +
                attackedCell.getCellCard().getName() + " the defense position monster is destroyed \n";
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
