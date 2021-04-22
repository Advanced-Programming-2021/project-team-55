package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class Ringofdefense {

    private boolean isActive = false;

    private String name = "Ring of defense";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.QUICK-PLAY;
    private static final String description = "When a Trap effect that inflicts damage is activated: Make that effect damage 0.";
    private Status status = Status.UNLIMITED;
    private int price = 3500;
    
}