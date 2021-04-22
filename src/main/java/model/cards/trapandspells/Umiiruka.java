package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class Umiiruka {

    private boolean isActive = false;

    private String name = "Umiiruka";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.FIELD;
    private static final String description = "Increase the ATK of all WATER monsters by 500 points and decrease their DEF by 400 points.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}