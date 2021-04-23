package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class ChangeofHeart extends SpellAndTrap {

    public ChangeofHeart() {
        super("Change of Heart", "Target 1 monster your opponent controls; take control of it until the End Phase.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, Status.LIMITED);
    }

}