package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class MonsterReborn {

    private boolean isActive = false;

    private String name = "Monster Reborn";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Target 1 monster in either GY; Special Summon it.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}