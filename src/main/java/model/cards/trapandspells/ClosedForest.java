package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class ClosedForest {

    private boolean isActive = false;

    private String name = "Closed Forest";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.FIELD;
    private static final String description = "All Beast-Type monsters you control gain 100 ATK for each monster in your Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot be activated during the turn this card is destroyed.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}