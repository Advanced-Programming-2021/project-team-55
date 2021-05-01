package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.EffectiveTerm;

public class SpellAbsorption extends SpellAndTrap {

    public SpellAbsorption() {
        super("Spell Absorption", "Each time a Spell Card is activated, gain 500 Life Points immediately after it resolves.",
                4000, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.CONTINUOUS, EffectiveTerm.UNLIMITED);
    }

}