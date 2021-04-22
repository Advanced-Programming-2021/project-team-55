package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class Messengerofpeace {

    private boolean isActive = false;

    private String name = "Messenger of peace";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.CONTINUOUS;
    private static final String description = "Monsters with 1500 or more ATK cannot declare an attack. Once per turn, during your Standby Phase, pay 100 LP or destroy this card.";
    private Status status = Status.UNLIMITED;
    private int price = 4000;
    
}