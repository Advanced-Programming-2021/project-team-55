package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.EffectiveTerm;

public class Mysticalspacetyphoon extends SpellAndTrap {

    public Mysticalspacetyphoon() {
        super("Mystical space typhoon", "Target 1 Spell/Trap on the field; destroy that target.",
                3500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, EffectiveTerm.UNLIMITED);
    }

}