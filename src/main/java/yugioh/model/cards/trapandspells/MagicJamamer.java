package yugioh.model.cards.trapandspells;

import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class MagicJamamer extends SpellAndTrap {

    public MagicJamamer() {
        super("Magic Jamamer", "When a Spell Card is activated: Discard 1 card; negate the activation, and if you do, destroy it.",
                3000, SpellOrTrap.TRAP, SpellOrTrapAttribute.COUNTER, EffectiveTerm.UNLIMITED);
    }

}