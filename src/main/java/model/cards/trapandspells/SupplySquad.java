package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class SupplySquad {

    private boolean isActive = false;

    private String name = "Supply Squad";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.CONTINUOUS;
    private static final String description = "Once per turn, if a monster(s) you control is destroyed by battle or card effect: Draw 1 card.";
    private Status status = Status.UNLIMITED;
    private int price = 4000;
    
}