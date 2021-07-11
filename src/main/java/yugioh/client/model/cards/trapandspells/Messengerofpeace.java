package yugioh.client.model.cards.trapandspells;

import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;

public class Messengerofpeace extends SpellAndTrap {

    public Messengerofpeace() {
        super("Messenger of peace", "Monsters with 1500 or more ATK cannot declare an attack. Once per turn, during your Standby Phase, pay 100 LP or destroy this card.",
                4000, SpellOrTrap.SPELL, SpellOrTrapAttribute.CONTINUOUS, EffectiveTerm.UNLIMITED);
    }

}