package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class TwinTwisters {

    private boolean isActive = false;

    private String name = "Twin Twisters";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.QUICK-PLAY;
    private static final String description = "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.";
    private Status status = Status.UNLIMITED;
    private int price = 3500;
    
}