package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.EffectiveTerm;

public class VanitysEmptiness extends SpellAndTrap {

    public VanitysEmptiness() {
        super("Vanity's Emptiness", "Neither player can Special Summon monsters. If a card is sent from the Deck or the field to your Graveyard: Destroy this card.",
                3500, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.CONTINUOUS, EffectiveTerm.LIMITED);
    }

}