package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class BlackPendant extends SpellAndTrap {
    
    public BlackPendant() {
        super("Black Pendant", "The equipped monster gains 500 ATK. When this card is sent from the field to the Graveyard: Inflict 500 damage to your opponent.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, Status.UNLIMITED);
    }
    
}