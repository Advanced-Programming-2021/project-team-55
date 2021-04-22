package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class UnitedWeStand {

    private boolean isActive = false;

    private String name = "United We Stand";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.EQUIP;
    private static final String description = "The equipped monster gains 800 ATK/DEF for each face-up monster you control.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}