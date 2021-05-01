package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.EffectiveTerm;

public class Messengerofpeace extends SpellAndTrap {

    public Messengerofpeace() {
        super("Messenger of peace", "Monsters with 1500 or more ATK cannot declare an attack. Once per turn, during your Standby Phase, pay 100 LP or destroy this card.",
                4000, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.CONTINUOUS, EffectiveTerm.UNLIMITED);
    }

}