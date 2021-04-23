package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class MindCrush extends SpellAndTrap {

    public MindCrush() {
        super("Mind Crush", "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies of it, otherwise you discard 1 random card.",
                2000, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, Status.UNLIMITED);
    }

}