package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.Player;
import yugioh.model.board.Cell;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;

import java.util.ArrayList;

public class SpellAbsorption extends SpellAndTrap {

    private static final ArrayList<Cell> spellAbsorptions = new ArrayList<>();

    private Player owner;

    public SpellAbsorption() {
        super("Spell Absorption", "Each time a Spell Card is activated, gain 500 Life Points immediately after it resolves.",
                4000, SpellOrTrap.SPELL, SpellOrTrapAttribute.CONTINUOUS, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        try {
            ((SpellAbsorption) Cell.getSelectedCell().getCellCard()).owner = gameController.getCurrentTurnPlayer();
            spellAbsorptions.add(Cell.getSelectedCell());
        } catch (Exception e) {
            return;
        }
        ViewInterface.showResult("Spell Absorption activated.");
        updateSpellInGameBoard(gameController);
    }

    public static void handleEffect() {
        for (Cell cell : spellAbsorptions) {
            try {
                ((SpellAbsorption) cell.getCellCard()).owner.increaseLP(500);
            } catch (Exception ignored) {
            }
        }
    }

}