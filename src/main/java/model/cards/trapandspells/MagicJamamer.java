package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class MagicJamamer {

    private boolean isActive = false;

    private String name = "Magic Jamamer";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.COUNTER;
    private static final String description = "When a Spell Card is activated: Discard 1 card; negate the activation, and if you do, destroy it.";
    private Status status = Status.UNLIMITED;
    private int price = 3000;
    
}