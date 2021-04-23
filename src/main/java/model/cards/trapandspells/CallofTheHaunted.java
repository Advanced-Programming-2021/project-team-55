package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class CallofTheHaunted extends SpellAndTrap {

    public CallofTheHaunted() {
        super("Call of The Haunted", "Activate this card by targeting 1 monster in your GY; Special Summon that target in Attack Position. When this card leaves the field, destroy that monster. When that monster is destroyed, destroy this card.",
                3500, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.CONTINUOUS, Status.UNLIMITED);
    }

}