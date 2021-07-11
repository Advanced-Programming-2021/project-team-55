package yugioh.server.model.cards.trapandspells;

import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class MindCrush extends SpellAndTrap {

    public MindCrush() {
        super("Mind Crush", "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies of it, otherwise you discard 1 random card.",
                2000, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

}