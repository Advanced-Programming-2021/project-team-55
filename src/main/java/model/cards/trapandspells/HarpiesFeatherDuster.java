package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class HarpiesFeatherDuster extends SpellAndTrap {

    public HarpiesFeatherDuster() {
        super("Harpie's Feather Duster", "Destroy all Spells and Traps your opponent controls.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, Status.LIMITED);
    }

}