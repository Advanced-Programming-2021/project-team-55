package yugioh.model.cards.trapandspells;

import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class VanitysEmptiness extends SpellAndTrap {

    public VanitysEmptiness() {
        super("Vanity's Emptiness", "Neither player can Special Summon monsters. If a card is sent from the Deck or the field to your Graveyard: Destroy this card.",
                3500, SpellOrTrap.TRAP, SpellOrTrapAttribute.CONTINUOUS, EffectiveTerm.LIMITED);
    }

}