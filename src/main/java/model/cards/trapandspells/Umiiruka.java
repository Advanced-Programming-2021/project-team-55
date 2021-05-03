package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class Umiiruka extends SpellAndTrap {

    public Umiiruka() {
        super("Umiiruka", "Increase the ATK of all WATER monsters by 500 points and decrease their DEF by 400 points.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.FIELD, EffectiveTerm.UNLIMITED);
    }

}