package yugioh.client.model.cards.trapandspells;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.Cell;
import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.client.view.ViewInterface;

import java.util.ArrayList;

public class Yami extends SpellAndTrap {

    public Yami() {
        super("Yami", "All Fiend and Spellcaster monsters on the field gain 200 ATK/DEF, also all Fairy monsters on the field lose 200 ATK/DEF.",
                4300, SpellOrTrap.SPELL, SpellOrTrapAttribute.FIELD, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        ArrayList<Cell> allMonsters = new ArrayList<>();
        allMonsters.addAll(gameController.getCurrentTurnPlayer().getGameBoard().getMonsterZoneCells());
        allMonsters.addAll(gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterZoneCells());
        for (Cell cell : allMonsters) {
            try {
                Monster monster = (Monster) cell.getCellCard();
                switch (monster.getMonsterType()) {
                    case FIEND:
                    case SPELLCASTER: {
                        monster.addATK(200);
                        monster.addDEF(200);
                        break;
                    }
                    case FAIRY: {
                        monster.addATK(-200);
                        monster.addDEF(-200);
                        break;
                    }
                }
            } catch (Exception ignored) {
            }
        }
        ViewInterface.showResult("Yami activated.");
        updateSpellInGameBoard(gameController);
    }

}