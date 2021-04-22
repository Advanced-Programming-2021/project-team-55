package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class MagnumShield {

    private boolean isActive = false;

    private String name = "Magnum Shield";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.EQUIP;
    private static final String description = "Equip only to a Warrior-Type monster. Apply this effect, depending on its battle position.
● Attack Position: It gains ATK equal to its original DEF.
● Defense Position: It gains DEF equal to its original ATK.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}