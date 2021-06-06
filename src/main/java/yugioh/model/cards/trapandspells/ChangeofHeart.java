package yugioh.model.cards.trapandspells;

import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class ChangeofHeart extends SpellAndTrap {

    public ChangeofHeart() {
        super("Change of Heart", "Target 1 monster your opponent controls; take control of it until the End Phase.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

}