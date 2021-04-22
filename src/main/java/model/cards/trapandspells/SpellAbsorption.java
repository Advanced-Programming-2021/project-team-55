package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class SpellAbsorption {

    private boolean isActive = false;

    private String name = "Spell Absorption";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.CONTINUOUS;
    private static final String description = "Each time a Spell Card is activated, gain 500 Life Points immediately after it resolves.";
    private Status status = Status.UNLIMITED;
    private int price = 4000;
    
}