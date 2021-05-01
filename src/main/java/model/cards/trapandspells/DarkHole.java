package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.EffectiveTerm;

public class DarkHole extends SpellAndTrap {

    public DarkHole() {
        super("Dark Hole", "Destroy all monsters on the field.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

}