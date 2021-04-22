package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class TimeSeal {

    private boolean isActive = false;

    private String name = "Time Seal";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Skip the Draw Phase of your opponent's next turn.";
    private Status status = Status.LIMITED;
    private int price = 2000;
    
}