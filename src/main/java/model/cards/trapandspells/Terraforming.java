package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class Terraforming {

    private boolean isActive = false;

    private String name = "Terraforming";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Add 1 Field Spell from your Deck to your hand.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}