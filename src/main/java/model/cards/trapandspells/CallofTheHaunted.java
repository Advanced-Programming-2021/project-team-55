package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class CallofTheHaunted {

    private boolean isActive = false;

    private String name = "Call of The Haunted";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.CONTINUOUS;
    private static final String description = "Activate this card by targeting 1 monster in your GY; Special Summon that target in Attack Position. When this card leaves the field, destroy that monster. When that monster is destroyed, destroy this card.";
    private Status status = Status.UNLIMITED;
    private int price = 3500;
    
}