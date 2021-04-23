package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class PotofGreed extends SpellAndTrap {
    
    public PotofGreed() {
        super("Pot of Greed", "Draw 2 cards.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, Status.LIMITED);
    }
    
}