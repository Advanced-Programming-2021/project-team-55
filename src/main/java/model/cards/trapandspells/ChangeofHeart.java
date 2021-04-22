package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class ChangeofHeart {

    private boolean isActive = false;

    private String name = "Change of Heart";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Target 1 monster your opponent controls; take control of it until the End Phase.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}