package yugioh.client.model.cards.monsters;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.CardStatus;
import yugioh.client.model.board.Cell;
import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class TheCalculator extends Monster {

    public TheCalculator() {
        super("The Calculator", "The ATK of this card is the combined Levels of all face-up monsters you control x 300."
                , 8000, 0, 0, 2, MonsterAttribute.LIGHT, MonsterType.THUNDER, CardType.EFFECTIVE);
    }

    public static void handleEffect(GameController gameController, Cell attackerCell, Cell attackedCell) {
        try {
            if (!attackedCell.getCellCard().getName().equals("The Calculator") &&
                    !attackerCell.getCellCard().getName().equals("The Calculator")) return;
        } catch (Exception e) {
            return;
        }
        if (isCalculatorForCurrentTurnPlayer(gameController, attackerCell))
            ((Monster) attackerCell.getCellCard()).setAtk(calculateAtkPower(gameController.currentTurnPlayer.getGameBoard().getMonsterCardZone()));
        else
            ((Monster) attackedCell.getCellCard()).setAtk(calculateAtkPower(gameController.currentTurnOpponentPlayer.getGameBoard().getMonsterCardZone()));
    }

    private static int calculateAtkPower(Cell[] monsterCardZone) {
        int levelSum = 0;
        for (Cell monsterCell : monsterCardZone
        ) {
            if (monsterCell.getCellCard() != null && (monsterCell.getCardStatus() == CardStatus.OFFENSIVE_OCCUPIED))
                levelSum += ((Monster) monsterCell.getCellCard()).getLevel();
        }
        return levelSum * 300;
    }

    private static boolean isCalculatorForCurrentTurnPlayer(GameController gameController, Cell attackerCell) {
        return gameController.getCurrentTurnPlayer().getGameBoard().isCellInMonsterZone(attackerCell);
    }

}
