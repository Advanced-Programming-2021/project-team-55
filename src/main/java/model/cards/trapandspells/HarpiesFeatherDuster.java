package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class HarpiesFeatherDuster {

    private boolean isActive = false;

    private String name = "Harpie's Feather Duster";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Destroy all Spells and Traps your opponent controls.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}