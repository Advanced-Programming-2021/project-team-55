package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class Raigeki extends SpellAndTrap {
    
    public Raigeki() {
        super("Raigeki", "Destroy all monsters your opponent controls.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, Status.LIMITED);
    }
    
}