package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.cards.Monster;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;

import java.util.ArrayList;

import static yugioh.model.cards.cardfeaturesenums.MonsterType.AQUA;

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
        ViewInterface.showResult("Umiiruka activated: all WATER monsters ATK increased by 500 points and their DEF decreased by 400 points.");
        activateForPlayersMonsters(currentTurnPlayerMonsterCardZone);
        activateForPlayersMonsters(currentTurnOpponentPlayerMonsterCardZone);
        updateSpellInGameBoard(gameController);
    }

    public static void deActivateEffect(Cell cell) {
        if(!cell.isEmpty()&&cell.getCellCard().getName().equals("Umiiruka")) {
            ViewInterface.showResult("Umiiruka deactivated: all WATER monsters ATK decrease by 500 points and their DEF increased by 400 points.");
            deactivateForPlayersMonsters();
        }
    }

    private static void activateForPlayersMonsters(Cell[] monsterCardZone) {
        for (Cell monster : monsterCardZone
        ) {
            if (!monster.isEmpty()&&isForUmiiruka(monster)) {
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