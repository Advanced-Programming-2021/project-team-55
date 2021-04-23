package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class Terraforming extends SpellAndTrap {
    
    public Terraforming() {
        super("Terraforming", "Add 1 Field Spell from your Deck to your hand.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, Status.LIMITED);
    }
    
}