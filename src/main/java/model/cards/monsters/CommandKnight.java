package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class CommandKnight extends Monster {

    public CommandKnight() {
        super("Command Knight", "All Warrior-Type monsters you control gain 400 ATK. If you control another monster, monsters your opponent controls cannot target this card for an attack."
                , 2100, 1000, 1000, 4, MonsterAttribute.FIRE, MonsterType.WARRIOR, CardType.EFFECTIVE);
    }

}
