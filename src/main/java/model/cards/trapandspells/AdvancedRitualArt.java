package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class AdvancedRitualArt extends SpellAndTrap {

    public AdvancedRitualArt() {
        super("Advanced Ritual Art", "This card can be used to Ritual Summon any 1 Ritual Monster. You must also send Normal Monsters from your Deck to the Graveyard whose total Levels equal the Level of that Ritual Monster.",
                3000, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.RITUAL, Status.UNLIMITED);
    }

}