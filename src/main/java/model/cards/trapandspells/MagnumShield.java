package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.EffectiveTerm;

public class MagnumShield extends SpellAndTrap {

    public MagnumShield() {
        super("Magnum Shield", "Equip only to a Warrior-Type monster. Apply this effect, depending on its battle position.\n● Attack Position: It gains ATK equal to its original DEF.\n● Defense Position: It gains DEF equal to its original ATK.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, EffectiveTerm.UNLIMITED);
    }

}