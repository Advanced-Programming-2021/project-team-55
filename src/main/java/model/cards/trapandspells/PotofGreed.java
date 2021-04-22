package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class PotofGreed {

    private boolean isActive = false;

    private String name = "Pot of Greed";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Draw 2 cards.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}