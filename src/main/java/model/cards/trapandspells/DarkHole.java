package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class DarkHole {

    private boolean isActive = false;

    private String name = "Dark Hole";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Destroy all monsters on the field.";
    private Status status = Status.UNLIMITED;
    private int price = 2500;
    
}