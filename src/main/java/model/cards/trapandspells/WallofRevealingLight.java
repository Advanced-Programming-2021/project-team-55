package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class WallofRevealingLight {

    private boolean isActive = false;

    private String name = "Wall of Revealing Light";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.CONTINUOUS;
    private static final String description = "Activate by paying any multiple of 1000 Life Points. Monsters your opponent controls cannot attack if their ATK is less than or equal to the amount you paid.";
    private Status status = Status.LIMITED;
    private int price = 3500;
    
}