package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class Forest {

    private boolean isActive = false;

    private String name = "Forest";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.FIELD;
    private static final String description = "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}