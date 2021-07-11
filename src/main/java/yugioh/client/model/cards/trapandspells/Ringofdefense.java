package yugioh.client.model.cards.trapandspells;

import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class Ringofdefense extends SpellAndTrap {

    public Ringofdefense() {
        super("Ring of defense", "When a Trap effect that inflicts damage is activated: Make that effect damage 0.",
                3500, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, EffectiveTerm.UNLIMITED);
    }

}