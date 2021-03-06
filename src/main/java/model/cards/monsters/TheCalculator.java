package model.cards.monsters;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;
import view.ViewInterface;

import static model.board.CardStatus.OFFENSIVE_OCCUPIED;

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
