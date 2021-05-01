package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.EffectiveTerm;

public class ClosedForest extends SpellAndTrap {

    public ClosedForest() {
        super("Closed Forest", "All Beast-Type monsters you control gain 100 ATK for each monster in your Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot be activated during the turn this card is destroyed.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.FIELD, EffectiveTerm.UNLIMITED);
    }

}