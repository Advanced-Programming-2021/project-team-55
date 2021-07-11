package yugioh.server.model.cards.trapandspells;

import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class WallofRevealingLight extends SpellAndTrap {

    public WallofRevealingLight() {
        super("Wall of Revealing Light", "Activate by paying any multiple of 1000 Life Points. Monsters your opponent controls cannot attack if their ATK is less than or equal to the amount you paid.",
                3500, SpellOrTrap.TRAP, SpellOrTrapAttribute.CONTINUOUS, EffectiveTerm.LIMITED);
    }

}