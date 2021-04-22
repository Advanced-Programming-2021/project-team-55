package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class TimeSeal {

    private boolean isActive = false;

    private String name = "Time Seal";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Skip the Draw Phase of your opponent's next turn.";
    private Status status = Status.LIMITED;
    private int price = 2000;
    
}