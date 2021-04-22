package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class Mysticalspacetyphoon {

    private boolean isActive = false;

    private String name = "Mystical space typhoon";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.QUICK-PLAY;
    private static final String description = "Target 1 Spell/Trap on the field; destroy that target.";
    private Status status = Status.UNLIMITED;
    private int price = 3500;
    
}