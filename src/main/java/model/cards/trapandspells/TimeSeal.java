package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class TimeSeal extends SpellAndTrap {

    public TimeSeal() {
        super("Time Seal", "Skip the Draw Phase of your opponent's next turn.",
                2000, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

}