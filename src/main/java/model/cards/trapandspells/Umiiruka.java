package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class Umiiruka {

    private boolean isActive = false;

    private String name = "Umiiruka";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.FIELD;
    private static final String description = "Increase the ATK of all WATER monsters by 500 points and decrease their DEF by 400 points.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}