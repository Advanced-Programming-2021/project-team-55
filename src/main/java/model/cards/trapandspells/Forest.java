package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.EffectiveTerm;

public class Forest extends SpellAndTrap {

    public Forest() {
        super("Forest", "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.FIELD, EffectiveTerm.UNLIMITED);
    }

}