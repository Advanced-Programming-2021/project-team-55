package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class PotofGreed {

    private boolean isActive = false;

    private String name = "Pot of Greed";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Draw 2 cards.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}