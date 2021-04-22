package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class MindCrush {

    private boolean isActive = false;

    private String name = "Mind Crush";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies of it, otherwise you discard 1 random card.";
    private Status status = Status.UNLIMITED;
    private int price = 2000;
    
}