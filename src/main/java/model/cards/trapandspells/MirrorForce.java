package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class MirrorForce extends SpellAndTrap {

    public MirrorForce() {
        super("Mirror Force", "When an opponent's monster declares an attack: Destroy all your opponent's Attack Position monsters.",
                2000, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, Status.UNLIMITED);
    }

}