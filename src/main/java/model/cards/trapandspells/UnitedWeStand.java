package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class UnitedWeStand {

    private boolean isActive = false;

    private String name = "United We Stand";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.EQUIP;
    private static final String description = "The equipped monster gains 800 ATK/DEF for each face-up monster you control.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}