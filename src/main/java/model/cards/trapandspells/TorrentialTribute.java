package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class TorrentialTribute {

    private boolean isActive = false;

    private String name = "Torrential Tribute";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "When a monster(s) is Summoned: Destroy all monsters on the field.";
    private Status status = Status.UNLIMITED;
    private int price = 2000;
    
}