package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class Ringofdefense extends SpellAndTrap {
    
    public Ringofdefense() {
        super("Ring of defense", "When a Trap effect that inflicts damage is activated: Make that effect damage 0.",
                3500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, Status.UNLIMITED);
    }
    
}