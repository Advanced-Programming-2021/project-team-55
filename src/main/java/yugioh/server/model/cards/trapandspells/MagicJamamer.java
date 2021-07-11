package yugioh.server.model.cards.trapandspells;

import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class MagicJamamer extends SpellAndTrap {

    public MagicJamamer() {
        super("Magic Jamamer", "When a Spell Card is activated: Discard 1 card; negate the activation, and if you do, destroy it.",
                3000, SpellOrTrap.TRAP, SpellOrTrapAttribute.COUNTER, EffectiveTerm.UNLIMITED);
    }

}