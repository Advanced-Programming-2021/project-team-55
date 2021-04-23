package model.cards.trapandspells;

import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class Swordofdarkdestruction extends SpellAndTrap {

    public Swordofdarkdestruction() {
        super("Sword of dark destruction", "A DARK monster equipped with this card increases its ATK by 400 points and decreases its DEF by 200 points.",
                4300, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.EQUIP, Status.UNLIMITED);
    }

}