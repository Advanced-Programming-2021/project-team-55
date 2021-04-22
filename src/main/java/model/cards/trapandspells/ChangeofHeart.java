package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class ChangeofHeart {

    private boolean isActive = false;

    private String name = "Change of Heart";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Target 1 monster your opponent controls; take control of it until the End Phase.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}