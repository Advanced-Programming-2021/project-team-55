package yugioh.server.model.cards.monsters;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;
import yugioh.server.view.ViewInterface;

public class ManEaterBug extends Monster {

    public ManEaterBug() {
        super("Man-Eater Bug", "FLIP: Target 1 monster on the field; destroy that target."
                , 600, 450, 600, 2, MonsterAttribute.EARTH, MonsterType.INSECT, CardType.EFFECTIVE);
    }

    public static void handleEffect(GameController gameController, Cell flippedCell) {
        if (!flippedCell.getCellCard().getName().equals("Man-Eater Bug")) return;

        Cell toBeRemovedCell = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getFirstNotEmptyCell();
        if (toBeRemovedCell == null) {
            ViewInterface.showResult("Man-Eater Bug effect activated, but opponent doesn't have any monster");
            return;
        }
        ViewInterface.showResult("Man-Eater Bug effect activated: opponent monster card: \"" + toBeRemovedCell.getCellCard().getName() + "\" removed.");
        toBeRemovedCell.removeCardFromCell(gameController.getCurrentTurnPlayer().getGameBoard());
    }

}
