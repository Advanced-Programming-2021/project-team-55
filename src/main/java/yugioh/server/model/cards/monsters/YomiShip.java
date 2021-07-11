package model.cards.monsters;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;
import view.ViewInterface;

public class YomiShip extends Monster {

    public YomiShip() {
        super("Yomi Ship", "If this card is destroyed by battle and sent to the GY: Destroy the monster that destroyed this card."
                , 1700, 800, 1400, 3, MonsterAttribute.WATER, MonsterType.AQUA, CardType.EFFECTIVE);
    }

    public static void handleEffect(GameController gameController, Cell attackerCell, Cell attackedCell) {
        if (!attackedCell.getCellCard().getName().equals("Yomi Ship")) return;

        ViewInterface.showResult("Yomi Ship effect activated:  monster card: \"" + attackerCell.getCellCard().getName() + "\" removed.");
        attackerCell.removeCardFromCell(gameController.getCurrentTurnPlayer().getGameBoard());
    }

}
