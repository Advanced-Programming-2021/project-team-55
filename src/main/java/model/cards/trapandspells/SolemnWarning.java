package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class SolemnWarning {

    private boolean isActive = false;

    private String name = "Solemn Warning";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.COUNTER;
    private static final String description = "When a monster(s) would be Summoned, OR when a Spell/Trap Card, or monster effect, is activated that includes an effect that Special Summons a monster(s): Pay 2000 LP; negate the Summon or activation, and if you do, destroy it.";
    private Status status = Status.UNLIMITED;
    private int price = 3000;
    
}