package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class SupplySquad extends SpellAndTrap {

    public SupplySquad() {
        super("Supply Squad", "Once per turn, if a monster(s) you control is destroyed by battle or card effect: Draw 1 card.",
                4000, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.CONTINUOUS, Status.UNLIMITED);
    }

}