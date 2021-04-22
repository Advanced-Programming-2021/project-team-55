package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class Raigeki {

    private boolean isActive = false;

    private String name = "Raigeki";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Destroy all monsters your opponent controls.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}