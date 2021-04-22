package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class MonsterReborn {

    private boolean isActive = false;

    private String name = "Monster Reborn";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.NORMAL;
    private static final String description = "Target 1 monster in either GY; Special Summon it.";
    private Status status = Status.LIMITED;
    private int price = 2500;
    
}