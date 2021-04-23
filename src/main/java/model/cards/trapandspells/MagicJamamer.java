package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class MagicJamamer extends SpellAndTrap {

    public MagicJamamer() {
        super("Magic Jamamer", "When a Spell Card is activated: Discard 1 card; negate the activation, and if you do, destroy it.",
                3000, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.COUNTER, Status.UNLIMITED);
    }

}