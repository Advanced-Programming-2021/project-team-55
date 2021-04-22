package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class HarpiesFeatherDuster {

    private boolean isActive = false;

    private String name = "Harpie's Feather Duster";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Destroy all Spells and Traps your opponent controls.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}