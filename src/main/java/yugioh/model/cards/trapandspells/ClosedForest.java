package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.cards.Monster;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.MonsterType;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;

import java.util.ArrayList;

public class ClosedForest extends SpellAndTrap {

    public ClosedForest() {
        super("Closed Forest", "All Beast-Type monsters you control gain 100 ATK for each monster in your Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot be activated during the turn this card is destroyed.",
                4300, SpellOrTrap.SPELL, SpellOrTrapAttribute.FIELD, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        ArrayList<Cell> allMonsters = new ArrayList<>();
        allMonsters.addAll(gameController.getCurrentTurnPlayer().getGameBoard().getMonsterZoneCells());
        int toBeAddedATK = gameController.getCurrentTurnPlayer().getGameBoard().getGraveyard().size() * 100;
        for (Cell cell : allMonsters) {
            try {
                Monster monster = (Monster) cell.getCellCard();
                if (monster.getMonsterType() == MonsterType.BEAST) {
                    monster.addATK(toBeAddedATK);
                }
            } catch (Exception ignored) {
            }
        }
        ViewInterface.showResult("Closed Forest activated.");
        updateSpellInGameBoard(gameController);
    }

}