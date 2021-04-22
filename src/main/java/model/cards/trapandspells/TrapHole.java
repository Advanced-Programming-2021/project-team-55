package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class TrapHole {

    private boolean isActive = false;

    private String name = "Trap Hole";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: Target that monster; destroy that target.";
    private Status status = Status.UNLIMITED;
    private int price = 2000;
    
}