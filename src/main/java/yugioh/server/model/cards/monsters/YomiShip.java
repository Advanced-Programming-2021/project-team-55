package yugioh.server.model.cards.monsters;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;
import yugioh.server.view.ViewInterface;

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
