package yugioh.server.model.cards.trapandspells;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;

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