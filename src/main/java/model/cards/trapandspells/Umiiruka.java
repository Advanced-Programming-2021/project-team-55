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

public class Umiiruka extends SpellAndTrap {
    private static final ArrayList<Monster> effectedMonsters;

    static {
        effectedMonsters = new ArrayList<>();
    }

    public Umiiruka() {
        super("Umiiruka", "Increase the ATK of all WATER monsters by 500 points and decrease their DEF by 400 points.",
                4300, SpellOrTrap.SPELL, SpellOrTrapAttribute.FIELD, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        Cell[] currentTurnPlayerMonsterCardZone = gameController.getCurrentTurnPlayer().getGameBoard().getMonsterCardZone();
        Cell[] currentTurnOpponentPlayerMonsterCardZone = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone();
        activateForPlayersMonsters(currentTurnPlayerMonsterCardZone);
        activateForPlayersMonsters(currentTurnOpponentPlayerMonsterCardZone);
        updateSpellInGameBoard(gameController);
    }

    public static void deActivateEffect() {
        deactivateForPlayersMonsters();
    }

    private static void activateForPlayersMonsters(Cell[] monsterCardZone) {
        for (Cell monster : monsterCardZone
        ) {
            if (isForUmiiruka(monster)) {
                ((Monster) monster.getCellCard()).addATK(500);
                ((Monster) monster.getCellCard()).addDEF(-400);
                effectedMonsters.add((Monster) monster.getCellCard());
            }
        }
    }

    private static void deactivateForPlayersMonsters() {
        for (Monster monster : effectedMonsters
        ) {
            monster.addATK(-500);
            monster.addDEF(400);
        }
    }

    private static boolean isForUmiiruka(Cell monster) {
        return ((Monster) monster.getCellCard()).getMonsterType() == AQUA;
    }


}