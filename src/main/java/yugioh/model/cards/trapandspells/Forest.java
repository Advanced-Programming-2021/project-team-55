package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.cards.Monster;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.MonsterType;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

import java.util.ArrayList;

public class Forest extends SpellAndTrap {

    private static final ArrayList<Monster> effectedMonsters = new ArrayList();

    public Forest() {
        super("Forest", "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.",
                4300, SpellOrTrap.SPELL, SpellOrTrapAttribute.FIELD, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        Cell[] currentTurnPlayerMonsterCardZone = gameController.getCurrentTurnPlayer().getGameBoard().getMonsterCardZone();
        Cell[] currentTurnOpponentPlayerMonsterCardZone = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone();
        increaseOpponentPlayerMonstersAttack(currentTurnPlayerMonsterCardZone);
        increaseOpponentPlayerMonstersAttack(currentTurnOpponentPlayerMonsterCardZone);
        updateSpellInGameBoard(gameController);
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
        return ((Monster) monster.getCellCard()).getMonsterType() == MonsterType.INSECT ||
                ((Monster) monster.getCellCard()).getMonsterType() == MonsterType.BEAST ||
                ((Monster) monster.getCellCard()).getMonsterType() == MonsterType.BEAST_WARRIOR;
    }

}