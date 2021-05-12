package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.cards.Monster;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.MonsterType;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;

import java.util.ArrayList;

public class ClosedForest extends SpellAndTrap {

    public ClosedForest() {
        super("Closed Forest", "All Beast-Type monsters you control gain 100 ATK for each monster in your Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot be activated during the turn this card is destroyed.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.FIELD, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        ArrayList<Cell> allMonsters = new ArrayList<>();
        allMonsters.addAll(gameController.getCurrentTurnPlayer().getGameBoard().getMonsterZoneCells());
        int toBeAddedATK = gameController.getCurrentTurnPlayer().getGameBoard().getGraveyard().size() * 100;
        for (Cell cell : allMonsters) {
            try{
                Monster monster = (Monster) cell.getCellCard();
                if (monster.getMonsterType() == MonsterType.BEAST) {
                    monster.addATK(toBeAddedATK);
                }
            }catch (Exception ignored){}
        }
        ViewInterface.showResult("Closed Forest activated.");
        updateSpellInGameBoard(gameController);
    }

}