package yugioh.server.model.cards.trapandspells;

import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class Ringofdefense extends SpellAndTrap {

    public Ringofdefense() {
        super("Ring of defense", "When a Trap effect that inflicts damage is activated: Make that effect damage 0.",
                3500, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, EffectiveTerm.UNLIMITED);
    }

}