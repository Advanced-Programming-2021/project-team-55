package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class WarriorDaiGrepher extends Monster {

    public WarriorDaiGrepher() {
        super("Warrior Dai Grepher", "The warrior who can manipulate dragons. Nobody knows his mysterious past."
                , 3400, 1700, 1600, 4, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
