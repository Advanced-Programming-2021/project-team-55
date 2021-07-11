package yugioh.client.model.cards.monsters;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.CardStatus;
import yugioh.client.model.board.Cell;
import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class Marshmallon extends Monster {

    public Marshmallon() {
        super("Marshmallon", "Cannot be destroyed by battle. After damage calculation, if this card was attacked, and was face-down at the start of the Damage Step: The attacking player takes 1000 damage."
                , 700, 300, 500, 3, MonsterAttribute.LIGHT, MonsterType.FAIRY, CardType.EFFECTIVE);
    }

    public static String handleEffect(GameController gameController, Cell attackerCell, Cell attackedCell) {
        String response = "";
        if (!isMarshmallon(attackerCell, attackedCell)) return "";
        if (marshmallonDiesDuringBeingAttacked(gameController, attackerCell, attackedCell))
            if (attackedCell.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                if (!isMarshmallon(attackedCell)) return "";
                if (attackedCell.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {
                    gameController.getCurrentTurnPlayer().decreaseLP(1000);
                    response = "opponent’s monster card was " +
                            attackedCell.getCellCard().getName() +
                            " \nMarshmallon effect activated: " +
                            "Marshmallon is not destroyed and attacking player takes 1000 damage.";
                } else if (attackedCell.getCardStatus() == CardStatus.DEFENSIVE_OCCUPIED) {
                    response = "Marshmallon effect activated: Marshmallon is not destroyed";

                } else if (attackedCell.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED) {

                } else {

                }

        /*else if (marshmallonDiesDuringBeingAttacked(gameController, attackerCell, attackedCell)) {
                response = "Marshmallon effect activated: Marshmallon is not destroyed. ";

        } else if (attackerCell.getCardStatus() == OFFENSIVE_OCCUPIED &&
                !marshmallonDiesDuringBeingAttacked(gameController, attackerCell, attackedCell)) {

            if (attackedCell.getCardStatus() == CardStatus.DEFENSIVE_HIDDEN) {

                return (" \nMarshmallon effect activated: attacking player takes 1000 damage.");
            } else
                return "";
        }
        return response;*/
            }
        return "";

    }

    private static boolean isMarshmallon(Cell attackerCell, Cell attackedCell) {
        return attackedCell.getCellCard().getName().equals("Marshmallon") ||
                attackerCell.getCellCard().getName().equals("Marshmallon");
    }

    public static boolean isMarshmallon(Cell attackedCell) {
        return attackedCell.getCellCard().getName().equals("Marshmallon");
    }

    private static boolean marshmallonDiesDuringBeingAttacked(GameController gameController, Cell attackerCell, Cell attackedCell) {
        return gameController.getBattlePhaseController().isAttackerStronger(attackerCell, attackedCell) &&
                attackedCell.getCellCard().getName().equals("Marshmallon");
    }


}