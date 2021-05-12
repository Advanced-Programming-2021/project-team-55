package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.cards.Monster;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;

import java.util.ArrayList;

import static model.cards.cardfeaturesenums.MonsterType.*;

public class Forest extends SpellAndTrap {

    private static ArrayList<Monster> effectedMonsters = new ArrayList();

    public Forest() {
        super("Forest", "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.FIELD, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        Cell[] currentTurnPlayerMonsterCardZone = gameController.getCurrentTurnPlayer().getGameBoard().getMonsterCardZone();
        Cell[] currentTurnOpponentPlayerMonsterCardZone = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone();
        increaseOpponentPlayerMonstersAttack(currentTurnPlayerMonsterCardZone);
        increaseOpponentPlayerMonstersAttack(currentTurnOpponentPlayerMonsterCardZone);
    }

    public static void deActivateEffect(GameController gameController) {
        Cell[] currentTurnPlayerMonsterCardZone = gameController.getCurrentTurnPlayer().getGameBoard().getMonsterCardZone();
        Cell[] currentTurnOpponentPlayerMonsterCardZone = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone();
        decreaseOpponentPlayerMonstersAttack(currentTurnPlayerMonsterCardZone);
        decreaseOpponentPlayerMonstersAttack(currentTurnOpponentPlayerMonsterCardZone);
    }

    private static void increaseOpponentPlayerMonstersAttack(Cell[] monsterCardZone) {
        for (Cell monster : monsterCardZone
        ) {
            if (isForForest(monster)) {
                ((Monster) monster.getCellCard()).addATK(200);
                ((Monster) monster.getCellCard()).addDEF(200);
                effectedMonsters.add((Monster) monster.getCellCard());
            }

        }
    }

    private static void decreaseOpponentPlayerMonstersAttack(Cell[] monsterCardZone) {
        for (Monster monster : effectedMonsters
        ) {
            monster.addATK(-200);
            monster.addDEF(-200);
        }
    }

    private static boolean isForForest(Cell monster) {
        return ((Monster) monster.getCellCard()).getMonsterType() == INSECT ||
                ((Monster) monster.getCellCard()).getMonsterType() == BEAST ||
                ((Monster) monster.getCellCard()).getMonsterType() == BEAST_WARRIOR;
    }

}