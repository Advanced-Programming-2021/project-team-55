package yugioh.model.cards.trapandspells;

import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class SolemnWarning extends SpellAndTrap {

    public SolemnWarning() {
        super("Solemn Warning", "When a monster(s) would be Summoned, OR when a Spell/Trap Card, or monster effect, is activated that includes an effect that Special Summons a monster(s): Pay 2000 LP; negate the Summon or activation, and if you do, destroy it.",
                3000, SpellOrTrap.TRAP, SpellOrTrapAttribute.COUNTER, EffectiveTerm.UNLIMITED);
    }

}