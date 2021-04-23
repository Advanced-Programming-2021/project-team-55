package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class MonsterReborn extends SpellAndTrap {
    
    public MonsterReborn() {
        super("Monster Reborn", "Target 1 monster in either GY; Special Summon it.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, Status.LIMITED);
    }
    
}