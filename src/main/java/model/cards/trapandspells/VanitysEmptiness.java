package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class VanitysEmptiness {

    private boolean isActive = false;

    private String name = "Vanity's Emptiness";
    private SpellOrTrap type = SpellOrTrap.TRAP;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.CONTINUOUS;
    private static final String description = "Neither player can Special Summon monsters. If a card is sent from the Deck or the field to your Graveyard: Destroy this card.";
    private Status status = Status.LIMITED;
    private int price = 3500;
    
}