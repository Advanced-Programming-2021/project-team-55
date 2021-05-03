package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class UnitedWeStand extends SpellAndTrap {

    public UnitedWeStand() {
        super("United We Stand", "The equipped monster gains 800 ATK/DEF for each face-up monster you control.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, EffectiveTerm.UNLIMITED);
    }

}