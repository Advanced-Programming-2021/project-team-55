package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class NegateAttack {

    private boolean isActive = false;

    private String name = "Negate Attack";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.COUNTER;
    private static final String description = "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, then end the Battle Phase.";
    private Status status = Status.UNLIMITED;
    private int price = 3000;
    
}