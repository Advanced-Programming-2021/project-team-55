package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class Forest {

    private boolean isActive = false;

    private String name = "Forest";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.FIELD;
    private static final String description = "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}