package yugioh.model.cards.trapandspells;

import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class MindCrush extends SpellAndTrap {

    public MindCrush() {
        super("Mind Crush", "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies of it, otherwise you discard 1 random card.",
                2000, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

}