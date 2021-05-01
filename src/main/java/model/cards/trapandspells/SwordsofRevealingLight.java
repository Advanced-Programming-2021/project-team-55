package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.EffectiveTerm;

public class SwordsofRevealingLight extends SpellAndTrap {

    public SwordsofRevealingLight() {
        super("Swords of Revealing Light", "After this card's activation, it remains on the field, but destroy it during the End Phase of your opponent's 3rd turn. When this card is activated: If your opponent controls a face-down monster, flip all monsters they control face-up. While this card is face-up on the field, your opponent's monsters cannot declare an attack.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

}