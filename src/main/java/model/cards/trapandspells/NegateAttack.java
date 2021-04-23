package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class NegateAttack extends SpellAndTrap {
    
    public NegateAttack() {
        super("Negate Attack", "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, then end the Battle Phase.",
                3000, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.COUNTER, Status.UNLIMITED);
    }
    
}