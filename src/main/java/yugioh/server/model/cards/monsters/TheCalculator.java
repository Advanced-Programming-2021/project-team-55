package yugioh.server.model.cards.monsters;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.board.GameBoard;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;
import yugioh.server.view.ViewInterface;

import yugioh.server.static model.board.CardStatus.OFFENSIVE_OCCUPIED;

public class TheCalculator extends Monster {

    public TheCalculator() {
        super("The Calculator", "The ATK of this card is the combined Levels of all face-up monsters you control x 300."
                , 8000, 0, 0, 2, MonsterAttribute.LIGHT, MonsterType.THUNDER, CardType.EFFECTIVE);
    }

    public static void handleEffect(GameController gameController, Cell attackerCell, Cell attackedCell) {
        if (!attackedCell.getCellCard().getName().equals("The Calculator") &&
                !attackerCell.getCellCard().getName().equals("The Calculator")) return;
        if (isCalculatorForCurrentTurnPlayer(gameController, attackerCell))
            ((Monster) attackerCell.getCellCard()).setAtk(calculateAtkPower(gameController.currentTurnPlayer.getGameBoard().getMonsterCardZone()));
        else
            ((Monster) attackedCell.getCellCard()).setAtk(calculateAtkPower(gameController.currentTurnOpponentPlayer.getGameBoard().getMonsterCardZone()));
    }

    private static int calculateAtkPower(Cell[] monsterCardZone) {
        int levelSum = 0;
        for (Cell monsterCell : monsterCardZone
        ) {
            if (monsterCell.getCellCard() != null && (monsterCell.getCardStatus() == OFFENSIVE_OCCUPIED))
                levelSum += ((Monster) monsterCell.getCellCard()).getLevel();
        }
        return levelSum * 300;
    }

    private static boolean isCalculatorForCurrentTurnPlayer(GameController gameController, Cell attackerCell) {
        return gameController.getCurrentTurnPlayer().getGameBoard().isCellInMonsterZone(attackerCell);
    }

}
