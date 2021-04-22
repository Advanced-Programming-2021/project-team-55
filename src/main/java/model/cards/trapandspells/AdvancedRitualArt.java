package model.cards.trapandspells;

import model.cards.SpellOrTrap;
import model.cards.SpellOrTrapAttribute;
import model.cards.Status;

public class AdvancedRitualArt {

    private boolean isActive = false;

    private String name = "Advanced Ritual Art";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.RITUAL;
    private static final String description = "This card can be used to Ritual Summon any 1 Ritual Monster. You must also send Normal Monsters from your Deck to the Graveyard whose total Levels equal the Level of that Ritual Monster.";
    private Status status = Status.UNLIMITED;
    private int price = 3000;
    
}