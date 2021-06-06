package yugioh.model.cards.trapandspells;

import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class CallofTheHaunted extends SpellAndTrap {

    public CallofTheHaunted() {
        super("Call of The Haunted", "Activate this card by targeting 1 monster in your GY; Special Summon that target in Attack Position. When this card leaves the field, destroy that monster. When that monster is destroyed, destroy this card.",
                3500, SpellOrTrap.TRAP, SpellOrTrapAttribute.CONTINUOUS, EffectiveTerm.UNLIMITED);
    }

}