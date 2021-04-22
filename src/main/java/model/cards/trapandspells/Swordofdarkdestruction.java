package model.cards.trapandspells;

import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import model.cards.cardfeaturesenums.Status;

public class Swordofdarkdestruction {

    private boolean isActive = false;

    private String name = "Sword of dark destruction";
    private SpellOrTrap type = SpellOrTrap.SPELL;
    private SpellOrTrapAttribute attribute = SpellOrTrapAttribute.EQUIP;
    private static final String description = "A DARK monster equipped with this card increases its ATK by 400 points and decreases its DEF by 200 points.";
    private Status status = Status.UNLIMITED;
    private int price = 4300;
    
}