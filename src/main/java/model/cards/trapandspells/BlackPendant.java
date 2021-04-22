package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class BlackPendant {

    private boolean isActive = false;

    private String name = "Black Pendant";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.EQUIP;
    private static final String description = "The equipped monster gains 500 ATK. When this card is sent from the field to the Graveyard: Inflict 500 damage to your opponent.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}