package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class DarkHole {

    private boolean isActive = false;

    private String name = "Dark Hole";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Destroy all monsters on the field.";
    private Status status = Status.UNLIMITED;
    private int price = 2500;
    
}