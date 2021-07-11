package yugioh.client.model.cards.trapandspells;

import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class SupplySquad extends SpellAndTrap {

    public SupplySquad() {
        super("Supply Squad", "Once per turn, if a monster(s) you control is destroyed by battle or card effect: Draw 1 card.",
                4000, SpellOrTrap.SPELL, SpellOrTrapAttribute.CONTINUOUS, EffectiveTerm.UNLIMITED);
    }

}