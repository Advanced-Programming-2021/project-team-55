package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class MirrorForce {

    private boolean isActive = false;

    private String name = "Mirror Force";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "When an opponent's monster declares an attack: Destroy all your opponent's Attack Position monsters.";
    private Status status = Status.UNLIMITED;
    private int price = 2000;
    
}